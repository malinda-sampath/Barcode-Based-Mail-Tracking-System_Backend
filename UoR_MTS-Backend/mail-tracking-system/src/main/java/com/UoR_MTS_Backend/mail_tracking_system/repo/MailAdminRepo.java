
package com.UoR_MTS_Backend.mail_tracking_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailAdmin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



public interface MailAdminRepo extends JpaRepository<MailAdmin, Long> {

    // Find a MailAdmin by email
     Optional<MailAdmin> findByEmail(String email);

    // Check if a MailAdmin exists by email
    boolean existsByEmail(String email);

    // Check if a MailAdmin exists by username
    boolean existsByUserName(String username);

    // Delete a MailAdmin by email
    @Transactional
    @Modifying
    @Query("DELETE FROM MailAdmin m WHERE m.email = :email")
    void deleteByEmail(@Param("email") String email);
}