package com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDailyMailDTO {
    private String branchCode;
    private String branchName;
    private String senderName;
    private String receiverName;
    private String mailType;
    private String trackingNumber;
    private String mailDescription;
    private LocalDateTime insertDateTime;
    private LocalDateTime updateDateTime;
}
