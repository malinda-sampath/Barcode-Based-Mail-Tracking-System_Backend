package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.HeaderResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailRecordResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailRecordService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/branch-mail")
public class BranchMailController {

    private final MailRecordService mailRecordService;

    @GetMapping("mail-details")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StandardResponse<List<MailRecordResponseDTO>>> getBranchMails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        String branchCode = currentUser.getBranchCode();

        List<MailRecordResponseDTO> mailRecords = mailRecordService.getByBranch(branchCode);

        return ResponseBuilder.success("Here are your header details.", mailRecords);
    }
}
