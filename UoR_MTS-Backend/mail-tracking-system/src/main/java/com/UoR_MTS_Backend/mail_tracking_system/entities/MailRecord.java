package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import java.time.LocalDateTime;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data

    @Entity
    @Table(name = "mail_record")
    public class MailRecord {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String senderName;
        private String receiverName;
        private String mailType;
        private String trackingNumber;
        private String barcodeId;
        private String mailDescription;

        @Lob
        private byte[] barcodeImage;

        @CreationTimestamp
        private LocalDateTime insertDateTime;

        @UpdateTimestamp
        private LocalDateTime updateDateTime;

        @ManyToOne
        @JoinColumn(name = "branch_code", referencedColumnName = "branch_code", nullable = false)
        private Branch branch;

        private String location;
        private String status;
        private String referenceNumber;

        public MailRecord(String senderName, String receiverName, String mailType, String trackingNumber, String barcodeId, String mailDescription, byte[] barcodeImage, Branch branch) {
            this.senderName = senderName;
            this.receiverName = receiverName;
            this.mailType = mailType;
            this.trackingNumber = trackingNumber;
            this.barcodeId = barcodeId;
            this.mailDescription = mailDescription;
            this.barcodeImage = barcodeImage;
            this.branch = branch;
        }
    }

