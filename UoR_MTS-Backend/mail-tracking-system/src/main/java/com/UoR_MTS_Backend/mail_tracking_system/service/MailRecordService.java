package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MailRecordService {

    public String transferDailyMailsToMainCart();


    MailRecord searchMailByBarcodeId(String barcodeId);

    Page<MailRecordDTO> filterMailRecords(String senderName, String receiverName, String mailType, String trackingNumber, String branchName, Pageable pageable);

    Page<MailRecord> getAllMailRecords(Pageable pageable);
}
