package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.BranchManagerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.BranchManagerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchManagerService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/branch-manager")
@AllArgsConstructor
public class BranchManagerController {

    private final BranchManagerService branchManagerService;


    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<String>> registerMailHandler(@RequestBody BranchManagerRequestDTO branchManagerRequestDTO) {
        String message = branchManagerService.saveBranchManager(branchManagerRequestDTO);
        return ResponseBuilder.success(message,null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> branchManagerUpdate(@PathVariable String id, @RequestBody BranchManagerRequestDTO branchManagerRequestDTO) {
        String message = branchManagerService.branchManagerUpdate(id, branchManagerRequestDTO);
        return ResponseBuilder.success(message, null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> branchManagerDelete(@PathVariable String id) {
        String message = branchManagerService.branchManagerDelete(id);
        return ResponseBuilder.success(message, null);
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<List<BranchManagerResponseDTO>>> getAllBranchManagers() {
        List<BranchManagerResponseDTO> branchManagers = branchManagerService.getAllBranchManagers();
        return ResponseBuilder.success("Branch managers retrieved successfully.", branchManagers);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StandardResponse<User>> getBranchManagerById(@PathVariable("id") String id) {
        User branchManager = branchManagerService.getBranchManagerById(id);
        return ResponseBuilder.success("Branch manager retrieved successfully.", branchManager);
    }
}
