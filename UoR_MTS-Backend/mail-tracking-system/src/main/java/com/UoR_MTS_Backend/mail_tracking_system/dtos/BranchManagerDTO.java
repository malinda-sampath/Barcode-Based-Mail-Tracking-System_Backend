package com.UoR_MTS_Backend.mail_tracking_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BranchManagerDTO {
    private String branchManagerName;
    private String branchManagerPassword;
    private String branchCode;
}
