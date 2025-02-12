package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.configs.ModelMapperConfig;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailBucketDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailRecordNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.NoDailyMailsFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.NoMailActivitiesFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailBucket;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailRecordRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.specification.MailRecordSpecification;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailRecordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class MailRecordServiceIMPL implements MailRecordService {

    private final MailRecordRepo mailRecordRepo;
    private final DailyMailRepo dailyMailRepo;
    private final ModelMapperConfig modelMapperConfig;

    @Override
    @Transactional
    public String transferDailyMailsToMainCart() {

        List<DailyMail> dailyMails = dailyMailRepo.findAll();

        if (dailyMails.isEmpty()) {
            // Throw the custom exception when no daily mails are found
            throw new NoDailyMailsFoundException("No daily mails to transfer.");
        } else {

            List<MailBucket> mailBucket = dailyMails.stream()
                    .map(dailyMail -> modelMapperConfig.modelMapper().map(dailyMail, MailBucket.class))
                    .toList();

            mailRecordRepo.saveAll(mailBucket);
            dailyMailRepo.deleteAll();
            dailyMailRepo.resetAutoIncrement();
        }

        return "All daily mails successfully transferred to the main mail cart.";
    }



    @Override
    @Transactional
    public Page<MailBucketDTO> filterMailRecords(
            String senderName,
            String receiverName,
            String mailType,
            String trackingNumber,
            String branchName,
            Pageable pageable) {

        // Filter records using Specification
        Page<MailBucket> filteredMailRecords = mailRecordRepo.findAll(
                MailRecordSpecification.filterBy(
                        senderName, receiverName, mailType, trackingNumber, branchName
                ), pageable);

        // Throw an exception if no records are found
        if (filteredMailRecords.isEmpty()) {
            throw new NoMailActivitiesFoundException("No mail activities found with the provided filters.");
        }

        // Map to DTO and return
        return filteredMailRecords.map(mail -> {
            MailBucketDTO dto = new MailBucketDTO();
            dto.setId(mail.getId());
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
    public MailBucket searchMailByBarcodeId(String barcodeId) {
        // Fetch the mail record by barcodeId
        MailBucket mailBucket = mailRecordRepo.findByBarcodeId(barcodeId);

        // If no record found, throw custom exception
        if (mailBucket == null) {
            throw new MailRecordNotFoundException("Mail with barcode ID " + barcodeId + " not found.");
        }

        return mailBucket; // Return the found mail record
    }


    @Override
    public Page<MailBucket> getAllMailRecords(Pageable pageable) {
        Page<MailBucket> mailPage = mailRecordRepo.findAll(pageable);

        return mailPage.map(mail -> new MailBucket(
                mail.getId(),
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


}
