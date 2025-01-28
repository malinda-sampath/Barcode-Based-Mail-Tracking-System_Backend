package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.BranchAlreadyExistsException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.BranchNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.model.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    @Override
    @Transactional
    public String branchSave(BranchDTO branchDTO) {
        // Validate BranchDTO
        String sanitizedBranchName = branchDTO.getBranchName().toLowerCase().replaceAll("[^a-z0-9_]", "_");

        Branch existingBranch = branchRepo.findByBranchNameIgnoreCase(branchDTO.getBranchName());
        if (existingBranch != null) {
            throw new BranchAlreadyExistsException("Branch name '" + branchDTO.getBranchName() + "' already exists.");
        }

        // Map DTO to Entity
        Branch branch = new Branch();
        branch.setBranchCode(branchDTO.getBranchCode());
        branch.setBranchName(branchDTO.getBranchName());
        branch.setInsertDate(LocalDateTime.now());

        try {
            // Save branch to the database
            branchRepo.save(branch);

            String sql = getString(sanitizedBranchName);

            jdbcTemplate.execute(sql);

            return "Branch saved successfully.";

        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while saving the branch: " + e.getMessage(), e);
        }
    }

    private static String getString(String sanitizedBranchName) {
        String tableName = sanitizedBranchName + "_mailcart";

        // Create branch-specific mail cart table
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "barcode INT AUTO_INCREMENT PRIMARY KEY, " +
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
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE" +
                ")";
    }


    @Override
    public List<BranchDTO> getAllBranches() {
        List<BranchDTO> branches = branchRepo.findAll()
                .stream()
                .map(branch -> new BranchDTO(
                        branch.getBranchCode(),
                        branch.getBranchName(),
                        branch.getInsertDate(),
                        branch.getUpdateDate()
                ))
                .toList();

        if (branches.isEmpty()) {
            throw new BranchNotFoundException("No branches found in the system.");
        }

        return branches;
    }


    @Override
    public BranchDTO getBranchById(int id) {
        return branchRepo.findById(id)
                .map(branch -> new BranchDTO(
                        branch.getBranchCode(),
                        branch.getBranchName(),
                        branch.getInsertDate(),
                        branch.getInsertDate()))
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found for id: " + id));
    }


    @Override
    @Transactional
    public String updateBranchById(int id, BranchDTO branchDTO) {
        Branch existingBranch = branchRepo.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with ID: " + id));

        String oldSanitizedBranchName = existingBranch.getBranchName()
                .toLowerCase()
                .replaceAll("[^a-z0-9_]", "_");

        String newSanitizedBranchName = null;

        // Update fields if provided
        if (branchDTO.getBranchName() != null) {
            existingBranch.setBranchName(branchDTO.getBranchName());
            existingBranch.setUpdateDate(LocalDateTime.now());
            newSanitizedBranchName = branchDTO.getBranchName()
                    .toLowerCase()
                    .replaceAll("[^a-z0-9_]", "_");
        }

        if (newSanitizedBranchName != null && !newSanitizedBranchName.equals(oldSanitizedBranchName)) {
            String oldTableName = oldSanitizedBranchName + "_mailcart";
            String newTableName = newSanitizedBranchName + "_mailcart";

            String renameTableSQL = "RENAME TABLE " + oldTableName + " TO " + newTableName;

            jdbcTemplate.execute(renameTableSQL);
        }

        // Save the updated branch
        branchRepo.save(existingBranch);

        return "Branch " + existingBranch.getBranchName() + " updated successfully.";
    }


    @Override
    public String deleteBranchById(int id) {
        if (!branchRepo.existsById(id)) {
            throw new BranchNotFoundException("Branch not found with ID: " + id);
        }

        // Delete the branch
        return branchRepo.findById(id)
                .map(branch -> {
                    branchRepo.deleteById(id);
                    return "Branch " + branch.getBranchName() + " deleted successfully.";
                })
                .orElseThrow(() -> new BranchNotFoundException("Branch not found with ID: " + id));
    }

}
