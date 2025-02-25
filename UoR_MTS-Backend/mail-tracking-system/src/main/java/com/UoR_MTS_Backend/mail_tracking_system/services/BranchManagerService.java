package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchManagerDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.BranchManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchManagerService {

    String branchManagerSave(BranchManagerDTO branchManagerDto);

    String branchManagerUpdate(int id, BranchManagerDTO branchManagerDto);

    String branchManagerDelete(int id);

    List<BranchManager> getAllBranchManagers();

    BranchManager getBranchManagerById(int branchManagerId);

}
