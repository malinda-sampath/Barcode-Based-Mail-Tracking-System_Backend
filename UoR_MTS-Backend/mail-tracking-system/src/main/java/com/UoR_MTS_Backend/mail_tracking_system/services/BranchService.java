package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail.RequestBranchDTO;

import java.util.List;

public interface BranchService {
    public String branchSave(RequestBranchDTO requestBranchDTO);

    public List<BranchDTO> getAllBranches();

    BranchDTO getBranchById(int id);


    String updateBranchById(int id, RequestBranchDTO requestBranchDTO);

    String deleteBranchById(int id);
}
