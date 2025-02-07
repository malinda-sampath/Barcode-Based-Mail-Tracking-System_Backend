package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchManagerDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.BranchManager;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchManagerService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/branch-manager")
@AllArgsConstructor
public class BranchManagerController {

    private final BranchManagerService branchManagerService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> branchManagerSave(@RequestBody BranchManagerDTO branchManagerDto) {
        String message = branchManagerService.branchManagerSave(branchManagerDto);
        return ResponseBuilder.success(message, null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> branchManagerUpdate(@PathVariable Integer id, @RequestBody BranchManagerDTO branchManagerDto) {
        String message = branchManagerService.branchManagerUpdate(id, branchManagerDto);
        return ResponseBuilder.success(message, null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> branchManagerDelete(@PathVariable Integer id) {
        String message = branchManagerService.branchManagerDelete(id);
        return ResponseBuilder.success(message, null);
    }

    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<BranchManager>>> getAllBranchManagers() {
        List<BranchManager> branchManagers = branchManagerService.getAllBranchManagers();
        return ResponseBuilder.success("Branch managers retrieved successfully.", branchManagers);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StandardResponse<BranchManager>> getBranchManagerById(@PathVariable("id") Integer branchManagerId) {
        BranchManager branchManager = branchManagerService.getBranchManagerById(branchManagerId);
        return ResponseBuilder.success("Branch manager retrieved successfully.", branchManager);
    }
}
