package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchUserDto;
import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchUserService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/branch-user")
public class BranchUserController {

    @Autowired
    private BranchUserService branchUserService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> branchUserSave(@RequestBody BranchUserDto branchUserDto) {
        String message = branchUserService.branchUserSave(branchUserDto);
        return ResponseBuilder.success(message, null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> branchUserUpdate(@PathVariable Integer id, @RequestBody BranchUserDto branchUserDto) {
        String message = branchUserService.branchUserUpdate(id, branchUserDto);
        return ResponseBuilder.success(message, null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> branchUserDelete(@PathVariable Integer id) {
        String message = branchUserService.branchUserDelete(id);
        return ResponseBuilder.success(message, null);
    }

    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<BranchUser>>> getAllBranchUsers() {
        List<BranchUser> branchUsers = branchUserService.getAllBranchUsers();
        return ResponseBuilder.success("Branch users retrieved successfully.", branchUsers);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StandardResponse<BranchUser>> getBranchUserById(@PathVariable("id") Integer branchUserId) {
        BranchUser branchUser = branchUserService.getBranchUserById(branchUserId);
        return ResponseBuilder.success("Branch user retrieved successfully.", branchUser);
    }
}
