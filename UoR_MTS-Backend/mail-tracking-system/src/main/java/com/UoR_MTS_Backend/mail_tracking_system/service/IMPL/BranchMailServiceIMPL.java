package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.controller.MailRecordController;
import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchMailServiceIMPL implements BranchMailService {

    private static final Logger logger = LoggerFactory.getLogger(MailRecordController.class);

    @Autowired
    private BranchMailRepo branchMailRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public String transferMailToBranchCart(String barcodeId) {
        try {
            // Fetch the mail record by barcodeId
            MailRecord mailRecord = branchMailRepo.findByBarcodeId(barcodeId);

            // If no record found, handle accordingly
            if (mailRecord == null) {
                logger.warn("Mail record with barcodeId {} not found.", barcodeId);
                return "Mail record not found.";
            }

            // Sanitize the branch name
            String sanitizedBranchName = mailRecord.getBranchName()
                    .toLowerCase()
                    .replaceAll("[^a-z0-9_]", "_");
            String tableName = sanitizedBranchName + "_mailcart";

            // Parameterized query
            String query = "INSERT INTO " + tableName + " " +
                    "(barcode, branch_code, sender, receiver, description, mail_type, postal_code, tracking_code, received_date, claimed_date, claimed_person) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Execute the query with prepared statement
            jdbcTemplate.update(query,
                    mailRecord.getBarcodeId(),
                    mailRecord.getBranchCode(),
                    mailRecord.getSenderName(),
                    mailRecord.getReceiverName(),
                    mailRecord.getMailDescription(),
                    mailRecord.getMailType(),
                    null,
                    mailRecord.getTrackingNumber(),
                    mailRecord.getInsertDateTime(),
                    mailRecord.getUpdateDateTime(),
                    null // For claimed_person
            );

            return "Data saved to " + tableName + " successfully.";
        } catch (Exception e) {
            logger.error("Error occurred while transferring mail by barcodeId: {}", barcodeId, e);
            throw new RuntimeException("Error occurred while transferring mail", e);  // Or throw a custom exception
        }
    }

    @Override
    @Transactional
    public void updateMailRecordList(String barcodeId) {
        branchMailRepo.deleteMailRecordByBarcodeId(barcodeId);
    }

    @Override
    public List<MailRecordDTO> getMailsByBranch(String branch) {
        List<MailRecord> allBranches = branchMailRepo.findByBranchName(branch);
        List<MailRecordDTO> branchDTOList = new ArrayList<>();

        for (MailRecord mailRecord : allBranches) {
            MailRecordDTO dto = new MailRecordDTO(
                    mailRecord.getReceiverName(),
                    mailRecord.getBarcodeId(),
                    mailRecord.getMailDescription()
            );
            branchDTOList.add(dto);
        }

        return branchDTOList;
    }
}
