package com.UoR_MTS_Backend.mail_tracking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RegisterUserDTO {
    private String email;
    private String password;
    private String fullName;
    private String branchCode;
}
