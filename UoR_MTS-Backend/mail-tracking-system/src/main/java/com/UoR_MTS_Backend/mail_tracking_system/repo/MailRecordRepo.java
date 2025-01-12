package com.UoR_MTS_Backend.mail_tracking_system.repo;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
//import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.ContentHandler;
import java.time.LocalDateTime;

public interface MailRecordRepo extends JpaRepository<MailRecord, Integer> {

    Page<MailRecord> findByCartType(String cartType, Pageable pageable);

    Page<MailRecord> findByCartTypeAndBranchName(String cartType, String branchName, Pageable pageable);

    Page<MailRecord> findByCartTypeAndInsertDateTime(String cartType, LocalDateTime date, Pageable pageable);

    Page<MailRecord> findByCartTypeAndBranchNameAndInsertDateTime(String cartType, String branchName, LocalDateTime date, Pageable pageable);
}
