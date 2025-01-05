package com.UoR_MTS_Backend.mail_tracking_system.controller;
import org.springframework.http.ResponseEntity;
import com.UoR_MTS_Backend.mail_tracking_system.utill.StandardResponse;
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
        try{
            String uniqueID = BarcodeIDGenerator.generateUniqueId();

            byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);

            String message = dailyMailService.updateDailyMail(requestDailyMailDTO, barcodeImage, uniqueID);


            return ResponseEntity.ok(message);

        } catch (IOException | WriterException e) {
            // If an error occurs, return an error response
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
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getAllDailyMails() {
        try {

            List<RequestDailyMailViewAllDTO> dailyMails = dailyMailService.getAllDailyMails();

            // Return a success response with the list of daily mails
            return ResponseBuilder.success("Daily mails retrieved successfully", dailyMails);

        } catch (Exception e) {
            // If an error occurs, return an error response
            return ResponseBuilder.error("Error retrieving daily mails: " + e.getMessage(), null);
        }
    }
}
