package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.RequestPasswordResetDTO;

public interface PasswordService {
    public String getOTP(String email);

    public String resetPassword(RequestPasswordResetDTO requestPasswordResetDTO);
}
