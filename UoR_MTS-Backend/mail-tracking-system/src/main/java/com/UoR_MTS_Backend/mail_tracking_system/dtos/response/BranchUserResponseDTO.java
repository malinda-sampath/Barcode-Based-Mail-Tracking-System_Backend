package com.UoR_MTS_Backend.mail_tracking_system.dtos.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BranchUserResponseDTO {
    private String id;
    private String branchCode;
    private String contact;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
    private String email;
    private String fullName;
    private String password;
}
