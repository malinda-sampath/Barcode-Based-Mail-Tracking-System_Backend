package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "user_activity_logs")
public class MailActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String activityType;
    private String branchCode;
    private String senderName;
    private String receiverName;
    private String barcodeId;
    private LocalDateTime activityDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public MailActivity(String activityType ,String branchCode,String senderName,String receiverName,String barcodeId, LocalDateTime activityDateTime, User user) {
        this.activityType = activityType;
        this.branchCode = branchCode;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.barcodeId = barcodeId;
        this.activityDateTime = activityDateTime;
        this.user = user;
    }
}
