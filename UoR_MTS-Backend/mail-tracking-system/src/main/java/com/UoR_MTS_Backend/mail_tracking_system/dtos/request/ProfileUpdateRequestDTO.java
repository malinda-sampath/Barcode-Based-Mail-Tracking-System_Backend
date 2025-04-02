package com.UoR_MTS_Backend.mail_tracking_system.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileUpdateRequestDTO {
    private String name;
    private String contact;
    private String email;
    private String password;
}
