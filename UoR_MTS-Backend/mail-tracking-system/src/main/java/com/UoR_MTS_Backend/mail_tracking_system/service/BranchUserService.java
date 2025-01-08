package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.BranchUserDto;
import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchUserService {

    String branchUserSave(BranchUserDto branchUserDto);

    String branchUserUpdate(int id, BranchUserDto branchUserDto);

    String branchUserDelete(int id);

    List<BranchUser> getAllBranchUsers();

    BranchUser getBranchUserById(int branchUserId);

}
