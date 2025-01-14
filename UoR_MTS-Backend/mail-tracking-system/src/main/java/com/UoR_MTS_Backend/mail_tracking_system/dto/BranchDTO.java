package com.UoR_MTS_Backend.mail_tracking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BranchDTO {
    private int branchCode;
    private String branchName;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
