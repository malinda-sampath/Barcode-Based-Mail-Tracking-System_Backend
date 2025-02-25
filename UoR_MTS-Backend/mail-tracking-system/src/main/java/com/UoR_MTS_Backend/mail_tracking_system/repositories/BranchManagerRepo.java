package com.UoR_MTS_Backend.mail_tracking_system.repositories;

import com.UoR_MTS_Backend.mail_tracking_system.entities.BranchManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchManagerRepo extends JpaRepository<BranchManager, Integer> {
    boolean existsByBranchManagerName(String branchManagerName);
}

