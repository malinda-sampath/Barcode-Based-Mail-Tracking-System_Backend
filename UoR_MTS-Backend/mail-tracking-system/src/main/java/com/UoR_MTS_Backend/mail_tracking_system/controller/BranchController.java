package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.DuplicateBranchNameException;
import com.UoR_MTS_Backend.mail_tracking_system.model.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/save")
    public ResponseEntity<?> branchSave(@RequestBody BranchDTO branchDTO) {
        try {
            Branch savedBranch = branchService.branchSave(branchDTO);
            return ResponseEntity.ok(savedBranch);
        } catch (DuplicateBranchNameException e) {
            // Custom exception handling
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @GetMapping("/view-all")
    public List<BranchDTO> getAllBranches() {
        return branchService.getAllBranches();
    }

    @GetMapping("/view/{id}")
    public BranchDTO getBranchById(@PathVariable int id) {
        return branchService.getBranchById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> branchUpdate(@PathVariable int id, @RequestBody BranchDTO branchDTO) {
        try {
            String response = branchService.updateBranchById(id, branchDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the branch.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable int id) {
        try {
            branchService.deleteBranchById(id); // Call service method to delete the branch
            return ResponseEntity.ok("Branch deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the branch.");
        }
    }


}
