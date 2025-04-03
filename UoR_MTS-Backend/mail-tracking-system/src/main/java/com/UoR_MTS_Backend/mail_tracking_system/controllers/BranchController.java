package com.UoR_MTS_Backend.mail_tracking_system.controllers;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.RequestBranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/branch")
@AllArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<String>> branchSave(@RequestBody RequestBranchDTO requestBranchDTO) {
        String message = branchService.branchSave(requestBranchDTO);
        return ResponseBuilder.success(message, null);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MAIL_HANDLER')")
    public ResponseEntity<StandardResponse<List<BranchDTO>>> getAllBranches() {
        List<BranchDTO> branches = branchService.getAllBranches();
        return ResponseBuilder.success("Branches Retrieved Successfully!", branches);
    }

    @GetMapping("/get/{branchCode}")
    public ResponseEntity<StandardResponse<BranchDTO>> getBranchById(@PathVariable(value = "branchCode") String id) {
        BranchDTO branchDTO = branchService.getBranchById(id);
        return ResponseBuilder.success(null, branchDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> branchUpdate(@PathVariable(value = "id")String id, @RequestBody RequestBranchDTO requestBranchDTO) {
        String message = branchService.updateBranchById(id, requestBranchDTO);
        return ResponseBuilder.success(message, null);
    }

    @DeleteMapping("/delete/{branchCode}")
    public ResponseEntity<StandardResponse<String>> deleteBranch(@PathVariable() String branchCode) {
        System.out.println("Branch Code: " + branchCode);
        String message = branchService.deleteBranchByBranchCode(branchCode);
        return ResponseBuilder.success(message, null);
    }
}
