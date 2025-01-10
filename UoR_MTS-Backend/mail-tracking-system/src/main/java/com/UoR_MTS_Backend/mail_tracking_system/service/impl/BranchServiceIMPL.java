package com.UoR_MTS_Backend.mail_tracking_system.service.impl;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BranchServiceIMPL implements BranchService {

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Constructor injection (not mandatory due to @Autowired but considered a good practice)
    public BranchServiceIMPL(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    @Override
    @Transactional
    public ResponseEntity<String> branchSave(BranchDTO branchDTO) {
        // Validate BranchDTO
        if (branchDTO.getBranchName() == null || branchDTO.getBranchName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Branch name cannot be null or empty.");
        }

        String sanitizedBranchName = branchDTO.getBranchName().toLowerCase().replace(" ", "");

        // Map DTO to Entity
        Branch branch = new Branch();
        branch.setBranchCode(branchDTO.getBranchCode());
        branch.setBranchName(branchDTO.getBranchName());
        branch.setUpdateDate(null);

        try {
            // Save branch to the database
            branchRepo.save(branch);

            String tableName = sanitizedBranchName /*+"_"+ branch.getBranchCode()*/ + "_mailcart";

            // Create branch-specific mail cart table
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "barcode INT PRIMARY KEY AUTO_INCREMENT, " +
                    "branch_code INT NOT NULL, " +
                    "sender VARCHAR(255), " +
                    "receiver VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "mail_type VARCHAR(50), " +
                    "postal_code INT, " +
                    "tracking_code INT, " +
                    "received_date DATE, " +
                    "claimed_date DATE, " +
                    "claimed_person VARCHAR(255), " +
                    "FOREIGN KEY (branch_code) REFERENCES branches(branch_code) " +
                    "ON DELETE CASCADE "+
                    "ON UPDATE CASCADE" +
                    ")";
            jdbcTemplate.execute(sql);

            return ResponseEntity.status(HttpStatus.CREATED).body("Branch and corresponding mail cart table created successfully.");
        } catch (DataAccessException e) {
            // Rollback transaction if table creation fails
            throw new RuntimeException("Error occurred while creating the table: " + e.getMessage(), e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("An error occurred while saving the branch: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BranchDTO> getAllBranches() {
        return branchRepo.findAll()
                .stream()
                .map(branch -> new BranchDTO(branch.getBranchCode(), branch.getBranchName(), branch.getInsertDate(),branch.getUpdateDate()))
                .toList();
    }

    @Override
    public BranchDTO getBranchById(int id) {
        return branchRepo.findById(id)
                .map(branch -> new BranchDTO(branch.getBranchCode(), branch.getBranchName(), branch.getInsertDate(),branch.getInsertDate()))
                .orElse(null);
    }

    @Override
    public String updateBranchById(int id, BranchDTO branchDTO) {
        Branch existingBranch = branchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + id));

        // Update fields
        if (branchDTO.getBranchName() != null) {
            existingBranch.setBranchName(branchDTO.getBranchName());
            existingBranch.setUpdateDate(LocalDateTime.now());
        }
        System.out.println(existingBranch);

        // Save updated branch
        branchRepo.save(existingBranch);
        return "Branch updated successfully.";
    }

    @Override
    public void deleteBranchById(int id) {
        if (!branchRepo.existsById(id)) {
            throw new RuntimeException("Branch not found with ID: " + id);
        }

        // Delete the branch
        branchRepo.deleteById(id);
    }
}
