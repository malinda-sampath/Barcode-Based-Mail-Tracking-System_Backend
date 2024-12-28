package com.UoR_MTS_Backend.mail_tracking_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailAdmin;


    public interface MailAdminRepo extends JpaRepository<MailAdmin, Long> {
        boolean existsByEmail(String email);
        boolean existsByUserName(String username);
    }

