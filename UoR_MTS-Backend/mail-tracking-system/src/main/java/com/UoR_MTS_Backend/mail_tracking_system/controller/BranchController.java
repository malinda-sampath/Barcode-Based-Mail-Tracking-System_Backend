package com.UoR_MTS_Backend.mail_tracking_system.controller;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> branchSave(@RequestBody BranchDTO branchDTO) {
                String message = branchService.branchSave(branchDTO);
                return ResponseBuilder.success(message, null);
    }

    @GetMapping
    public ResponseEntity<StandardResponse<List<BranchDTO>>> getAllBranches() {
        List<BranchDTO> branches = branchService.getAllBranches();
        return ResponseBuilder.success("Branches Retrieved Successfully!", branches);
    }



    @GetMapping("/view/{id}")
    public ResponseEntity<StandardResponse<BranchDTO>> getBranchById(@PathVariable int id) {
        BranchDTO branchDTO = branchService.getBranchById(id);
        return ResponseBuilder.success("Branch found", branchDTO);
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> branchUpdate(@PathVariable int id, @RequestBody BranchDTO branchDTO) {
        String message = branchService.updateBranchById(id, branchDTO);
        return ResponseBuilder.success(message, null);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> deleteBranch(@PathVariable int id) {
        String message = branchService.deleteBranchById(id);
        return ResponseBuilder.success(message, null);
    }

}
