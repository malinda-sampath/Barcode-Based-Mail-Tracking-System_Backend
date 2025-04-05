package com.UoR_MTS_Backend.mail_tracking_system.repositories;

import com.UoR_MTS_Backend.mail_tracking_system.entities.TrackingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface TrackingDetailsRepo extends JpaRepository<TrackingDetails, Long> {
    Optional<TrackingDetails> findAllByMailTrackingNumber(String mailTrackingNumber);
}
