package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchUserDto;
import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchUserService;
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
    public ResponseEntity<String> branchUserSave(@RequestBody BranchUserDto branchUserDto){
        String message = String.valueOf(branchUserService.branchUserSave(branchUserDto));
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> branchUserUpdate(@PathVariable Integer id, @RequestBody BranchUserDto branchUserDto) {
        String message = String.valueOf(branchUserService.branchUserUpdate(id, branchUserDto));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> branchUserDelete(@PathVariable Integer id){
        branchUserService.branchUserDelete(id);
        return new ResponseEntity<>("Branch User Deleted Successfully",HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BranchUser>> getAllBranchUsers() {
        List<BranchUser> branchUsers = branchUserService.getAllBranchUsers();
        if (branchUsers.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no data is found
        }
        return ResponseEntity.ok(branchUsers); // Returns 200 with the list of users
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BranchUser> getBranchUserById(@PathVariable("id") Integer branchUserId) {
        try {
            BranchUser branchUser = branchUserService.getBranchUserById(branchUserId);
            return ResponseEntity.ok(branchUser); // Returns 200 with the branch user
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Returns 404 if branch user not found
        }
    }
}
