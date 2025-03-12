package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.WebSocketController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.RequestBranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.websocketResponse.WCBranchUpdateDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.BranchAlreadyExistsException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.BranchNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.tableID.IDGenerator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BranchServiceIMPL implements BranchService {

    private final BranchRepo branchRepo;
    private final JdbcTemplate jdbcTemplate;
    private final ModelMapper modelMapper;
    private final WebSocketController webSocketController;
    private final IDGenerator idGenerator;

    @Override
    @Transactional
    public String branchSave(RequestBranchDTO requestBranchDTO) {
        String sanitizedBranchName = requestBranchDTO.getBranchName()
                .toLowerCase()
                .replaceAll("[^a-z0-9_]", "_");

        // Check if branch already exists (Avoid NPE)
        Optional<Branch> existingBranchOpt = branchRepo.findAllByBranchName(requestBranchDTO.getBranchName());

        if (existingBranchOpt.isPresent()) {
            throw new BranchAlreadyExistsException("Branch name '" + requestBranchDTO.getBranchName() + "' already exists.");
        }

        // Generate unique branch code
        String branchCode;
        do {
            branchCode = idGenerator.generateID("BR", 4  );
        } while (branchRepo.findByBranchCode(branchCode) != null); // No need to reassign `existingBranch`

        try {
            // Map DTO to Entity
            Branch branch = modelMapper.map(requestBranchDTO, Branch.class);
            branch.setInsertDate(LocalDateTime.now());
            branch.setBranchCode(branchCode); // Ensure branchCode is set

            // Save branch first
            Branch savedBranch = branchRepo.save(branch);
            BranchDTO branchDTO = modelMapper.map(savedBranch, BranchDTO.class);

            // Send branch update to WebSocket
            WCBranchUpdateDTO wcBranchUpdateDTO = new WCBranchUpdateDTO("save", branchDTO);
            webSocketController.sendBranchUpdate(wcBranchUpdateDTO);

            // Now create the table (Only if saving was successful)
            createMailCartTable(sanitizedBranchName);

            return "Branch saved successfully.";

        } catch (Exception e) {
            throw new RuntimeException("Error while processing the branch: " + e.getMessage(), e);
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
    public BranchDTO getBranchById(String id) {
        return branchRepo.findById(id)
                .map(branch -> modelMapper.map(branch, BranchDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found for id: " + id));
    }


    @Override
    @Transactional
    public String updateBranchById(String id, RequestBranchDTO requestBranchDTO) {
        Branch existingBranch = branchRepo.findById(id)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found!"));

        if (Objects.equals(existingBranch.getBranchName(), requestBranchDTO.getBranchName())) {
            throw new BranchAlreadyExistsException("Branch name '" + requestBranchDTO.getBranchName() + "' already exists.");
        }

        String oldSanitizedBranchName = existingBranch.getBranchName()
                .toLowerCase()
                .replaceAll("[^a-z0-9_]", "_");

        String newSanitizedBranchName = null;

        // Update fields if provided
        if (requestBranchDTO.getBranchName() != null) {
            existingBranch.setBranchName(requestBranchDTO.getBranchName());
            existingBranch.setDescription(requestBranchDTO.getBranchDescription());
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
        Branch branch = branchRepo.save(existingBranch);
        BranchDTO branchDTO = modelMapper.map(branch, BranchDTO.class);
        webSocketController.sendBranchUpdate(branchDTO);

        return "Branch " + existingBranch.getBranchName() + " updated successfully.";
    }


    @Override
    @Transactional
    public String deleteBranchByBranchCode(String branchCode) {
        Branch existingBranch = branchRepo.findByBranchCode(branchCode);
        if (existingBranch==null) {
            throw new BranchNotFoundException("Branch not found with ID: " + branchCode);
        } else {
            try {
               branchRepo.deleteBranchByBranchCode(branchCode);

                // Send branch update to WebSocket
                BranchDTO branchDTO = modelMapper.map(existingBranch, BranchDTO.class);
                WCBranchUpdateDTO wcBranchUpdateDTO = new WCBranchUpdateDTO("delete", branchDTO);
                webSocketController.sendBranchUpdate(wcBranchUpdateDTO);
            } catch (Exception e) {
                throw new RuntimeException("Error while deleting the branch: " + e.getMessage(), e);
            }
        }
        return "Branch deleted successfully.";
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
