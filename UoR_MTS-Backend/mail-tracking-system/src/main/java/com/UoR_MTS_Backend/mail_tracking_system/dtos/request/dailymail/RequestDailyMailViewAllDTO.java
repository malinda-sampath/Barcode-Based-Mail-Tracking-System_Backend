package com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDailyMailViewAllDTO {
    private int id;
    private String branchCode;
    private String senderName;
    private String receiverName;
    private String mailType;
    private String trackingNumber;
    private String mailDescription;
    private String barcodeId;
    private byte[] barcodeImage;
    private LocalDateTime insertDateTime;
    private LocalDateTime updateDateTime;
}
