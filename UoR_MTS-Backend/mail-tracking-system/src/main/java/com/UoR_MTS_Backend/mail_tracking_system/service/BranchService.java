package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.Branch;

import java.util.List;

public interface BranchService {
    public Branch branchSave(BranchDTO branchDTO);

    public List<BranchDTO> getAllBranches();

    BranchDTO getBranchById(int id);

    String updateBranchById(int id, BranchDTO branchDTO);

    void deleteBranchById(int id);
}
