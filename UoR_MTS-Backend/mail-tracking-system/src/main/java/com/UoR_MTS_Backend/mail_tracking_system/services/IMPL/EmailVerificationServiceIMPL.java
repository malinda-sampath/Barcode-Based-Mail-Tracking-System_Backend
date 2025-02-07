package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.EmailVerificationResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.EmailVerificationService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.email.EmailBody;
import com.UoR_MTS_Backend.mail_tracking_system.utils.email.OTPgenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailVerificationServiceIMPL implements EmailVerificationService {

    private final OTPgenerator otpGenerator;
    private final EmailBody emailBody;
    private final TemplateEngine templateEngine;

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
