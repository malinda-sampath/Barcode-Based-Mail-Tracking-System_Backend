package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail.RequestBranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.BranchAlreadyExistsException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.BranchNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BranchServiceIMPL implements BranchService {

    private final BranchRepo branchRepo;
    private final JdbcTemplate jdbcTemplate;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public String branchSave(RequestBranchDTO requestBranchDTO) {
        String sanitizedBranchName = requestBranchDTO.getBranchName().toLowerCase().replaceAll("[^a-z0-9_]", "_");
        Branch existingBranchName = branchRepo.findByBranchNameIgnoreCase(requestBranchDTO.getBranchName());
        Branch existingBranchCode = branchRepo.findByBranchCode(requestBranchDTO.getBranchCode());

        if (existingBranchName != null) {
            throw new BranchAlreadyExistsException("Branch name '" + requestBranchDTO.getBranchName() + "' already exists.");
        }

        if (existingBranchCode != null) {
            throw new BranchAlreadyExistsException("Branch code '" + requestBranchDTO.getBranchCode() + "' already exists.");
        }

        try {
            // First, create the table (DDL statement)
            createMailCartTable(sanitizedBranchName);

            // Map DTO to Entity
            Branch branch = modelMapper.map(requestBranchDTO, Branch.class);

            // Save branch to the database
            branchRepo.save(branch);

            return "Branch saved successfully.";

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while processing the branch: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void createMailCartTable(String sanitizedBranchName) {
        jdbcTemplate.execute(getString(sanitizedBranchName));
    }


    @Override
    public List<BranchDTO> getAllBranches() {
        List<BranchDTO> branches = branchRepo.findAll()
                .stream()
                .map(branch -> modelMapper.map(branch, BranchDTO.class))
                .toList();

        if (branches.isEmpty()) {
            throw new BranchNotFoundException("No branches found in the system.");
        }
        return branches;
    }


    @Override
    public BranchDTO getBranchById(int id) {
        return branchRepo.findById(id)
                .map(branch -> modelMapper.map(branch, BranchDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found for id: " + id));
    }


    @Override
    @Transactional
    public String updateBranchById(int id, RequestBranchDTO requestBranchDTO) {
        Branch existingBranch = branchRepo.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found!"));

        if (Objects.equals(existingBranch.getBranchName(), requestBranchDTO.getBranchName())) {
            throw new BranchAlreadyExistsException("Branch name '" + requestBranchDTO.getBranchName() + "' already exists.");
        }

        if (Objects.equals(existingBranch.getBranchCode(), requestBranchDTO.getBranchCode())) {
            throw new BranchAlreadyExistsException("Branch code '" + requestBranchDTO.getBranchCode() + "' already exists.");
        }

        String oldSanitizedBranchName = existingBranch.getBranchName()
                .toLowerCase()
                .replaceAll("[^a-z0-9_]", "_");

        String newSanitizedBranchName = null;

        // Update fields if provided
        if (requestBranchDTO.getBranchName() != null) {
            existingBranch.setBranchName(requestBranchDTO.getBranchName());
            existingBranch.setUpdateDate(LocalDateTime.now());
            newSanitizedBranchName = requestBranchDTO.getBranchName()
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

    // Create branch-specific mail cart table
    private static String getString(String sanitizedBranchName) {
        String tableName = sanitizedBranchName + "_mailcart";

        // Create branch-specific mail cart table
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "barcode INT ," +
                "branch_code VARCHAR(255) NOT NULL, " +
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
}
