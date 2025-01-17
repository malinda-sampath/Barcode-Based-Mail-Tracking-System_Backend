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
    @Table(name = "mail_record")
    public class MailRecord {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int mailRecordId;

        private int branchCode;
        private String branchName;
        private String mailType;
        private String trackingNumber;
        private String barcodeId;
        private String mailDescription;
        private String senderName;
        private String receiverName;

        @Lob
        private byte[] barcodeImage;
        private LocalDateTime insertDateTime;
        private LocalDateTime updateDateTime;
    }

