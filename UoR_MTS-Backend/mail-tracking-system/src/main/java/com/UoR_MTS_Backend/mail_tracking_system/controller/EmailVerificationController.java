package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.EmailVerificationResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.EmailVerificationService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
                EmailVerificationResponseDTO Message = emailVerificationService.verifyEmail(email);
                return ResponseBuilder.success("OTP Send Successfully",null);
            }catch(Exception e){
                return ResponseBuilder.error("Error Occure When OTP Send",null);
            }


    }
}
