package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.model.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.repo.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repo.MailRecordRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailRecordService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MailRecordServiceIMPL implements MailRecordService {


    @Autowired
    private MailRecordRepo mailRecordRepo;
    @Autowired
    private DailyMailRepo dailyMailRepo;

    @Override
    @Transactional


    public String transferDailyMailToMainMail() {

        List<DailyMail> dailyMails = dailyMailRepo.findAll();

        if (dailyMails.isEmpty()) {
            return "No daily mails available for transfer.";
        }

        List<MailRecord> mainMails = dailyMails.stream().map(dailyMail -> {
            MailRecord mainMail = new MailRecord();
            mainMail.setBranchCode(dailyMail.getBranchCode());
            mainMail.setBranchName(dailyMail.getBranchName());
            mainMail.setSenderName(dailyMail.getSenderName());
            mainMail.setReceiverName(dailyMail.getReceiverName());
            mainMail.setMailType(dailyMail.getMailType());
            mainMail.setTrackingNumber(dailyMail.getTrackingNumber());
            mainMail.setMailDescription(dailyMail.getMailDescription());
            mainMail.setBarcodeId(dailyMail.getBarcodeId());
            mainMail.setBarcodeImage(dailyMail.getBarcodeImage());
            mainMail.setInsertDateTime(LocalDateTime.now());
            mainMail.setUpdateDateTime(LocalDateTime.now());
            return mainMail;
        }).toList();

        // Save all data to MainMail
        mailRecordRepo.saveAll(mainMails);

        // Delete all data from DailyMail
        dailyMailRepo.deleteAll();

        return "All daily mails have been successfully transferred to the main mail cart and deleted from the daily mail cart.";
    }
    @Override
    public Page<MailRecord> filterMailRecords(
            String senderName,
            String receiverName,
            String mailType,
            String trackingNumber,
            String branchName,
            Pageable pageable) {

        // Create a Specification to filter by the provided criteria
        Specification<MailRecord> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Start with a true predicate

            // Apply filters based on the provided criteria
            if (senderName != null && !senderName.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("senderName"), senderName));
            }

            if (receiverName != null && !receiverName.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("receiverName"), receiverName));
            }

            if (mailType != null && !mailType.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("mailType"), mailType));
            }

            if (trackingNumber != null && !trackingNumber.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("trackingNumber"), trackingNumber));
            }

            if (branchName != null && !branchName.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("branchName"), branchName));
            }

            return predicate;
        };
        Page<MailRecord> filteredMailRecords = mailRecordRepo.findAll(specification, pageable);

        // Map the filtered MailRecords to MailRecord (can add logging for response here as well)
        return filteredMailRecords;
    }

    @Override
    public MailRecord searchMailByBarcodeId(String barcodeId) {

        return mailRecordRepo.findByBarcodeId(barcodeId);
    }
}


