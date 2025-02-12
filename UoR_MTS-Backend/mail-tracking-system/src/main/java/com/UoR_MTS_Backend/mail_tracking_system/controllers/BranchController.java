package com.UoR_MTS_Backend.mail_tracking_system.controllers;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.RequestBranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/branch")
@AllArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> branchSave(@RequestBody RequestBranchDTO requestBranchDTO) {
                String message = branchService.branchSave(requestBranchDTO);
                return ResponseBuilder.success(message, null);
    }

    @GetMapping
    public ResponseEntity<StandardResponse<List<BranchDTO>>> getAllBranches() {
        List<BranchDTO> branches = branchService.getAllBranches();
        return ResponseBuilder.success("Branches Retrieved Successfully!", branches);
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<StandardResponse<BranchDTO>> getBranchById(@PathVariable(value = "id") int id) {
        BranchDTO branchDTO = branchService.getBranchById(id);
        return ResponseBuilder.success(null, branchDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> branchUpdate(@PathVariable(value = "id")int id, @RequestBody RequestBranchDTO requestBranchDTO) {
        String message = branchService.updateBranchById(id, requestBranchDTO);
        return ResponseBuilder.success(message, null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> deleteBranch(@PathVariable() int id) {
        String message = branchService.deleteBranchById(id);
        return ResponseBuilder.success(message, null);
    }
}
