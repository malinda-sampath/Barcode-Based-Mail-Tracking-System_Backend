package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.DailyMailService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.barcode.BarcodeIDGenerator;
import com.UoR_MTS_Backend.mail_tracking_system.utils.barcode.BarcodeImageGenerator;
import com.google.zxing.WriterException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/daily-mail")
@CrossOrigin
@AllArgsConstructor
@PreAuthorize("hasAnyRole('MAIL_HANDLER', 'SUPER_ADMIN')")
public class DailyMailController {

    private final DailyMailService dailyMailService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> addDailyMail(@RequestBody DailyMailDTO dailyMailDTO) throws WriterException, IOException {
        if (dailyMailDTO == null) {
            throw new IllegalArgumentException("DailyMailDTO cannot be null.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String uniqueID = BarcodeIDGenerator.generateUniqueId();
        byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);


        String message = dailyMailService.addDailyMail(dailyMailDTO, barcodeImage, uniqueID,authentication);

        return ResponseBuilder.success(message, null);
    }

    @PutMapping("/update/{barcodeId}")
    @PreAuthorize("hasRole('MAIL_HANDLER')")
    public ResponseEntity<StandardResponse<String>> updateDailyMail(@PathVariable String barcodeId,@RequestBody DailyMailDTO dailyMailDTO) throws WriterException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uniqueID = BarcodeIDGenerator.generateUniqueId();
        byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);
        String message = dailyMailService.updateDailyMail(barcodeId,dailyMailDTO, barcodeImage, uniqueID,authentication);
        return ResponseBuilder.success(message, null);
    }


    @DeleteMapping("/delete/{barcodeId}")
    @PreAuthorize("hasRole('MAIL_HANDLER')")
    public ResponseEntity<StandardResponse<String>> deleteDailyMail(@PathVariable String barcodeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String message = dailyMailService.deleteDailyMail(barcodeId, authentication);

        if (message != null) {
            return ResponseBuilder.success(message, null);
        } else {
            return ResponseBuilder.notFound("Mail not found.");
        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getAllDailyMails() {

        List<RequestDailyMailViewAllDTO> dailyMails = dailyMailService.getAllDailyMails();

        if (dailyMails == null || dailyMails.isEmpty()) {
            return ResponseBuilder.notFound("No daily mails found.");
        }

        // Add the missing return statement here
        return ResponseBuilder.success("Daily mails retrieved successfully.", dailyMails);
    }


    @GetMapping("/get/{barcodeId}")
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getAllDailyMailsByBarcodeId(@PathVariable String barcodeId) {

        List<RequestDailyMailViewAllDTO> allMailActivityByBarcodeId = dailyMailService.getAllDailyMailsByBarcodeId(barcodeId);

        if (allMailActivityByBarcodeId == null || allMailActivityByBarcodeId.isEmpty()) {
            return ResponseBuilder.notFound("No daily mails found for the given barcode ID: " + barcodeId);
        }


        return ResponseBuilder.success("Daily mails retrieved successfully by barcode ID: " + barcodeId, allMailActivityByBarcodeId);
    }


    @GetMapping("/filter")
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getMailActivityByFilter(
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName,
            @RequestParam(required = false) String mailType,
            @RequestParam(required = false) String trackingNumber,
            @RequestParam(required = false) String branchName) {


        List<RequestDailyMailViewAllDTO> mailActivityDTOList = dailyMailService.filterDailyMail(
                senderName, receiverName, mailType, trackingNumber, branchName
        );

        return ResponseBuilder.success("Mail activities retrieved successfully", mailActivityDTOList);
    }


}
