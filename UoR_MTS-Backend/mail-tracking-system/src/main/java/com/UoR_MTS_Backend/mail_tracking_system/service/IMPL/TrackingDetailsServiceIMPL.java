package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dto.request.TrackingDetailsRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.TrackingDetails;
import com.UoR_MTS_Backend.mail_tracking_system.repo.TrackingDetailsRepository;
import com.UoR_MTS_Backend.mail_tracking_system.service.TrackingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackingDetailsServiceIMPL implements TrackingDetailsService {

    @Autowired
    private TrackingDetailsRepository trackingDetailsRepository;

    @Override
    public TrackingDetailsRequestDTO saveTrackingDetails(TrackingDetailsRequestDTO trackingDetailsRequestDTO) {

        TrackingDetails trackingDetails = new TrackingDetails(
                trackingDetailsRequestDTO.getEmail(),
                trackingDetailsRequestDTO.getInsertedAt(),
                trackingDetailsRequestDTO.getMailType(),
                trackingDetailsRequestDTO.getMailTrackingNumber()
        );

        TrackingDetails savedTrackingDetails = trackingDetailsRepository.save(trackingDetails);

        TrackingDetailsRequestDTO savedTrackingDetailsRequestDTO = new TrackingDetailsRequestDTO(
                savedTrackingDetails.getEmail(),
                savedTrackingDetails.getInsertedAt(),
                savedTrackingDetails.getMailType(),
                savedTrackingDetails.getMailTrackingNumber()
        );
        return savedTrackingDetailsRequestDTO;
    }
}
