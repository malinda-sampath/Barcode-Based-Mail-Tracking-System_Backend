package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailRecordResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.exception.NoMailActivitiesFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailRecordService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RestController
@RequestMapping("/api/mailRecord")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('MAIL_HANDLER', 'SUPER_ADMIN')")
public class MailRecordController {

    private final MailRecordService mailRecordService;

    @PostMapping("/transfer")
    public ResponseEntity<StandardResponse<String>> transferDailyMailsToMainCart() throws MessagingException {
        String message = mailRecordService.transferDailyMailsToMailRecord();
        return ResponseBuilder.success(message, null);
    }


//    @GetMapping("/filter/{page}")
//    public ResponseEntity<StandardResponse<Page<MailRecordDTO>>> filterMailRecords(
//            @RequestParam(required = false) String senderName,
//            @RequestParam(required = false) String receiverName,
//            @RequestParam(required = false) String mailType,
//            @RequestParam(required = false) String trackingNumber,
//            @RequestParam(required = false) String branchName,
//            @PathVariable int page) {
//
//        int pageSize = 10;
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//        // Call the service method to get filtered records
//        Page<MailRecordDTO> filteredRecords = mailRecordService.filterMailRecords(
//                senderName, receiverName, mailType, trackingNumber, branchName, pageable);
//
//        if (filteredRecords.isEmpty()) {
//            throw new NoMailActivitiesFoundException("No mail activities found with the provided filters.");
//        }
//
//        // Return successful response if records are found
//        return ResponseBuilder.success("Mail records retrieved successfully.", filteredRecords);
//    }

    @GetMapping("/get-by-branch/{branchCode}")
    public ResponseEntity<StandardResponse<List<MailRecordResponseDTO>>> getByBranch(@PathVariable String branchCode) {

        List<MailRecordResponseDTO> mailRecords = mailRecordService.getByBranch(branchCode);

        // Check if the result is empty
        if (mailRecords.isEmpty()) {
            return ResponseBuilder.notFound("No mail records found.");
        }

        // Return successful response
        return ResponseBuilder.success("All Mail records retrieved successfully", mailRecords);
    }

    @GetMapping("/get-by-branch")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StandardResponse<List<MailRecordResponseDTO>>> getByBranchWithoutBranchCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User branchUser = (User) authentication.getPrincipal();

        List<MailRecordResponseDTO> mailRecords = mailRecordService.getByBranch(branchUser.getBranchCode());

        // Check if the result is empty
        if (mailRecords.isEmpty()) {
            return ResponseBuilder.notFound("No mail records found.");
        }

        // Return successful response
        return ResponseBuilder.success("All Mail records retrieved successfully", mailRecords);
    }


    @GetMapping("/search/{barcodeId}")
    public ResponseEntity<StandardResponse<MailRecord>> searchMailByBarcodeId(@PathVariable String barcodeId) {
        // Call the service method to search mail by barcode ID
        MailRecord mailRecord = mailRecordService.searchMailByBarcodeId(barcodeId);

        // Return the found mail record
        return ResponseBuilder.success("Mail record found successfully.", mailRecord);
    }


    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<MailRecordResponseDTO>>> getAllMailRecords() {

        List<MailRecordResponseDTO> mailRecords = mailRecordService.getAllMailRecords();

        // Check if the result is empty
        if (mailRecords.isEmpty()) {
            return ResponseBuilder.notFound("No mail records found.");
        }

        // Return successful response
        return ResponseBuilder.success("All Mail records retrieved successfully", mailRecords);
    }

}




