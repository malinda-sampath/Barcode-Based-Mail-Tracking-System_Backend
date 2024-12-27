package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/save")
    public String branchSave(@RequestBody BranchDTO branchDTO){
        String response = branchService.branchSave(branchDTO);
        return response;
    }
}
