package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.BranchManagerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.BranchManagerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailHandlerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.exception.AlreadyExistsException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.RoleRepo;
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
    private final RoleRepo roleRepo;
    private final BranchRepo branchRepo;

    @Override
    public String saveBranchManager(BranchManagerRequestDTO branchManagerRequestDTO) {

        if (userRepo.findByEmail(branchManagerRequestDTO.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Email is already in use.");
        }

        User user = modelMapper.map(branchManagerRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(branchManagerRequestDTO.getPassword()));
        user.setRole(roleRepo.findByName(RoleEnum.BRANCH_MANAGER).orElseThrow(() -> new RuntimeException("Role not found")));
        user.setBranchCode(branchManagerRequestDTO.getBranchCode());

        try {
            userRepo.save(user);

            // Send the updated mail handler to the web socket
//            MailHandlerResponseDTO mailHandlerResponseDTO = modelMapper.map(user, MailHandlerResponseDTO.class);
//            WCMailHandlerUpdateDTO wcMailHandlerUpdateDTO = new WCMailHandlerUpdateDTO("save",mailHandlerResponseDTO);
//            webSocketController.sendMailHandlerUpdate(wcMailHandlerUpdateDTO);

            return "Mail Handler created successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error creating Mail Handler" + e.getMessage());
        }
    }

    @Override
    public String branchManagerUpdate(String id, BranchManagerRequestDTO branchManagerRequestDTO) {
        return userRepo.findById(id)
                .map(existingBranchManager -> {
                    modelMapper.map(branchManagerRequestDTO, existingBranchManager);
                    existingBranchManager.setPassword(passwordEncoder.encode(branchManagerRequestDTO.getPassword()));
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
    public List<BranchManagerResponseDTO> getAllBranchManagers() {
        List<User> users = userRepo.findAllByRole_Name(RoleEnum.BRANCH_MANAGER);

        if (users.isEmpty()) {
            throw new UserNotFoundException("No Branch Managers found.");
        }

        return users.stream().map(user -> {
            BranchManagerResponseDTO dto = modelMapper.map(user, BranchManagerResponseDTO.class);

            String branchCode = user.getBranchCode();
            dto.setBranchCode(branchCode);

            // Fetch branch name from branchRepo using branchCode
            Branch branch = branchRepo.findByBranchCode(branchCode);// Handle missing branches gracefully

            dto.setBranchName(branch.getBranchName());
            return dto;
        }).toList();
    }


    @Override
    public User getBranchManagerById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Branch Manager not found with ID: " + id));
    }
}
