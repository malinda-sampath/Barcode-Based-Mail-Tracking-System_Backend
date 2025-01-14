package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public String branchSave(@RequestBody BranchDTO branchDTO) {

            String message = branchService.branchSave(branchDTO);
            return message;
    }

    @GetMapping
    public List<BranchDTO> getAllBranches() {
        return branchService.getAllBranches();
    }

    @GetMapping("/view/{id}")
    public BranchDTO getBranchById(@PathVariable int id) {
        return branchService.getBranchById(id);
    }

    @PutMapping("/update/{id}")
    public String branchUpdate(@PathVariable int id, @RequestBody BranchDTO branchDTO) {

            String message = branchService.updateBranchById(id, branchDTO);
            return message;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBranch(@PathVariable int id) {
            String message = branchService.deleteBranchById(id); // Call service method to delete the branch
            return message;
    }
}
