package com.UoR_MTS_Backend.mail_tracking_system.model;

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
@Table(name = "daily_mails")
public class DailyMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String branchCode;
    private String branchName;
    private String senderName;
    private String receiverName;
    private String mailType;
    private String trackingNumber;
    private String mailDescription;
    private String barcodeId;

    @Lob
    private byte[] barcodeImage;
    private LocalDateTime insertDateTime;
    private LocalDateTime updateDateTime;

}
