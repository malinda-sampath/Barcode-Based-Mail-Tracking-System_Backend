package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.services.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password")
@AllArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/reset/{email}")
    public String getOTP(@RequestBody String email){
        String message = passwordService.getOTP(email);
        return message;
    }
}
