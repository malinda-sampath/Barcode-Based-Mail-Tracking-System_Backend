package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchUserDto;
import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchUserServiceImpl implements BranchUserService{

    @Autowired
    private BranchUserRepo branchUserRepo;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Override
    public BranchUserDto branchUserSave(BranchUserDto branchUserDto) {
        //String hashedPassword = passwordEncoder.encode(branchUserDto.getBranchUserPassword());
        //branchUserDto.setBranchUserPassword(hashedPassword);
        BranchUser branchUser = new BranchUser(
                branchUserDto.getBranchUserId(),
                branchUserDto.getBranchUserName(),
                branchUserDto.getBranchUserPassword(),
                branchUserDto.getBranchCode()
        );
        BranchUser savedBranchUser = branchUserRepo.save(branchUser);

        BranchUserDto savedBranchUserDto = new BranchUserDto(
                savedBranchUser.getBranchUserId(),
                savedBranchUser.getBranchUserName(),
                savedBranchUser.getBranchUserPassword(),
                savedBranchUser.getBranchCode()
        );

        return savedBranchUserDto;
    }

    @Override
    public BranchUserDto branchUserUpdate(int id, BranchUserDto branchUserDto) {
        // Check if a BranchUser exists with the given ID
        BranchUser existingBranchUser = branchUserRepo.findById(String.valueOf(id))
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

        // Save the updated BranchUser to the database
        BranchUser updatedBranchUser = branchUserRepo.save(existingBranchUser);

        // Convert the updated entity to a DTO and return it
        return new BranchUserDto(
                updatedBranchUser.getBranchUserId(),
                updatedBranchUser.getBranchUserName(),
                updatedBranchUser.getBranchUserPassword(),
                updatedBranchUser.getBranchCode()
        );
    }


    @Override
    public void branchUserDelete(int id){
        if (!branchUserRepo.existsById(String.valueOf(id))) {
            throw new RuntimeException("Branch user not found with ID: " + id);
        }
        branchUserRepo.deleteById(String.valueOf(id));
    }

    @Override
    public void allBranchUserDelete() {
        branchUserRepo.deleteAll();
    }

    @Override
    public List<BranchUser> getAllBranchUsers() {
        return branchUserRepo.findAll();
    }

    @Override
    public BranchUser getBranchUserById(int branchUserId) {
        return branchUserRepo.findById(String.valueOf(branchUserId))
                .orElseThrow(() -> new RuntimeException("Branch user not found with ID: " + branchUserId));
    }


}
