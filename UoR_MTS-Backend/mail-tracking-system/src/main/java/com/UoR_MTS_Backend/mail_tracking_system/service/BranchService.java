package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import org.springframework.http.ResponseEntity;

public interface BranchService {
    public ResponseEntity<String> branchSave(BranchDTO branchDTO);
}
