package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchUserDto;
import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import com.UoR_MTS_Backend.mail_tracking_system.repo.BranchUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchUserServiceImpl implements BranchUserService{

    @Autowired
    private BranchUserRepo branchUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public BranchUserDto branchUserSave(BranchUserDto branchUserDto) {
        String hashedPassword = passwordEncoder.encode(branchUserDto.getBranchUserPassword());
        branchUserDto.setBranchUserPassword(hashedPassword);
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
    public BranchUserDto branchUserUpdate(String id, BranchUserDto branchUserDto) {
        String hashedPassword = passwordEncoder.encode(branchUserDto.getBranchUserPassword());
        branchUserDto.setBranchUserPassword(hashedPassword);
        BranchUser existingBranchUser = branchUserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch user not found with ID: " + id));
        existingBranchUser.setBranchUserName(branchUserDto.getBranchUserName());
        existingBranchUser.setBranchUserPassword(branchUserDto.getBranchUserPassword());
        existingBranchUser.setBranchCode(branchUserDto.getBranchCode());
        BranchUser updatedBranchUser = branchUserRepo.save(existingBranchUser);
        return null;
    }

    @Override
    public void branchUserDelete(String id){
        if (!branchUserRepo.existsById(id)) {
            throw new RuntimeException("Branch user not found with ID: " + id);
        }
        branchUserRepo.deleteById(id);
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
    public BranchUser getBranchUserById(String branchUserId) {
        Optional<BranchUser> branchUserOptional = branchUserRepo.findById(branchUserId);
        if (branchUserOptional.isPresent()) {
            return branchUserOptional.get();
        } else {
            throw new RuntimeException("Branch user not found with ID: " + branchUserId);
        }
    }

}
