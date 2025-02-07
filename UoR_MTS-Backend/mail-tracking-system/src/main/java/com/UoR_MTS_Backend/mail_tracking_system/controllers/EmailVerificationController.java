package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.EmailVerificationResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.EmailVerificationService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StandardResponse<EmailVerificationResponseDTO>>  verifyEmail(@PathVariable String email) {
            try{
            //message should be "OTP sent successfully"
                EmailVerificationResponseDTO emailResponse = emailVerificationService.verifyEmail(email);
                return ResponseBuilder.success("OTP Send Successfully!",emailResponse);
            }catch(Exception e){
                System.err.println("Error OTP Send : " + e.getMessage());
                return ResponseBuilder.error("Error Occur When OTP Send",null);
            }
    }
}
