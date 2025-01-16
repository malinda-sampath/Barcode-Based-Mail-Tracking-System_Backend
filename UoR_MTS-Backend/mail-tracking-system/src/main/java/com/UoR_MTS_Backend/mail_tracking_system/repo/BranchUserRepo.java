package com.UoR_MTS_Backend.mail_tracking_system.repo;

import com.UoR_MTS_Backend.mail_tracking_system.model.BranchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchUserRepo extends JpaRepository<BranchUser, Integer> {
    boolean existsByBranchUserName(String branchUserName);
}

