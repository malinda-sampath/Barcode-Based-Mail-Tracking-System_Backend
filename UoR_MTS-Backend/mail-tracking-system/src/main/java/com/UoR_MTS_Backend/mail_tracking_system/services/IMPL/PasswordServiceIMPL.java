package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.RequestPasswordResetDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.PasswordService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.email.EmailBody;
import com.UoR_MTS_Backend.mail_tracking_system.utils.email.OTPgenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PasswordServiceIMPL implements PasswordService {

    private final UserRepo userRepo;
    private final OTPgenerator otpGenerator;
    private final EmailBody emailBody;
    private final TemplateEngine templateEngine;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String getOTP(String email) {

        try {
            userRepo.findByEmail(email);

            try{
                String otp = otpGenerator.OTP();

                Context context = new Context();
                context.setVariable("otp", otp);

                String htmlContent = templateEngine.process("passwordResetTemplate", context);
                String subject = "Confidential: OTP for Password Reset";
                emailBody.sendEmailWithHtmlContent(subject, email, htmlContent);

                return otp;
            } catch (Exception e){
                throw new RuntimeException("Error sending OTP to the provided email", e);
            }
        } catch (Exception e){
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public String resetPassword(RequestPasswordResetDTO requestPasswordResetDTO) {
        try{
            Optional<User> existingUser = userRepo.findByEmail(requestPasswordResetDTO.email);
            User user = userRepo.getReferenceById(existingUser.get().getId());

            user.setPassword(bCryptPasswordEncoder.encode(requestPasswordResetDTO.password));
            userRepo.save(user);
            return "Password reset successfully";
        } catch (Exception e){
            throw new RuntimeException("Error resetting password: ", e);
        }
    }
}
