package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.config.ModelMapperConfig;
import com.UoR_MTS_Backend.mail_tracking_system.controller.MailRecordController;
import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.repo.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repo.MailRecordRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repo.specification.MailRecordSpecification;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailRecordServiceIMPL implements MailRecordService {
    private static final Logger logger = LoggerFactory.getLogger(MailRecordController.class);

    @Autowired
    private MailRecordRepo mailRecordRepo;
    @Autowired
    private DailyMailRepo dailyMailRepo;
    @Autowired
    private ModelMapperConfig modelMapperConfig;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public String transferDailyMailsToMainCart() {

        List<DailyMail> dailyMails = dailyMailRepo.findAll();

        if (dailyMails.isEmpty()) {
            return "No daily mails to transfer.";
        } else {

            List<MailRecord> mailRecord = dailyMails.stream()
                    .map(dailyMail -> modelMapperConfig.modelMapper().map(dailyMail, MailRecord.class))
                    .toList();

            mailRecordRepo.saveAll(mailRecord);
            dailyMailRepo.deleteAll();
            dailyMailRepo.resetAutoIncrement();

        }

        return "All daily mails successfully transferred to the main mail cart.";
    }


    @Override
    @Transactional
    public Page<MailRecordDTO> filterMailRecords(
            String senderName,
            String receiverName,
            String mailType,
            String trackingNumber,
            String branchName,
            Pageable pageable) {

        // Filter records using Specification
            Page<MailRecord> filteredMailRecords = mailRecordRepo.findAll(
                    MailRecordSpecification.filterBy(
                            senderName, receiverName, mailType, trackingNumber, branchName
                    ),pageable);



            // Map to DTO and return
            return filteredMailRecords.map(mail -> {

                    MailRecordDTO dto = new MailRecordDTO();
                    dto.setMailRecordId(mail.getMailRecordId());
                    dto.setSenderName(mail.getSenderName());
                    dto.setReceiverName(mail.getReceiverName());
                    dto.setMailType(mail.getMailType());
                    dto.setTrackingNumber(mail.getTrackingNumber());
                    dto.setBranchName(mail.getBranchName());
                    dto.setInsertDateTime(mail.getInsertDateTime());
                    dto.setUpdateDateTime(mail.getUpdateDateTime());
                    return dto;

            });

    }

    @Override
    public MailRecord searchMailByBarcodeId(String barcodeId) {
        try {
            // Fetch the mail record by barcodeId
            MailRecord mailRecord = mailRecordRepo.findByBarcodeId(barcodeId);

            // If no record found, return null or handle accordingly
            if (mailRecord == null) {
                logger.warn("Mail record with barcodeId {} not found.", barcodeId);
            }

            return  mailRecord;  // Return the found mail record

        } catch (Exception e) {
            logger.error("Error occurred while searching for mail by barcodeId: {}", barcodeId, e);
            throw new RuntimeException("Error occurred while searching by barcodeId", e);  // Or throw custom exception
        }
    }

    @Override
    public String transferMailToBranchCart(String barcodeId) {
        try {
            // Fetch the mail record by barcodeId
            MailRecord mailRecord = mailRecordRepo.findByBarcodeId(barcodeId);

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
    public Page<MailRecord> getAllMailRecords(Pageable pageable) {
        Page<MailRecord> mailPage = mailRecordRepo.findAll(pageable);

        return mailPage.map(mail -> new MailRecord(
                mail.getMailRecordId(),
                mail.getBranchCode(),
                mail.getBranchName(),
                mail.getSenderName(),
                mail.getReceiverName(),
                mail.getMailType(),
                mail.getTrackingNumber(),
                mail.getMailDescription(),
                mail.getBarcodeId(),
                mail.getBarcodeImage(),
                mail.getInsertDateTime(),
                mail.getUpdateDateTime()
        ));
    }

    @Override
    public List<MailRecordDTO> getMailsByBranch(String branch) {
        List<MailRecord> allBranches = mailRecordRepo.findByBranchName(branch);
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

    @Override
    public void updateMailRecordList(String barcodeId) {
        mailRecordRepo.deleteMailRecordByBarcodeId(barcodeId);
    }

}
