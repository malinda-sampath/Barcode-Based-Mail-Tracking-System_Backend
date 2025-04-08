package com.UoR_MTS_Backend.mail_tracking_system.repositories;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailRecordResponseDTO;

import java.util.List;

public interface MailRecordRepositoryCustom {
    List<MailRecordResponseDTO> getMailRecordsFromDynamicTable(String tableName);
}
