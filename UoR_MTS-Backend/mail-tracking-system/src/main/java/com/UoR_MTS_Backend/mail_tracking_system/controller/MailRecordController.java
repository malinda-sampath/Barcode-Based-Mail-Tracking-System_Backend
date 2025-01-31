package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.service.DailyMailService;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailRecordService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/mailRecord")
public class MailRecordController {
    private static final Logger logger = LoggerFactory.getLogger(MailRecordController.class);

    @Autowired
    private MailRecordService mailRecordService;

    @PostMapping("/transfer")
    public ResponseEntity<StandardResponse<String>> transferDailyMailsToMainCart() {
        try {
            String message = mailRecordService.transferDailyMailsToMainCart();

            return ResponseBuilder.success(message, null);
        } catch (Exception e) {
            return ResponseBuilder.error("Error Saving Mail Record:" + e.getMessage(), null);
        }
    }

    @PostMapping("/transfer/{barcodeId}")
    public ResponseEntity<String> transferMailToBranchCart(@PathVariable("barcodeId") String barcodeId, @RequestBody MailRecordDTO mailRecordDTO) {
        MailRecord mailRecord = mailRecordService.searchMailByBarcodeId(barcodeId);

        // Validate that the mail belongs to the selected branch
        if (!mailRecord.getBranchName().equals(mailRecordDTO.getBranchName()))
            return ResponseEntity.badRequest().body("Mail does not belong to the selected branch.");
        else {
            String addToBranchMailcart = mailRecordService.transferMailToBranchCart(barcodeId);
            mailRecord.setUpdateDateTime(LocalDateTime.now());
            mailRecordService.updateMailRecordList(barcodeId);
            return ResponseEntity.ok("Mail transferred successfully."+addToBranchMailcart);
        }

    }

    @GetMapping("/filter/{page}")
    public ResponseEntity<StandardResponse<Page<MailRecordDTO>>> filterMailRecords(
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName,
            @RequestParam(required = false) String mailType,
            @RequestParam(required = false) String trackingNumber,
            @RequestParam(required = false) String branchName,
            @RequestParam(defaultValue = "0") int page) {
        try {
            int pageSize = 10;
            Pageable pageable = PageRequest.of(page, pageSize);
            // Call the service method
            Page<MailRecordDTO> filteredRecords = mailRecordService.filterMailRecords(
                    senderName, receiverName, mailType, trackingNumber, branchName, pageable);
            if (filteredRecords == null || filteredRecords.isEmpty()) {
                return ResponseBuilder.notFound("No mail activities found with the provided filters.");
            }

            // Return successful response
            return ResponseBuilder.success(null, filteredRecords);
        } catch (Exception e) {
            return ResponseBuilder.error("Error retrieving mail activities:" + e.getMessage(), null);
        }
    }

    @GetMapping("/searchByBarcode/{barcodeId}")
    public ResponseEntity<StandardResponse<MailRecord>> searchMailByBarcodeId(@PathVariable String barcodeId) {
        try {
            MailRecord mailRecord = mailRecordService.searchMailByBarcodeId(barcodeId);

            if (mailRecord == null) {
                return ResponseBuilder.notFound("Mail with barcode ID " + barcodeId + " not found.");
            }

            return ResponseBuilder.success(null, mailRecord);
        } catch (Exception e) {
            return ResponseBuilder.error("Error occurred while searching by barcode ID:" + e.getMessage(), null);
        }
    }

    @GetMapping("/all-main-mails")
    public ResponseEntity<?> getAllMailRecords(@RequestParam(defaultValue = "0") int page) { // Default page size
        try {
            int size = 10;
            Pageable pageable = PageRequest.of(page, size);

            // Call the service method to fetch mail records
            Page<MailRecord> mailRecords = mailRecordService.getAllMailRecords(pageable);

            // Check if the result is empty
            if (mailRecords.isEmpty()) {
                return ResponseBuilder.notFound("No mail records found.");
            }

            // Return successful response
            return ResponseBuilder.success(null, mailRecords);
        } catch (Exception e) {
            // Log the exception and return error response
            return ResponseBuilder.error("Error retrieving mail records:" + e.getMessage(), null);
        }
    }

    @GetMapping("/get-all-mails-by-branch/{branch}")
    public ResponseEntity<List<MailRecordDTO>> getMailsByBranch(@PathVariable(value = "branch") String branch) {
        List<MailRecordDTO> mails = mailRecordService.getMailsByBranch(branch);
        return ResponseEntity.ok(mails);
    }

    @GetMapping("/claimed-mails")
    public ResponseEntity<?> getClaimedMails(@RequestParam(defaultValue = "0") int page) {
        try {
            int size = 10;
            Pageable pageable = PageRequest.of(page, size);

            // Call the service method to fetch mail records
            Page<MailRecord> mailRecords = mailRecordService.getAllMailRecords(pageable);
            List<MailRecord> claimedMailRecords = new ArrayList<>();
            for (MailRecord mailRecord : mailRecords) {
                if(mailRecord.getUpdateDateTime() != null)
                    claimedMailRecords.add(mailRecord);
            }
            // Check if the result is empty
            if (claimedMailRecords.isEmpty()) {
                return ResponseBuilder.notFound("No mail records found.");
            }

            // Return successful response
            return ResponseBuilder.success(null, claimedMailRecords);
        } catch (Exception e) {
            // Log the exception and return error response
            return ResponseBuilder.error("Error retrieving mail records:" + e.getMessage(), null);
        }
    }
}

