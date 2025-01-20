package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.TrackingDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface TrackingDetailsService {
    public String saveTrackingDetails(TrackingDetailsDTO trackingDetailsDTO);
}
