package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailBucketDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailBucket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MailRecordService {

    public String transferDailyMailsToMainCart();


    MailBucket searchMailByBarcodeId(String barcodeId);

    Page<MailBucketDTO> filterMailRecords(String senderName, String receiverName, String mailType, String trackingNumber, String branchName, Pageable pageable);

    Page<MailBucket> getAllMailRecords(Pageable pageable);
}
