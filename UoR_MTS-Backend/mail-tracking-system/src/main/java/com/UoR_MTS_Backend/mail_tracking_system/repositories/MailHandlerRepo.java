
package com.UoR_MTS_Backend.mail_tracking_system.repositories;

import com.UoR_MTS_Backend.mail_tracking_system.entities.MailHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



public interface MailHandlerRepo extends JpaRepository<MailHandler, Long> {

    // Find a MailHandler by email
     Optional<MailHandler> findByEmail(String email);

    // Check if a MailHandler exists by email
    boolean existsByEmail(String email);

    // Check if a MailHandler exists by username
    boolean existsByUserName(String username);

    // Delete a MailHandler by email
    @Transactional
    @Modifying
    @Query("DELETE FROM MailHandler m WHERE m.email = :email")
    void deleteByEmail(@Param("email") String email);
}