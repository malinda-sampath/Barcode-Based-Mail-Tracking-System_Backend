package com.UoR_MTS_Backend.mail_tracking_system.dtos;

import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailRecordDTO {
    private int id;
    private String senderName;
    private String receiverName;
    private String mailType;
    private String trackingNumber;
    private String barcodeId;
    private String mailDescription;
    private byte[] barcodeImage;
    private LocalDateTime insertDateTime;
    private LocalDateTime updateDateTime;
    private Branch branch;
    private String location;
    private String status;
    private String referenceNumber;
}
