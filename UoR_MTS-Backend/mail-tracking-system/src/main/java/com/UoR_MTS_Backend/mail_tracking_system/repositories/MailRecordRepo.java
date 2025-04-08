package com.UoR_MTS_Backend.mail_tracking_system.repositories;

import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailRecord;
//import org.springdoc.core.converters.models.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface MailRecordRepo extends JpaRepository<MailRecord, Long>{
    Optional<MailRecord> findAllByBarcodeId(String barcodeId);
    Page<MailRecord> findAll(Specification<MailRecord> specification, Pageable pageable);

    MailRecord findByBarcodeId(String barcodeId);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE mail_record AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetAutoIncrement();

    List<MailRecord> findAllByBranch(Branch branch);

    List<MailRecord> findAllByBarcodeIdIn(List<String> barcodeID);
}

