package com.UoR_MTS_Backend.mail_tracking_system.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchUserRequestDTO {
    private String contact;
    private String email;
    private String fullName;
    private String password;
}
