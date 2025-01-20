package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchUserDto;
import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchUserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchUserServiceIMPL implements BranchUserService {

    @Autowired
    private BranchUserRepo branchUserRepo;

    @Override
    public String branchUserSave(BranchUserDto branchUserDto) {
        BranchUser branchUser = new BranchUser(
                branchUserDto.getBranchUserName(),
                branchUserDto.getBranchUserPassword(),
                branchUserDto.getBranchCode()
        );

        branchUserRepo.save(branchUser);

        return branchUserDto.getBranchUserName()+" has been saved Successfully";
    }

    @Override
    public String branchUserUpdate(int id, BranchUserDto branchUserDto) {
        // Check if a BranchUser exists with the given ID
        BranchUser existingBranchUser = branchUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch user not found with ID: " + id));

        // Update the fields of the existing user
        if (branchUserDto.getBranchUserName() != null) {
            existingBranchUser.setBranchUserName(branchUserDto.getBranchUserName());
        }
        if (branchUserDto.getBranchUserPassword() != null) {
            existingBranchUser.setBranchUserPassword(branchUserDto.getBranchUserPassword());
        }
        if (branchUserDto.getBranchCode() != null) {
            existingBranchUser.setBranchCode(branchUserDto.getBranchCode());
        }

        branchUserRepo.save(existingBranchUser);

        // Convert the updated entity to a DTO and return it
        return existingBranchUser.getBranchUserName() + " has been updated Successfully";
    }


    @Override
    public String branchUserDelete(int id){
        if (!branchUserRepo.existsById(id) ){
            throw new RuntimeException("Branch user not found with ID: " + id);
        }
        branchUserRepo.deleteById(id);

        return "Branch User Deleted Successfully";
    }

    @Override
    public List<BranchUser> getAllBranchUsers() {
        return branchUserRepo.findAll();
    }

    @Override
    public BranchUser getBranchUserById(int branchUserId) {
        return branchUserRepo.findById(branchUserId)
                .orElseThrow(() -> new RuntimeException("Branch user not found with ID: " + branchUserId));
    }
}
