package com.UoR_MTS_Backend.mail_tracking_system.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MailHandlerRequestDTO {
    private String fullName;
    private String email;
    private String contact;
    private String password;
}
