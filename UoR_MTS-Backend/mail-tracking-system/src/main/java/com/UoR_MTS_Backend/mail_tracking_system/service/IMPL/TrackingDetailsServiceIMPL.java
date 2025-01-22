package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dto.TrackingDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.TrackingDetails;
import com.UoR_MTS_Backend.mail_tracking_system.repo.TrackingDetailsRepository;
import com.UoR_MTS_Backend.mail_tracking_system.service.TrackingDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackingDetailsServiceIMPL implements TrackingDetailsService {

    @Autowired
    private TrackingDetailsRepository trackingDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String saveTrackingDetails(TrackingDetailsDTO trackingDetailsDTO) {

        TrackingDetails trackingDetails = modelMapper.map(trackingDetailsDTO, TrackingDetails.class);
        trackingDetailsRepository.save(trackingDetails);

        return "You have successfully activated the Mail Tracking service";
    }
}
