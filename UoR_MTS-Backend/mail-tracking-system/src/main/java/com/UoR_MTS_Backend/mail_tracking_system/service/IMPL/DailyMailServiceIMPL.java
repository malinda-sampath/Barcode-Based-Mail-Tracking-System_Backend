package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.config.ModelMapperConfig;
import com.UoR_MTS_Backend.mail_tracking_system.dto.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailActivity;
import com.UoR_MTS_Backend.mail_tracking_system.repo.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repo.MailActivityRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repo.specification.DailyMailSpecification;
import com.UoR_MTS_Backend.mail_tracking_system.service.DailyMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyMailServiceIMPL implements DailyMailService {

    @Autowired
    private ModelMapperConfig modelMapper;

    @Autowired
    private DailyMailRepo dailyMailRepo;

    @Autowired
    private MailActivityRepo mailActivityRepo;

    //Username and user ID should get from user session
    private String mailUsername = "";
    private int mailUserId= 0;

    @Override
    @Transactional
    public String addDailyMail(DailyMailDTO dailyMailDTO, byte[] barcodeImage, String uniqueID) {
        LocalDateTime now = LocalDateTime.now();

        // Map the request DTO to the DailyMail entity and save fields
        DailyMail dailyMail = modelMapper.modelMapper().map(dailyMailDTO, DailyMail.class);
        dailyMail.setBarcodeId(uniqueID);
        dailyMail.setBarcodeImage(barcodeImage);
        dailyMail.setInsertDateTime(now);

        dailyMailRepo.save(dailyMail);

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

        mailActivityRepo.save(log);

        return dailyMail.getBarcodeId() + " saved with barcode Image!";
    }

    @Override
    @Transactional
    public String updateDailyMail(RequestDailyMailDTO requestDailyMailDTO, byte[] barcodeImage, String uniqueID) {

        if (dailyMailRepo.existsById(requestDailyMailDTO.getDailyMailId())) {

            LocalDateTime now = LocalDateTime.now();

            // Fetch the existing DailyMail entity
            DailyMail existingMail = dailyMailRepo.findById(requestDailyMailDTO.getDailyMailId())
                    .orElseThrow(() -> new RuntimeException("No data found for that id"));

            // Map the request DTO to the DailyMail entity and update fields
            DailyMail dailyMail = modelMapper.modelMapper().map(requestDailyMailDTO, DailyMail.class);
            dailyMail.setUpdateDateTime(now);
            dailyMail.setBarcodeId(existingMail.getBarcodeId());
            dailyMail.setBarcodeImage(existingMail.getBarcodeImage());

            dailyMailRepo.save(dailyMail);

            // Add the activity log
            MailActivity log = new MailActivity(
                    mailUserId,
                    mailUsername,
                    "Update",
                    dailyMail.getBranchName(),
                    dailyMail.getSenderName(),
                    dailyMail.getReceiverName(),
                    //Shows the Existing Barcode ID then
                    existingMail.getBarcodeId(),
                    now
            );

            mailActivityRepo.save(log);

            return "Mail id: "+dailyMail.getDailyMailId()+ " Barcode: "+dailyMail.getBarcodeId()+ " updated successfully";

        } else {
            throw new RuntimeException("No data found for that ID");
        }
    }

    @Override
    @Transactional
    public String deleteDailyMail(int dailyMailId) {
        if (dailyMailRepo.existsById(dailyMailId)){

            DailyMail existingMail = dailyMailRepo.findById(dailyMailId)
                    .orElseThrow(() -> new RuntimeException("No data found for that id"));

            LocalDateTime now = LocalDateTime.now();

            // Get the existing barcode ID
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
        return mailList.stream()
                .sorted(Comparator.comparing(DailyMail::getDailyMailId))
                .map(mail -> new RequestDailyMailViewAllDTO(
                        mail.getDailyMailId(),
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
        return mailList.stream()
                .sorted(Comparator.comparing(DailyMail::getDailyMailId))
                .map(mail -> new RequestDailyMailViewAllDTO(
                        mail.getDailyMailId(),
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

        return filteredMail.stream()
                .sorted(Comparator.comparing(DailyMail::getDailyMailId))
                .map(mail -> new RequestDailyMailViewAllDTO(
                        mail.getDailyMailId(),
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