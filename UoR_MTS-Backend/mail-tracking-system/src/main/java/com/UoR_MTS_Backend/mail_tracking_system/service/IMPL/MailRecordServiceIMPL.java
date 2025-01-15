package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.config.ModelMapperConfig;
import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.repo.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repo.MailRecordRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailRecordService;
//import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Autowired
    private ModelMapperConfig modelMapperConfig;

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
        }

        return "All daily mails successfully transferred to the main mail cart.";
    }

    @Override
    public Page<MailRecord> searchAndFilterMails(String cartType, String branchName, LocalDateTime date, int page, int size) {
        // Ensure page and size are non-negative
//        if (page < 0 || size <= 0) {
//            throw new IllegalArgumentException("Page index must not be negative and size must be greater than zero.");
//        }
//
//        Pageable pageable = PageRequest.of(page,size);
//
//        if (branchName != null && date != null) {
//            //filter by cart type, branch name and date
//            return mailRecordRepo.findByCartTypeAndBranchNameAndInsertDateTime(cartType, branchName, date, pageable);
//        } else if (branchName != null) {
//            //filter by cart type and branch name
//            return mailRecordRepo.findByCartTypeAndBranchName(cartType, branchName, pageable);
//        } else if (date != null) {
//            //filter by cart type and date
//            return mailRecordRepo.findByCartTypeAndInsertDateTime(cartType, date, pageable);
//        } else {
//            //filter by cart type only
//            return mailRecordRepo.findByCartType(cartType, pageable);
//        }
        return null;
    }

    @Override
    public MailRecord addMailRecord(MailRecordDTO mailRecordDTO) {
        MailRecord mailRecord = new MailRecord();
        mailRecord.setBranchCode(mailRecordDTO.getBranchCode());
        mailRecord.setBranchName(mailRecordDTO.getBranchName());
        mailRecord.setMailType(mailRecordDTO.getMailType());
        mailRecord.setTrackingNumber(mailRecordDTO.getTrackingNumber());
        mailRecord.setBarcodeId(mailRecordDTO.getBarcodeId());
        mailRecord.setInsertDateTime(mailRecordDTO.getInsertDateTime());
        mailRecord.setMailDescription(mailRecordDTO.getMailDescription());
        mailRecord.setReceiverName(mailRecordDTO.getReceiverName());
        mailRecord.setSenderName(mailRecordDTO.getSenderName());

        return mailRecordRepo.save(mailRecord);
    }
}
