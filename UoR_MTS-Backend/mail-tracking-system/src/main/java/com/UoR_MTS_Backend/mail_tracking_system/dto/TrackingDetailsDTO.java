package com.UoR_MTS_Backend.mail_tracking_system.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrackingDetailsDTO {

    private Long id;
    private String email;
    private LocalDateTime insertedAt;
    private String mailType;
    private int mailTrackingNumber;
}
