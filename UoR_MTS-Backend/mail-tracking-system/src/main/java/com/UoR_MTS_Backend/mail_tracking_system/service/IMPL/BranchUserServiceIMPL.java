package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchUserDto;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchUserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchUserService;
import lombok.extern.flogger.Flogger;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchUserServiceIMPL implements BranchUserService {

    @Autowired
    private BranchUserRepo branchUserRepo;

    @Override
    public String branchUserSave(BranchUserDto branchUserDto) {

        if (branchUserDto == null) {
            throw new IllegalArgumentException("Branch user data cannot be null.");
        }


        if (branchUserDto.getBranchUserName() == null || branchUserDto.getBranchUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch user name cannot be null or empty.");
        }
        if (branchUserDto.getBranchUserPassword() == null || branchUserDto.getBranchUserPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch user password cannot be null or empty.");
        }
        if (branchUserDto.getBranchCode() == null || branchUserDto.getBranchCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Branch code cannot be null or empty.");
        }




        BranchUser branchUser = new BranchUser(
                branchUserDto.getBranchUserName(),
                branchUserDto.getBranchUserPassword(),
                branchUserDto.getBranchCode()
        );


        try {
            branchUserRepo.save(branchUser);
        }catch (Exception e){

            throw  new RuntimeException("Error Occured Saving Branch"+e.getMessage(),e);
        }


        return branchUserDto.getBranchUserName() + " has been saved successfully";
    }


    @Override
    public String branchUserUpdate(int id, BranchUserDto branchUserDto) {
try {
    BranchUser existingBranchUser = branchUserRepo.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Branch user not found with ID: " + id));


    if (branchUserDto == null) {
        throw new IllegalArgumentException("BranchUser Fields can not be Null");
    }
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
    return existingBranchUser.getBranchUserName() + " has been updated Successfully";


    }catch(UserNotFoundException ex)
    {

        throw ex;

    }catch(Exception e){
        throw new RuntimeException("Error Occure Updating User !");
    }




    }


    @Override
    public String branchUserDelete(int id){
        if (!branchUserRepo.existsById(id) ){
            throw new UserNotFoundException("Branch user not found with ID: " + id);
        }

        try {
            branchUserRepo.deleteById(id);

            return "Branch User Deleted Successfully";
        }catch(Exception e){

            throw new RuntimeException("Error Deleting BranchUser"+e.getMessage());
        }
    }

    @Override
    public List<BranchUser> getAllBranchUsers() {
        List<BranchUser> branchUsers =  branchUserRepo.findAll();
        if(branchUsers.isEmpty()){
            throw new UserNotFoundException("No Branches Found In The Database");
        }

        return  branchUsers;
    }

    @Override
    public BranchUser getBranchUserById(int branchUserId) {
        return branchUserRepo.findById(branchUserId)
                .orElseThrow(() -> new UserNotFoundException("Branch user not found with ID: " + branchUserId));
    }
}
