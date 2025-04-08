package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailRecordResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailRecordRepositoryCustom;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchMailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BranchMailServiceIMPL implements BranchMailService {

    private final BranchRepo branchRepo;
    private MailRecordRepositoryCustom mailRecordRepositoryCustom;

    @Override
    public List<MailRecordResponseDTO> getBranchMailTable(String branchCode) {
        String branchName = branchRepo.getBranchByBranchCode(branchCode).getBranchName();
        String tableName = branchName.toLowerCase() + "_mailcart"; // adjust case/formatting if needed
        return mailRecordRepositoryCustom.getMailRecordsFromDynamicTable(tableName);
    }
}
