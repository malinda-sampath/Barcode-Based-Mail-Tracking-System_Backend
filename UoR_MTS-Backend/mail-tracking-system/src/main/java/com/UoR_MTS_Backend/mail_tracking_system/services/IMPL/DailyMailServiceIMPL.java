package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.configs.ModelMapperConfig;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailActivity;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailActivityRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.specification.DailyMailSpecification;
import com.UoR_MTS_Backend.mail_tracking_system.services.DailyMailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DailyMailServiceIMPL implements DailyMailService {

    private final ModelMapperConfig modelMapper;
    private final DailyMailRepo dailyMailRepo;
    private final MailActivityRepo mailActivityRepo;
    //Username and user ID should get from user session
    private final String mailUsername = "";
    private final int mailUserId= 0;

    @Override
    @Transactional
    public String addDailyMail(DailyMailDTO dailyMailDTO, byte[] barcodeImage, String uniqueID) {
        if (dailyMailDTO == null) {
            throw new IllegalArgumentException("DailyMailDTO cannot be null.");
        }


        if (dailyMailDTO.getSenderName() == null || dailyMailDTO.getReceiverName() == null) {
            throw new IllegalArgumentException("Sender and Receiver names cannot be null.");
        }


        LocalDateTime now = LocalDateTime.now();

        // Map the request DTO to the DailyMail entity and save fields
        DailyMail dailyMail = modelMapper.modelMapper().map(dailyMailDTO, DailyMail.class);
        dailyMail.setBarcodeId(uniqueID);
        dailyMail.setBarcodeImage(barcodeImage);
        dailyMail.setInsertDateTime(now);

        try {

            dailyMailRepo.save(dailyMail);
        } catch (Exception e) {

            throw new RuntimeException("Error occurred while saving daily mail: " + e.getMessage(), e);
        }


        MailActivity log = new MailActivity(
                mailUserId,
                mailUsername,
                "Insert",
                dailyMail.getBranchName(),
                dailyMail.getSenderName(),
                dailyMail.getReceiverName(),
                dailyMail.getBarcodeId(),
                now
        );

        try {
            mailActivityRepo.save(log);
        } catch (Exception e) {

            throw new RuntimeException("Error occurred while saving mail activity log: " + e.getMessage(), e);
        }

        return dailyMail.getBarcodeId() + " saved with barcode image!";
    }

    @Override
    @Transactional
    public String updateDailyMail(int id,DailyMailDTO dailyMailDTO, byte[] barcodeImage, String uniqueID) {

        if (dailyMailRepo.existsById(id)) {

            LocalDateTime now = LocalDateTime.now();

            // Fetch the existing DailyMail entity
            DailyMail existingMail = dailyMailRepo.getReferenceById(id);

            // Map the request DTO to the DailyMail entity and update fields
            modelMapper.modelMapper().map(dailyMailDTO, existingMail);
            existingMail.setUpdateDateTime(now);
            existingMail.setBarcodeId(existingMail.getBarcodeId());
            existingMail.setBarcodeImage(existingMail.getBarcodeImage());

            dailyMailRepo.save(existingMail);

            // Add the activity log
            MailActivity log = new MailActivity(
                    mailUserId,
                    mailUsername,
                    "Update",
                    existingMail.getBranchName(),
                    existingMail.getSenderName(),
                    existingMail.getReceiverName(),
                    //Shows the Existing Barcode ID then
                    existingMail.getBarcodeId(),
                    now
            );

            mailActivityRepo.save(log);

            return "Mail id: "+existingMail.getId()+ " Barcode: "+existingMail.getBarcodeId()+ " updated successfully";

        } else {
            throw new ResourceNotFoundException("No data found for that ID");
        }
    }

    @Override
    @Transactional
    public String deleteDailyMail(int dailyMailId) {
        if (dailyMailRepo.existsById(dailyMailId)){

        DailyMail existingMail = dailyMailRepo.findById(dailyMailId)
                .orElseThrow(() -> new ResourceNotFoundException("No data found for that id: " + dailyMailId));

        LocalDateTime now = LocalDateTime.now();


        String existingBarcodeId = existingMail.getBarcodeId();


        dailyMailRepo.deleteById(dailyMailId);


        MailActivity log = new MailActivity(
                mailUserId,
                mailUsername,
                "Delete",
                existingMail.getBranchName(),
                existingMail.getSenderName(),
                existingMail.getReceiverName(),
                existingBarcodeId,
                now
        );
        mailActivityRepo.save(log);

            return "Mail id: "+dailyMailId+ " Barcode: "+existingBarcodeId+" deleted successfully";

        } else {
            throw new RuntimeException("No data found for that id");
        }
    }

    @Override
    public List<RequestDailyMailViewAllDTO> getAllDailyMails() {
        List<DailyMail> mailList = dailyMailRepo.findAll();

        if (mailList.isEmpty()) {
            throw new ResourceNotFoundException("No daily mails found in the database.");
        }

        return mailList.stream()
                .sorted(Comparator.comparing(DailyMail::getId))
                .map(mail -> new RequestDailyMailViewAllDTO(
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
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDailyMailViewAllDTO> getAllDailyMailsByBarcodeId(String barcodeId) {
        List<DailyMail> mailList = dailyMailRepo.findAllByBarcodeIdEquals(barcodeId);


        if (mailList.isEmpty()) {
            throw new ResourceNotFoundException("No daily mails found for the given barcode ID: " + barcodeId);
        }


        return mailList.stream()
                .sorted(Comparator.comparing(DailyMail::getId))
                .map(mail -> new RequestDailyMailViewAllDTO(
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
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDailyMailViewAllDTO> filterDailyMail(
            String senderName,
            String receiverName,
            String mailType,
            String trackingNumber,
            String branchName) {

        List<DailyMail> filteredMail = dailyMailRepo.findAll(
                DailyMailSpecification.filterBy(
                        senderName,
                        receiverName,
                        mailType,
                        trackingNumber,
                        branchName
                )
        );

        if (filteredMail == null || filteredMail.isEmpty()) {
            throw new ResourceNotFoundException("No mail activities found with the provided filters.");
        }

        return filteredMail.stream()
                .sorted(Comparator.comparing(DailyMail::getId))
                .map(mail -> new RequestDailyMailViewAllDTO(
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
                ))
                .collect(Collectors.toList());
    }
}
