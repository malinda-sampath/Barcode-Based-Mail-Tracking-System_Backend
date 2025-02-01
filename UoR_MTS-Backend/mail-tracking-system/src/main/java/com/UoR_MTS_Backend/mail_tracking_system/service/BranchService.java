package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.Branch;
import jakarta.transaction.Transactional;

import java.util.List;

public interface BranchService {
    public String branchSave(BranchDTO branchDTO);

    public List<BranchDTO> getAllBranches();

    BranchDTO getBranchById(int id);


    String updateBranchById(int id, BranchDTO branchDTO);

    String deleteBranchById(int id);
}
