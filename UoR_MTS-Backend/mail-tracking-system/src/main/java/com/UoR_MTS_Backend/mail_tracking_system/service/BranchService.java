package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestBranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.Branch;
import jakarta.transaction.Transactional;

import java.util.List;

public interface BranchService {
    public String branchSave(RequestBranchDTO requestBranchDTO);

    public List<BranchDTO> getAllBranches();

    BranchDTO getBranchById(int id);


    String updateBranchById(int id, RequestBranchDTO requestBranchDTO);

    String deleteBranchById(int id);
}
