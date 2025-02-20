package com.UoR_MTS_Backend.mail_tracking_system.dtos;

import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailActivityDTO {
    private int id;
    private int userId;
    private String userName;
    private String activity;
    private String branchCode;
    private String senderName;
    private String receiverName;
    private String barcodeId;
    private LocalDateTime activityDateTime;
    private User user;
}
