package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.TrackingDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public interface TrackingDetailsService {
    public String saveTrackingDetails(TrackingDetailsDTO trackingDetailsDTO);
}
