package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailRecordResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailRecordNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.NoDailyMailsFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailRecordRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailRecordService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MailRecordServiceIMPL implements MailRecordService {

    private final MailRecordRepo mailRecordRepo;
    private final DailyMailRepo dailyMailRepo;
    private final BranchRepo branchRepo;

    @Override
    @Transactional
    public String transferDailyMailsToMailRecord() {
        List<DailyMail> dailyMails = dailyMailRepo.findAll();

        if (dailyMails.isEmpty()) {
            throw new NoDailyMailsFoundException("No daily mails to transfer.");
        }

        for (DailyMail dailyMail : dailyMails) {
                MailRecord newRecord = new MailRecord(
                        dailyMail.getSenderName(),
                        dailyMail.getReceiverName(),
                        dailyMail.getMailType(),
                        dailyMail.getTrackingNumber(),
                        dailyMail.getBarcodeId(),
                        dailyMail.getMailDescription(),
                        dailyMail.getBarcodeImage(),
                        dailyMail.getBranch()
                );
                mailRecordRepo.save(newRecord);
                dailyMailRepo.deleteById(dailyMail.getId());
        }

        dailyMailRepo.resetAutoIncrement();

        return "All daily mails successfully transferred to the main mail cart.";
    }








//    @Override
//    @Transactional
//    public Page<MailRecordDTO> filterMailRecords(
//            String senderName,
//            String receiverName,
//            String mailType,
//            String trackingNumber,
//            String branchName,
//            Pageable pageable) {

        // Filter records using Specification
//        Page<MailRecord> filteredMailRecords = mailRecordRepo.findAll(
//                MailRecordSpecification.filterBy(
//                        senderName, receiverName, mailType, trackingNumber, branchName
//                ), pageable);
//
//        // Throw an exception if no records are found
//        if (filteredMailRecords.isEmpty()) {
//            throw new NoMailActivitiesFoundException("No mail activities found with the provided filters.");
//        }
//
//        // Map to DTO and return
//        return filteredMailRecords.map(mail -> {
//            MailRecordDTO dto = new MailRecordDTO();
//            dto.setId(mail.getId());
//            dto.setSenderName(mail.getSenderName());
//            dto.setReceiverName(mail.getReceiverName());
//            dto.setMailType(mail.getMailType());
//            dto.setTrackingNumber(mail.getTrackingNumber());
//            dto.setBranchCode(mail.getBranch().getBranchCode());
//            dto.setInsertDateTime(mail.getInsertDateTime());
//            dto.setUpdateDateTime(mail.getUpdateDateTime());
//            return dto;
//        });
//    }



    @Override
    public MailRecord searchMailByBarcodeId(String barcodeId) {
        // Fetch the mail record by barcodeId
        MailRecord mailRecord = mailRecordRepo.findByBarcodeId(barcodeId);

        // If no record found, throw custom exception
        if (mailRecord == null) {
            throw new MailRecordNotFoundException("Mail with barcode ID " + barcodeId + " not found.");
        }

        return mailRecord; // Return the found mail record
    }


    @Override
    public List<MailRecordResponseDTO> getAllMailRecords() {
        List<MailRecord> mailRecords = mailRecordRepo.findAll();
        if (mailRecords.isEmpty()) {
            throw new MailRecordNotFoundException("No mail records found.");
        }

        return mailRecords.stream().map(mailRecord -> {
            MailRecordResponseDTO dto = new MailRecordResponseDTO();
            dto.setSenderName(mailRecord.getSenderName());
            dto.setReceiverName(mailRecord.getReceiverName());
            dto.setMailType(mailRecord.getMailType());
            dto.setTrackingNumber(mailRecord.getTrackingNumber());
            dto.setBarcodeId(mailRecord.getBarcodeId());
            dto.setMailDescription(mailRecord.getMailDescription());
            dto.setBarcodeImage(mailRecord.getBarcodeImage());
            dto.setInsertDateTime(mailRecord.getInsertDateTime());
            dto.setUpdateDateTime(mailRecord.getUpdateDateTime());
            dto.setLocation(mailRecord.getLocation());
            dto.setStatus(mailRecord.getStatus());
            dto.setReferenceNumber(mailRecord.getReferenceNumber());

            // Manually extract branch name
            if (mailRecord.getBranch() != null) {
                dto.setBranchName(mailRecord.getBranch().getBranchName());
            }

            return dto;
        }).toList();
    }

    @Override
    public List<MailRecordResponseDTO> getByBranch(String branchCode) {
        Branch branch =branchRepo.findByBranchCode(branchCode);
        List<MailRecord> mailRecords = mailRecordRepo.findAllByBranch(branch);
        if (mailRecords.isEmpty()) {
            throw new MailRecordNotFoundException("No mail records found.");
        }

        return mailRecords.stream().map(mailRecord -> {
            MailRecordResponseDTO dto = new MailRecordResponseDTO();
            dto.setSenderName(mailRecord.getSenderName());
            dto.setReceiverName(mailRecord.getReceiverName());
            dto.setMailType(mailRecord.getMailType());
            dto.setTrackingNumber(mailRecord.getTrackingNumber());
            dto.setBarcodeId(mailRecord.getBarcodeId());
            dto.setMailDescription(mailRecord.getMailDescription());
            dto.setBarcodeImage(mailRecord.getBarcodeImage());
            dto.setInsertDateTime(mailRecord.getInsertDateTime());
            dto.setUpdateDateTime(mailRecord.getUpdateDateTime());
            dto.setLocation(mailRecord.getLocation());
            dto.setStatus(mailRecord.getStatus());
            dto.setReferenceNumber(mailRecord.getReferenceNumber());

            // Manually extract branch name
            if (mailRecord.getBranch() != null) {
                dto.setBranchName(mailRecord.getBranch().getBranchName());
            }

            return dto;
        }).toList();
    }

}
