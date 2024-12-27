package com.UoR_MTS_Backend.mail_tracking_system.service.impl;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceIMPL implements BranchService {

    @Autowired
    private BranchRepo branchRepo;

    @Override
    public String branchSave(BranchDTO branchDTO) {
        Branch branch = new Branch(
                branchDTO.getBranchCode(),
                branchDTO.getBranchName(),
                branchDTO.getInsertDate(),
                branchDTO.getUpdateDate()
        );
        branchRepo.save(branch);
        return null;
    }
}
