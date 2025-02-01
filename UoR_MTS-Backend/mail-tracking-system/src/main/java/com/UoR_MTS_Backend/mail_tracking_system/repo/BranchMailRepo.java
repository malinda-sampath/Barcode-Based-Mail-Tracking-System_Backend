package com.UoR_MTS_Backend.mail_tracking_system.repo;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface BranchMailRepo extends JpaRepository<MailRecord, Long>, JpaSpecificationExecutor<MailRecord> {
    void deleteMailRecordByBarcodeId(String barcodeId);

    MailRecord findByBarcodeId(String barcodeId);

    List<MailRecord> findByBranchName(String branch);
}
