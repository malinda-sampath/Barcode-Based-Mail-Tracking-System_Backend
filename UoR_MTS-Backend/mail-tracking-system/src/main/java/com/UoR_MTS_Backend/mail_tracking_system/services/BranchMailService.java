package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailRecordResponseDTO;

import java.util.List;

public interface BranchMailService {
    List<MailRecordResponseDTO> getBranchMailTable(String branchCode);
}
