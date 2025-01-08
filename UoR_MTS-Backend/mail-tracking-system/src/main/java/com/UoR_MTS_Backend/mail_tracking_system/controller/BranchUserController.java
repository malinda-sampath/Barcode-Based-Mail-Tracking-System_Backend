package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchUserDto;
import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchUserService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            String message = branchUserService.branchUserSave(branchUserDto);
            return ResponseBuilder.success(message, null);

        } catch (Exception e) {
            // Log the exception (optional)
            System.err.println("Error saving branch user: " + e.getMessage());

            return ResponseBuilder.error("Error saving branch user.", null);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> branchUserUpdate(@PathVariable Integer id, @RequestBody BranchUserDto branchUserDto) {
        try {
            String message = String.valueOf(branchUserService.branchUserUpdate(id, branchUserDto));
            return ResponseBuilder.success(message, null);

        } catch (Exception e) {
            // Log the exception (optional)
            System.err.println("Error updating branch user: " + e.getMessage());

            return ResponseBuilder.error("Error updating branch user.", null);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> branchUserDelete(@PathVariable Integer id) {
        try {
            String message = branchUserService.branchUserDelete(id);

            // If the message indicates a successful deletion
            if (message != null) {
                return ResponseBuilder.success(message, null);
            } else {
                return ResponseBuilder.error("Branch User with ID " + id + " not found.", null); // If user not found
            }

        } catch (Exception e) {
            // Log the exception (optional)
            System.err.println("Error deleting branch user: " + e.getMessage());

            return ResponseBuilder.error("Error deleting branch user.", null);
        }
    }



    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<BranchUser>>> getAllBranchUsers() {
        try {
            List<BranchUser> branchUsers = branchUserService.getAllBranchUsers();
            if (branchUsers.isEmpty()) {
                return ResponseBuilder.success("No branch users found.", branchUsers); // Success response with empty list
            }
            return ResponseBuilder.success("Branch users retrieved successfully.", branchUsers); // Success response with data

        } catch (Exception e) {
            // Log the exception (optional)
            System.err.println("Error retrieving branch users: " + e.getMessage());

            return ResponseBuilder.error("Error retrieving branch users.", null);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<StandardResponse<BranchUser>> getBranchUserById(@PathVariable("id") Integer branchUserId) {
        try {
            BranchUser branchUser = branchUserService.getBranchUserById(branchUserId);
            return ResponseBuilder.success("Branch user retrieved successfully.", branchUser); // Success response with data
        } catch (RuntimeException e) {
            // Log the exception (optional)
            System.err.println("Error retrieving branch user: " + e.getMessage());

            return ResponseBuilder.notFound("Branch user not found."); // Error response with message
        }
    }

}
