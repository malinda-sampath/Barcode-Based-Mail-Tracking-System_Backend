package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.BranchManagerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.BranchManagerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchManagerService {
    public String saveBranchManager(BranchManagerRequestDTO branchManagerRequestDTO);

    String branchManagerUpdate(String id, BranchManagerRequestDTO branchManagerRequestDTO);

    String branchManagerDelete(String id);

    List<BranchManagerResponseDTO> getAllBranchManagers();

    User getBranchManagerById(String id);

}
