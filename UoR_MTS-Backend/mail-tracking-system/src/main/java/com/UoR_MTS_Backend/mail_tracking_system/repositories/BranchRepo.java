package com.UoR_MTS_Backend.mail_tracking_system.repositories;

import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface BranchRepo extends JpaRepository<Branch,String> {
    Optional<Branch> findAllByBranchName(String branchName);
    Branch findByBranchCode(String branchCode);
}
