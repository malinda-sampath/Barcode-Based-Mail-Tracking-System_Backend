package com.UoR_MTS_Backend.mail_tracking_system.controller;

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
    public String verifyEmail(
            @PathVariable String email
    ) {
        try {
            String otp = emailVerificationService.verifyEmail(email);
            return "OTP sent successfully.";
            // otp should send to the frontend as data to be used for verification(manuri)
            // return ResponseEntity.ok("OTP sent successfully.", otp);
        } catch (Exception e) {
            return "Failed to send OTP. Please try again later.";
        }
    }

    @PostMapping("/save/{email}")
    public void saveEmail(
            @PathVariable String email
    ) {
        try {
            emailVerificationService.saveEmail(email);

        } catch (Exception e) {
            throw new RuntimeException("Internal server error");
        }
    }

}
