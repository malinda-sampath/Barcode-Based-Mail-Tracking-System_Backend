package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.EmailVerificationResponseDTO;

public interface EmailVerificationService {
   EmailVerificationResponseDTO verifyEmail(String email);

}
