package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dto.EmailVerificationResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.TrackingDetails;
import com.UoR_MTS_Backend.mail_tracking_system.repo.TrackingDetailsRepository;
import com.UoR_MTS_Backend.mail_tracking_system.service.EmailVerificationService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.email.EmailBody;
import com.UoR_MTS_Backend.mail_tracking_system.utill.email.OTPgenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmailVerificationServiceIMPL implements EmailVerificationService {

    private final OTPgenerator otpGenerator;
    private final EmailBody emailBody;
    private final TemplateEngine templateEngine;
    private final TrackingDetailsRepository trackingDetailsRepository;

    @Override
    public EmailVerificationResponseDTO verifyEmail(String email) {
        String generatedOTP = otpGenerator.OTP();

        try{
            Context context = new Context();
            context.setVariable("otp", generatedOTP);

            String htmlContent = templateEngine.process("otpEmailTemplate", context);
            String subject = "Confidential: OTP for Email Verification";

            emailBody.sendEmailWithHtmlContent(subject, email, htmlContent);

            EmailVerificationResponseDTO emailVerificationResponseDTO = new EmailVerificationResponseDTO(
                    email,
                    generatedOTP
            );

            return emailVerificationResponseDTO;

        } catch (Exception e) {
            throw new RuntimeException("Error sending OTP to the provided email", e);
        }
    }
}
