package com.UoR_MTS_Backend.mail_tracking_system.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailHandlerResponseDTO {
    private String userID;
    private String name;
    private String contact;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
