package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.request.TrackingDetailsRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface TrackingDetailsService {
    TrackingDetailsRequestDTO saveTrackingDetails(TrackingDetailsRequestDTO trackingDetailsRequestDTO);
}
