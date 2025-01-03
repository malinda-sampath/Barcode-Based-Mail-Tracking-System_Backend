package com.UoR_MTS_Backend.mail_tracking_system.repo;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface MailActivityRepo extends JpaRepository<MailActivity, Integer> , JpaSpecificationExecutor<MailActivity> {
    public List<MailActivity> findAllByBarcodeIdEquals(String barcodeId);
}
