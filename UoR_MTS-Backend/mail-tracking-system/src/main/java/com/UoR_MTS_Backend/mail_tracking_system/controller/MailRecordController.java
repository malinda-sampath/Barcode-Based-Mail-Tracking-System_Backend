package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailRecordService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public String transferDailyMailsToMainCart() {

        return mailRecordService.transferDailyMailsToMainCart();
    }

//    @GetMapping("/search")
//    public Page<MailRecord> filterMailRecords(
//            @RequestParam(required = false) String senderName,
//            @RequestParam(required = false) String receiverName,
//            @RequestParam(required = false) String mailType,
//            @RequestParam(required = false) String trackingNumber,
//            @RequestParam(required = false) String branchName,
//            @PageableDefault(size = 10) Pageable pageable) {
//
//        try {
//            return mailRecordService.filterMailRecords(senderName, receiverName, mailType, trackingNumber, branchName, pageable);
//        } catch (Exception e) {
//            logger.error("Error occurred while filtering mail records", e);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while filtering mail records", e);
//        }
//    }
//
//    // Endpoint to search by barcode ID
//    @GetMapping("/searchByBarcode/{barcodeId}")
//    public MailRecord searchMailByBarcodeId(@PathVariable String barcodeId) {
//        MailRecord mailRecord = mailRecordService.searchMailByBarcodeId(barcodeId);
//        if (mailRecord == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mail with barcode ID " + barcodeId + " not found.");
//        }
//        return mailRecord;
//    }


    }

