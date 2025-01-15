package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface MailRecordService {

    public String transferDailyMailsToMainCart();

//    public Page<MailRecord> searchAndFilterMails(String cartType, String branchName, LocalDateTime date, int page, int size);
//
//    public MailRecord addMailRecord(MailRecordDTO mailRecordDTO);
}
