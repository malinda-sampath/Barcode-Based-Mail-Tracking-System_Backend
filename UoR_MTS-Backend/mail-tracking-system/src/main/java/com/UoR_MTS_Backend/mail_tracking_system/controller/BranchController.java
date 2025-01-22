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
            try{
                String message = branchService.branchSave(branchDTO);
                return ResponseBuilder.success(message, null);
            } catch (Exception e) {
                System.err.println("Error saving branch: " + e.getMessage());
                return ResponseBuilder.error("Error saving branch.", null);
            }

    }

    @GetMapping
    public ResponseEntity< StandardResponse <List<BranchDTO>>>getAllBranches() {
        try {
            List<BranchDTO> branches = branchService.getAllBranches();

            if (branches.isEmpty()) {
                return ResponseBuilder.notFound("Branches Not Found");
            }

            return ResponseBuilder.success("Branches Retrieve Successfully!", branches);

        }catch(Exception e){

            System.err.println("Error retrieving branches: " + e.getMessage());
            return  ResponseBuilder.error("Error Retrieving Branches!"+e.getMessage(),null);
        }
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<StandardResponse<BranchDTO>> getBranchById(@PathVariable int id) {
        try {
            BranchDTO branchDTO = branchService.getBranchById(id);

            if (branchDTO != null) {
                return ResponseBuilder.success("Branch found", branchDTO);
            } else {
                return ResponseBuilder.notFound("Branch Not Found");
            }
        }catch (Exception e){
            System.err.println("Error retrieving branch: " + e.getMessage());
            return ResponseBuilder.error("Branch Retrieved Error :"+e.getMessage(),null);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> branchUpdate(@PathVariable int id, @RequestBody BranchDTO branchDTO) {
            try {
                String message = branchService.updateBranchById(id, branchDTO);
                return ResponseBuilder.success(message,null);
            }catch(Exception e){
                System.err.println("Error updating branch: " + e.getMessage());
                return ResponseBuilder.error("Error Updating Branch :"+e.getMessage(),null);
            }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <StandardResponse<String>> deleteBranch(@PathVariable int id) {
            try{String message = branchService.deleteBranchById(id); // Call service method to delete the branch
                if(message!=null){
                    return ResponseBuilder.success(message,null);
                }else
                {
                    return ResponseBuilder.notFound("Branch Not Found With "+id);
                }

            } catch (Exception e) {

                System.err.println("Error deleting branch: " + e.getMessage());
                    return ResponseBuilder.error("Branch Deleting Error :"+e.getMessage() ,null );


            }
    }
}
