package com.UoR_MTS_Backend.mail_tracking_system.repositories;

import com.UoR_MTS_Backend.mail_tracking_system.entities.DailyMail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface DailyMailRepo extends JpaRepository<DailyMail, Integer>, JpaSpecificationExecutor<DailyMail> {
    public List<DailyMail> findAllByBarcodeIdEquals(String barcodeId);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE daily_mails AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetAutoIncrement();

    DailyMail findByBarcodeId(String barcodeId);
}
