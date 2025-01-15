package com.UoR_MTS_Backend.mail_tracking_system.repo;

import com.UoR_MTS_Backend.mail_tracking_system.model.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
//import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.net.ContentHandler;
import java.time.LocalDateTime;

public interface MailRecordRepo extends JpaRepository<MailRecord, Long>, JpaSpecificationExecutor<MailRecord> {
    Page<MailRecord> findAll(Specification<MailRecord> specification, Pageable pageable);

    MailRecord findByBarcodeId(String barcodeId);
}
