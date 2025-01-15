package com.UoR_MTS_Backend.mail_tracking_system.service;

public interface EmailVerificationService {
    public String verifyEmail(String email);

    public void saveEmail(String email);
}
