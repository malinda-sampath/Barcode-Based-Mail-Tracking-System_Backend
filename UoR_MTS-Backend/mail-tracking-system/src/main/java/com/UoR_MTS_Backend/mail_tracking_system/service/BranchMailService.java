package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;

import java.util.List;

public interface BranchMailService {
    String transferMailToBranchCart(String barcodeId);

    void updateMailRecordList(String barcodeId);

    List<MailRecordDTO> getMailsByBranch(String branch);
}
