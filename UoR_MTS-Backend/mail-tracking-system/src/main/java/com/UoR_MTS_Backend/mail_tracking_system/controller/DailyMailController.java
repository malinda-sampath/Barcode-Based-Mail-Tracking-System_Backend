package com.UoR_MTS_Backend.mail_tracking_system.controller;
import org.springframework.http.ResponseEntity;
import com.UoR_MTS_Backend.mail_tracking_system.utill.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.dto.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.DailyMailService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.generator.BarcodeIDGenerator;
import com.UoR_MTS_Backend.mail_tracking_system.utill.generator.BarcodeImageGenerator;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/daily-mail")
@CrossOrigin
public class DailyMailController {

    @Autowired
    private DailyMailService dailyMailService;

    @PostMapping("/add-daily-mail")
    public ResponseEntity<String> addDailyMail(@RequestBody DailyMailDTO dailyMailDTO) {
        try{
            String uniqueID = BarcodeIDGenerator.generateUniqueId();

            byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);

            String message = dailyMailService.addDailyMail(dailyMailDTO, barcodeImage, uniqueID);


            return ResponseEntity.ok(message);

        } catch (IOException | WriterException e) {


            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding daily mail: " + e.getMessage());
        }
    }

    @PutMapping("/update-daily-mail")
    public ResponseEntity<String> updateDailyMail(@RequestBody RequestDailyMailDTO requestDailyMailDTO) {
        String uniqueID;
        byte[] barcodeImage;

        try {
            // Generate unique ID
            uniqueID = BarcodeIDGenerator.generateUniqueId();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating unique ID: " + e.getMessage());
        }

        try {
            // Generate barcode image
            barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);
        } catch (IOException | WriterException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating barcode image: " + e.getMessage());
        }

        try {
            // Call the service to update daily mail
            String message = dailyMailService.updateDailyMail(requestDailyMailDTO, barcodeImage, uniqueID);
            return ResponseEntity.ok(message);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating daily mail: " + e.getMessage());
        }
    }


    @DeleteMapping(path = "/delete-daily-mail/{id}")
    public ResponseEntity<String> deleteDailyMail(@PathVariable(value = "id") int dailyMailId) {

        try {

            String message = dailyMailService.deleteDailyMail(dailyMailId);

            // Return a success response with message
            return ResponseEntity.ok(message);

        } catch (Exception e) {
            // If an error occurs, return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting daily mail: " + e.getMessage());
        }
    }

    @GetMapping("/get-all-daily-mails")
    public ResponseEntity<?> getAllDailyMails() {
        List<RequestDailyMailViewAllDTO> dailyMails = dailyMailService.getAllDailyMails();

        if (!dailyMails.isEmpty()) {
            return ResponseBuilder.success("Daily mails retrieved successfully", dailyMails);
        } else {
            return ResponseBuilder.notFound("No daily mails found");
        }
    }



    @GetMapping("/get-daily-mails-by-barcodeId/{barcodeId}")
    public ResponseEntity<?> getAllDailyMailsByBarcodeId(@PathVariable(value = "barcodeId") String barcodeId) {
        List<RequestDailyMailViewAllDTO> allMailActivityByBarcodeId = dailyMailService.getAllDailyMailsByBarcodeId(barcodeId);

        if (!allMailActivityByBarcodeId.isEmpty()) {
            return ResponseBuilder.success("Daily mails retrieved successfully by barcode ID", allMailActivityByBarcodeId);
        } else {
            return ResponseBuilder.notFound("No daily mails found for the given barcode ID");
        }
    }


    @GetMapping("/daily-mail-filter")
    public ResponseEntity<?> getMailActivityByFilter(
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName,
            @RequestParam(required = false) String mailType,
            @RequestParam(required = false) String trackingNumber,
            @RequestParam(required = false) String branchName) {

        List<RequestDailyMailViewAllDTO> mailActivityDTOList = dailyMailService.filterDailyMail(senderName, receiverName, mailType, trackingNumber, branchName);

        if (!mailActivityDTOList.isEmpty()) {
            return ResponseBuilder.success("Mail activities retrieved successfully", mailActivityDTOList);
        } else {
            return ResponseBuilder.notFound("No mail activities found with the provided filters");
        }
    }

}
