package com.UoR_MTS_Backend.mail_tracking_system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrackingDetailsRequestDTO {

    private String email;
    private LocalDateTime insertedAt;
    private String mailType;
    private int mailTrackingNumber;


}
