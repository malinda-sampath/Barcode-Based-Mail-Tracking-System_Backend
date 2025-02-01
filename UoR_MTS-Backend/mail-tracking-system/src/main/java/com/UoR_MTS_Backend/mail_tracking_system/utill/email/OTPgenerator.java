package com.UoR_MTS_Backend.mail_tracking_system.utill.email;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OTPgenerator {

    private static final int OTP_LENGTH = 6; // Make it 6 digits for better security

    public String OTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Generates a digit between 0-9
        }

        return otp.toString();
    }
}
