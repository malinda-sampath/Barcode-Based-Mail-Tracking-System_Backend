package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailRecordResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MailRecordService {

    public String transferDailyMailsToMailRecord();


    MailRecord searchMailByBarcodeId(String barcodeId);

//    Page<MailRecordDTO> filterMailRecords(String senderName, String receiverName, String mailType, String trackingNumber, String branchName, Pageable pageable);

    List<MailRecordResponseDTO> getAllMailRecords();

    List<MailRecordResponseDTO> getByBranch(String branchCode);
}
