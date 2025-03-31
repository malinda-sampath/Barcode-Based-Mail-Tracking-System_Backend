package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.TrackingDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailTrackingException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.TrackingDetails;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.TrackingDetailsRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.TrackingDetailsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrackingDetailsServiceIMPL implements TrackingDetailsService {

    private final TrackingDetailsRepo trackingDetailsRepo;
    private final ModelMapper modelMapper;

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
            trackingDetailsRepo.save(trackingDetails);

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
