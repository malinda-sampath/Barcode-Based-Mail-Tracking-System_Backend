package com.UoR_MTS_Backend.mail_tracking_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class LoginResponseDTO {
    private String token;
    private long expiresIn;
}
