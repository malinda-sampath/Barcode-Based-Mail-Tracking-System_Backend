package com.UoR_MTS_Backend.mail_tracking_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "activity_log")
public class MailActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityLogId;

    private int userId;
    private String userName;
    private String activityType;
    private String branchName;
    private String senderName;
    private String receiverName;
    private String barcodeId;
    private LocalDateTime activityDateTime;

    public MailActivity(int userId, String userName,String activityType ,String branchName,String senderName,String receiverName,String barcodeId, LocalDateTime activityDateTime) {
        this.userId = userId;
        this.userName = userName;
        this.activityType = activityType;
        this.branchName = branchName;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.barcodeId = barcodeId;
        this.activityDateTime = activityDateTime;
    }
}
