package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailClaimDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailClaimService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("api/mail-claim")
@AllArgsConstructor
public class MailClaimController {
    private final MailClaimService mailClaimService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> saveClaimDetails(@RequestBody MailClaimDetailsDTO claimDetailsDTO) throws SQLException {
        String message = mailClaimService.ClaimMailDetailsSave(claimDetailsDTO);
        return ResponseBuilder.success(message,null);
    }

}
