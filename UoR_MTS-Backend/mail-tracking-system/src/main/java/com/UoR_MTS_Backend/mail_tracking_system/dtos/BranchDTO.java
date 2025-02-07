package com.UoR_MTS_Backend.mail_tracking_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BranchDTO {
    private int id;
    private String branchName;
    private String branchCode;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
}
