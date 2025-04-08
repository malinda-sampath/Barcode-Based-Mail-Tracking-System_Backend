package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailClaimDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailClaimPerson;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailClaimReference;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailClaimPersonRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailClaimRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailRecordRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailClaimService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MailClaimServiceIMPL implements MailClaimService {

    private final MailClaimRepo mailClaimRepo;
    private final MailClaimPersonRepo mailClaimPersonRepo;
    private final MailRecordRepo mailRecordRepo;
    private final DataSource dataSource;

    @Override
    @Transactional
    public String ClaimMailDetailsSave(MailClaimDetailsDTO claimDetailsDTO) throws SQLException {

        List<MailRecord> claimMails = mailRecordRepo.findAllByBarcodeIdIn(claimDetailsDTO.getBarcodeID());

        if (claimMails.isEmpty()) {
            throw new ResourceNotFoundException("Mail records not found for the provided barcode IDs.");
        }

        String status = claimDetailsDTO.getStatus();

        for (MailRecord mailRecord : claimMails) {

            mailRecord.setReferenceNumber(claimDetailsDTO.getReferenceNumber());

            if ("picked".equalsIgnoreCase(status)) {
                mailRecord.setStatus("picked");
                mailRecord.setLocation(mailRecord.getBranch().getBranchName());

                insertMailRecordToDynamicTable(mailRecord); // Insert into dynamic table

            } else if ("claimed".equalsIgnoreCase(status)) {
                mailRecord.setStatus("claimed");
                mailRecord.setLocation(claimDetailsDTO.getPersonName() + "Claimed");

                insertMailRecordToDynamicTable(mailRecord); // Cleared since it's been claimed

            } else if ("returned".equalsIgnoreCase(status)) {
                mailRecord.setStatus("returned");
                mailRecord.setLocation("Admin Unit");

                insertMailRecordToDynamicTable(mailRecord);
            } else {
                return "Invalid status provided.";
            }

            mailRecordRepo.save(mailRecord);
        }

            MailClaimPerson mailClaimPerson = new MailClaimPerson(
                    claimDetailsDTO.getPersonName(),
                    claimDetailsDTO.getPersonContact(),
                    claimDetailsDTO.getNic(),
                    claimDetailsDTO.getNote()
            );

            MailClaimPerson claimPerson = mailClaimPersonRepo.save(mailClaimPerson);

            MailClaimReference mailClaimReference = new MailClaimReference(
                    claimDetailsDTO.getReferenceNumber(),
                    claimPerson.getPersonID()
            );
 // Save person first
        mailClaimRepo.save(mailClaimReference);    // Then save reference



        return "Mail claim details saved successfully.";
    }

    public void insertMailRecordToDynamicTable(MailRecord mailRecord) throws SQLException {
        String tableName = mailRecord.getBranch().getBranchName().toLowerCase() + "_mailcart";
        String status = mailRecord.getStatus();

        try (Connection conn = dataSource.getConnection()) {
            // If the status is 'claimed', update the record in the dynamic table
            if ("claimed".equalsIgnoreCase(status)) {
                String updateSql = "UPDATE " + tableName + " SET " +
                        "status = ?, location = ?, reference_number = ? " +
                        "WHERE barcode_id = ?";

                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, "claimed");
                    ps.setString(2, mailRecord.getLocation()); // Use location from the claim details
                    ps.setString(3, mailRecord.getReferenceNumber());
                    ps.setString(4, mailRecord.getBarcodeId());

                    ps.executeUpdate();
                }

            } else if ("returned".equalsIgnoreCase(status)) {
                // If the status is 'returned', delete the record from the dynamic table
                String deleteSql = "DELETE FROM " + tableName + " WHERE barcode_id = ?";

                try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                    ps.setString(1, mailRecord.getBarcodeId());
                    ps.executeUpdate();
                }

            } else {
                // For other statuses, just insert the record (default case)
                String insertSql = "INSERT INTO " + tableName + " (" +
                        "sender_name, receiver_name, mail_type, tracking_number, barcode_id, " +
                        "mail_description, barcode_image, insert_date_time, update_date_time, " +
                        "branch_code, location, status, reference_number) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setString(1, mailRecord.getSenderName());
                    ps.setString(2, mailRecord.getReceiverName());
                    ps.setString(3, mailRecord.getMailType());
                    ps.setString(4, mailRecord.getTrackingNumber());
                    ps.setString(5, mailRecord.getBarcodeId());
                    ps.setString(6, mailRecord.getMailDescription());
                    ps.setBytes(7, mailRecord.getBarcodeImage());

                    // Use timestamps from the entity, or let DB set them by passing null (if default is set)
                    ps.setTimestamp(8, mailRecord.getInsertDateTime() != null ?
                            Timestamp.valueOf(mailRecord.getInsertDateTime()) : new Timestamp(System.currentTimeMillis()));
                    ps.setTimestamp(9, mailRecord.getUpdateDateTime() != null ?
                            Timestamp.valueOf(mailRecord.getUpdateDateTime()) : new Timestamp(System.currentTimeMillis()));

                    ps.setString(10, mailRecord.getBranch().getBranchCode());
                    ps.setString(11, mailRecord.getLocation()); // e.g., claimDetailsDTO.getBranchCode()
                    ps.setString(12, mailRecord.getStatus());   // e.g., "picked"
                    ps.setString(13, mailRecord.getReferenceNumber());

                    ps.executeUpdate();
                }
            }
        }
    }


}
