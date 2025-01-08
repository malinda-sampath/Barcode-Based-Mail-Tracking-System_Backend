package com.UoR_MTS_Backend.mail_tracking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BranchUserDto {
    private String branchUserName;
    private String branchUserPassword;
    private String branchCode;
}
