package com.UoR_MTS_Backend.mail_tracking_system.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RequestBranchDTO {
    private String branchName;
    private String description;
}
