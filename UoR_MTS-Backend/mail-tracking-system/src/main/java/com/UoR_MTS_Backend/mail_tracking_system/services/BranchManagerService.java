package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.BranchUserRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.BranchUserResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchManagerService {

    String branchManagerUpdate(String id, BranchUserRequestDTO branchUserRequestDTO);

    String branchManagerDelete(String id);

    List<BranchUserResponseDTO> getAllBranchManagers();

    User getBranchManagerById(String id);

}
