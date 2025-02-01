package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dto.TrackingDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailTrackingException;
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
        try {
            // Check if the tracking details are valid (you can add more validation logic here)
            if (trackingDetailsDTO == null || trackingDetailsDTO.getMailTrackingNumber() == null) {
                throw new MailTrackingException("Tracking details or tracking number is missing.");
            }

            // Convert DTO to Entity
            TrackingDetails trackingDetails = modelMapper.map(trackingDetailsDTO, TrackingDetails.class);

            // Save to the repository
            trackingDetailsRepository.save(trackingDetails);

            // Return success message
            return "You have successfully activated the Mail Tracking service";
        } catch (MailTrackingException e) {
            // Optionally log the exception (or let it propagate)
            throw e;  // Re-throw the custom exception to be handled by @ControllerAdvice
        } catch (Exception e) {
            // Catch any unexpected exception and throw a generic one
            throw new RuntimeException("Error occurred while saving tracking details: " + e.getMessage());
        }
    }


}
