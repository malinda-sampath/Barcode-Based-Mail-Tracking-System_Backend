package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchManagerDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.BranchManager;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchManagerRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchManagerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchManagerServiceIMPL implements BranchManagerService {

    @Autowired
    private BranchManagerRepo branchManagerRepo;

    @Override
    public String branchManagerSave(BranchManagerDTO branchManagerDto) {

        if (branchManagerDto == null) {
            throw new IllegalArgumentException("Branch Manager data cannot be null.");
        }


        if (branchManagerDto.getBranchManagerName() == null || branchManagerDto.getBranchManagerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch Manager name cannot be null or empty.");
        }
        if (branchManagerDto.getBranchManagerPassword() == null || branchManagerDto.getBranchManagerPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch Manager password cannot be null or empty.");
        }
        if (branchManagerDto.getBranchCode() == null || branchManagerDto.getBranchCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch code cannot be null or empty.");
        }




        BranchManager branchManager = new BranchManager(
                branchManagerDto.getBranchManagerName(),
                branchManagerDto.getBranchManagerPassword(),
                branchManagerDto.getBranchCode()
        );


        try {
            branchManagerRepo.save(branchManager);
        }catch (Exception e){

            throw  new RuntimeException("Error Occurred Saving Branch"+e.getMessage(),e);
        }


        return branchManagerDto.getBranchManagerName() + " has been saved successfully";
    }


    @Override
    public String branchManagerUpdate(int id, BranchManagerDTO branchManagerDto) {
    try {
        BranchManager existingBranchManager = branchManagerRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Branch Manager not found with ID: " + id));


        if (branchManagerDto == null) {
            throw new IllegalArgumentException("BranchManager Fields can not be Null");
        }
        if (branchManagerDto.getBranchManagerName() != null) {
            existingBranchManager.setBranchManagerName(branchManagerDto.getBranchManagerName());
        }
        if (branchManagerDto.getBranchManagerPassword() != null) {
            existingBranchManager.setBranchManagerPassword(branchManagerDto.getBranchManagerPassword());
        }
        if (branchManagerDto.getBranchCode() != null) {
            existingBranchManager.setBranchCode(branchManagerDto.getBranchCode());
        }

        branchManagerRepo.save(existingBranchManager);
        return existingBranchManager.getBranchManagerName() + " has been updated Successfully";


        }catch(UserNotFoundException ex)
        {

            throw ex;

        }catch(Exception e){
            throw new RuntimeException("Error Occure Updating Manager !");
        }
    }


    @Override
    public String branchManagerDelete(int id){
        if (!branchManagerRepo.existsById(id) ){
            throw new UserNotFoundException("Branch Manager not found with ID: " + id);
        }

        try {
            branchManagerRepo.deleteById(id);

            return "Branch Manager Deleted Successfully";
        }catch(Exception e){

            throw new RuntimeException("Error Deleting BranchManager"+e.getMessage());
        }
    }

    @Override
    public List<BranchManager> getAllBranchManagers() {
        List<BranchManager> branchManagers =  branchManagerRepo.findAll();
        if(branchManagers.isEmpty()){
            throw new UserNotFoundException("No Branches Found In The Database");
        }

        return branchManagers;
    }

    @Override
    public BranchManager getBranchManagerById(int branchManagerId) {
        return branchManagerRepo.findById(branchManagerId)
                .orElseThrow(() -> new UserNotFoundException("Branch Manager not found with ID: " + branchManagerId));
    }
}
