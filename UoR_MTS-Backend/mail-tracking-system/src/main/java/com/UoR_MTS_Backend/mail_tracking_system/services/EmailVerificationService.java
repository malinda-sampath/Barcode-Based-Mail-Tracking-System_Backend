package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.EmailVerificationResponseDTO;

public interface EmailVerificationService {
   EmailVerificationResponseDTO verifyEmail(String email);

}
