package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.RequestPasswordResetDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.PasswordService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/get-otp/{email}")
    public ResponseEntity<StandardResponse<String>> getOTP(@PathVariable String email) {

        try {
            String OTP = passwordService.getOTP(email);
            return ResponseBuilder.success("OTP sent successfully", OTP);
        } catch (Exception e) {
            return ResponseBuilder.error(e.getMessage(), null);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<StandardResponse<String>> resetPassword(@RequestBody RequestPasswordResetDTO requestPasswordResetDTO) {

        try {
            String message = passwordService.resetPassword(requestPasswordResetDTO);
            return ResponseBuilder.success(message, null);
        } catch (Exception e) {
            return ResponseBuilder.error(e.getMessage(), null);
        }
    }

}
