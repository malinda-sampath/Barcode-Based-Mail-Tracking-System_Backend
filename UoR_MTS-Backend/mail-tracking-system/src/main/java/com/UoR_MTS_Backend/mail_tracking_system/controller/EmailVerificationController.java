package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.EmailVerificationResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.EmailVerificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email-verification")
@AllArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify/{email}")
    public EmailVerificationResponseDTO verifyEmail(
            @PathVariable String email
    ) {

            //message should be "OTP sent successfully"
            return emailVerificationService.verifyEmail(email);
    }
}
