package com.UoR_MTS_Backend.mail_tracking_system.service.impl;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceIMPL implements BranchService {

    @Autowired
    private BranchRepo branchRepo;

    // Constructor injection (not mandatory due to @Autowired but considered a good practice)
    public BranchServiceIMPL(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    @Override
    public ResponseEntity<String> branchSave(BranchDTO branchDTO) {
        // Map DTO to Entity
        Branch branch = new Branch();
        branch.setBranchCode(branchDTO.getBranchCode());
        branch.setBranchName(branchDTO.getBranchName());

        try {
            // Save branch to the database
            branchRepo.save(branch);
            return ResponseEntity.status(HttpStatus.CREATED).body("Branch created successfully.");
        } catch (ObjectOptimisticLockingFailureException e) {
            // Handle specific locking exception
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict occurred while saving the branch. Please try again.");
        } catch (Exception e) {
            // Handle generic exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the branch.");
        }
    }
}
