package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.NoMailActivitiesFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailRecordService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;
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


@RestController
@RequestMapping("/api/mailRecord")
public class MailRecordController {
    private static final Logger logger = LoggerFactory.getLogger(MailRecordController.class);

    @Autowired
    private MailRecordService mailRecordService;

    @PostMapping("/transfer")
    public ResponseEntity<StandardResponse<String>> transferDailyMailsToMainCart() {
        // Call the service method directly
        String message = mailRecordService.transferDailyMailsToMainCart();
        return ResponseBuilder.success(message, null);
    }


    @GetMapping("/filter/{page}")
    public ResponseEntity<StandardResponse<Page<MailRecordDTO>>> filterMailRecords(
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName,
            @RequestParam(required = false) String mailType,
            @RequestParam(required = false) String trackingNumber,
            @RequestParam(required = false) String branchName,
            @PathVariable int page) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        // Call the service method to get filtered records
        Page<MailRecordDTO> filteredRecords = mailRecordService.filterMailRecords(
                senderName, receiverName, mailType, trackingNumber, branchName, pageable);

        if (filteredRecords.isEmpty()) {
            throw new NoMailActivitiesFoundException("No mail activities found with the provided filters.");
        }

        // Return successful response if records are found
        return ResponseBuilder.success("Mail records retrieved successfully.", filteredRecords);
    }


    @GetMapping("/searchByBarcode/{barcodeId}")
    public ResponseEntity<StandardResponse<MailRecord>> searchMailByBarcodeId(@PathVariable String barcodeId) {
        // Call the service method to search mail by barcode ID
        MailRecord mailRecord = mailRecordService.searchMailByBarcodeId(barcodeId);

        // Return the found mail record
        return ResponseBuilder.success("Mail record found successfully.", mailRecord);
    }


    @GetMapping("/all-main-mails")
    public ResponseEntity<?> getAllMailRecords(@RequestParam(defaultValue = "0") int page) {
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
    }

}




