package com.UoR_MTS_Backend.mail_tracking_system.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchManagerRequestDTO {
    private String branchCode;
    private String fullName;
    private String email;
    private String contact;
    private String password;
}
