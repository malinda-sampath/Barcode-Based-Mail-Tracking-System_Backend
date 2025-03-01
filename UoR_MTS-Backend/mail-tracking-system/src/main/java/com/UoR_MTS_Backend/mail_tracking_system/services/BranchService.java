package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.RequestBranchDTO;

import java.util.List;

public interface BranchService {
    public String branchSave(RequestBranchDTO requestBranchDTO);

    public List<BranchDTO> getAllBranches();

    BranchDTO getBranchById(String id);

    String updateBranchById(String id, RequestBranchDTO requestBranchDTO);

    String deleteBranchByBranchCode(String branchCode);
}
