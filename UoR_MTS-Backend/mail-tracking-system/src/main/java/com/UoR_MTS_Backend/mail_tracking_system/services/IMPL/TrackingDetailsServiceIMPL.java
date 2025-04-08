package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.TrackingDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailTrackingException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.TrackingDetails;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.TrackingDetailsRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.TrackingDetailsService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.email.EmailBody;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class TrackingDetailsServiceIMPL implements TrackingDetailsService {

    private final TrackingDetailsRepo trackingDetailsRepo;
    private final DailyMailRepo dailyMailRepo;
    private final ModelMapper modelMapper;
    private final EmailBody emailBody;
    private final TemplateEngine templateEngine;


    @Override
    public String saveTrackingDetails(TrackingDetailsDTO trackingDetailsDTO) {
        try {
            // Check if the tracking details are valid (you can add more validation logic here)
            if (trackingDetailsDTO == null || trackingDetailsDTO.getMailTrackingNumber() == null) {
                throw new MailTrackingException("Tracking details or tracking number is missing.");
            }

            // Convert DTO to Entity
            TrackingDetails trackingDetails = modelMapper.map(trackingDetailsDTO, TrackingDetails.class);
            trackingDetailsRepo.save(trackingDetails);
            String trackingNumber = trackingDetails.getMailTrackingNumber();
            DailyMail dailyMail = dailyMailRepo.findByTrackingNumber(trackingNumber);

            if(dailyMail != null){
                String email= trackingDetails.getEmail();
                Context context = new Context();
                context.setVariable("receiver_name",dailyMail.getReceiverName());
                context.setVariable("tracking_number",dailyMail.getTrackingNumber());
                context.setVariable("insert_date", dailyMail.getInsertDateTime());

                String htmlContent = templateEngine.process("mailReadyForPickConfirmationTemplate", context);
                String subject = "Mail Ready For Pickup Confirmation";

                emailBody.sendEmailWithHtmlContent(subject,email,htmlContent);
            } else {
                String email= trackingDetails.getEmail();
                Context context = new Context();
                String htmlContent = templateEngine.process("confirmEmailTemplate", context);
                String subject = "Tracking Service Activate Confirmation";
                emailBody.sendEmailWithHtmlContent(subject,email,htmlContent);
            }

            // Save to the repository

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
