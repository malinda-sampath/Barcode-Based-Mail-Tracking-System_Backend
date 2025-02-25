package com.UoR_MTS_Backend.mail_tracking_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BranchDTO {
    private String branchCode;
    private String branchName;
    private String branchDescription;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
