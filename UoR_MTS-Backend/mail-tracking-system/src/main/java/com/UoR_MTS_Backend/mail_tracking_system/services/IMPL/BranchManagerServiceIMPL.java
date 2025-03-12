package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.BranchUserRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.BranchUserResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchManagerService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BranchManagerServiceIMPL implements BranchManagerService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String branchManagerUpdate(String id, BranchUserRequestDTO branchUserRequestDTO) {
        return userRepo.findById(id)
                .map(existingBranchManager -> {
                    modelMapper.map(branchUserRequestDTO, existingBranchManager);
                    existingBranchManager.setPassword(passwordEncoder.encode(branchUserRequestDTO.getPassword()));
                    userRepo.save(existingBranchManager);
                    return "Branch Manager updated successfully.";
                })
                .orElseThrow(() -> new UserNotFoundException("Branch Manager not found"));
    }

    @Override
    public String branchManagerDelete(String id){
        if (!userRepo.existsById(id) ){
            throw new UserNotFoundException("Branch Manager not found");
        }

        try {
            userRepo.deleteById(id);
            return "Branch Manager Deleted Successfully";
        }catch(Exception e){
            throw new RuntimeException("Error Deleting BranchManager"+e.getMessage());
        }
    }

    @Override
    public List<BranchUserResponseDTO> getAllBranchManagers() {
        List<User> users = userRepo.findAllByRole_Name(RoleEnum.BRANCH_MANAGER);

        if (users.isEmpty()) {
            throw new UserNotFoundException("No Branches Found In The Database");
        }

        return modelMapper.map(users, new TypeToken<List<BranchUserResponseDTO>>() {}.getType());
    }

    @Override
    public User getBranchManagerById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Branch Manager not found with ID: " + id));
    }
}
