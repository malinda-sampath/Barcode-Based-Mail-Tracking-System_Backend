package com.UoR_MTS_Backend.mail_tracking_system.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MailAdminDTO {

    private long mailAdminId;
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "UserName is mandatory")
    private String userName;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
    private String contact;

    @NotBlank(message = "Role is mandatory")
    private String role;

    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

}
