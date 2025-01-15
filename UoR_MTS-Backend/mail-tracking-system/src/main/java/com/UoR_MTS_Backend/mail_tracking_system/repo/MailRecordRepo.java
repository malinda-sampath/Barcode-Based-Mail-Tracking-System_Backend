package com.UoR_MTS_Backend.mail_tracking_system.repo;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
//import org.springdoc.core.converters.models.Pageable;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;
import java.time.LocalDateTime;

@Repository
@EnableJpaRepositories
public interface MailRecordRepo extends JpaRepository<MailRecord, Integer> {
//
//    Page<MailRecord> findByCartType(String cartType, Pageable pageable);
//
//    Page<MailRecord> findByCartTypeAndBranchName(String cartType, String branchName, Pageable pageable);
//
//    Page<MailRecord> findByCartTypeAndInsertDateTime(String cartType, LocalDateTime date, Pageable pageable);
//
//    Page<MailRecord> findByCartTypeAndBranchNameAndInsertDateTime(String cartType, String branchName, LocalDateTime date, Pageable pageable);
}
