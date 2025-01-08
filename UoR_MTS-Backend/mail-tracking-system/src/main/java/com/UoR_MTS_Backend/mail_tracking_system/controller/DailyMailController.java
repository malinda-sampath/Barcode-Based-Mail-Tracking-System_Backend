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
import com.UoR_MTS_Backend.mail_tracking_system.utill.StandardResponse;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/daily-mail")
@CrossOrigin
public class DailyMailController {

    @Autowired
    private DailyMailService dailyMailService;

    @PostMapping("/add-daily-mail")
    public ResponseEntity<StandardResponse<String>> addDailyMail(@RequestBody DailyMailDTO dailyMailDTO) {
        try {
            String uniqueID = BarcodeIDGenerator.generateUniqueId();
            byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);
            String message = dailyMailService.addDailyMail(dailyMailDTO, barcodeImage, uniqueID);

            return ResponseBuilder.success(message, null);

        } catch (Exception e) {
            // Log the exception (optional)
            System.err.println("Error adding daily mail: " + e.getMessage());

            return ResponseBuilder.error("Error adding daily mail.",null);
        }
    }


    @PutMapping("/update-daily-mail")
    public ResponseEntity<StandardResponse<String>> updateDailyMail(@RequestBody RequestDailyMailDTO requestDailyMailDTO) {
        try {
            // Generate unique ID
            String uniqueID = BarcodeIDGenerator.generateUniqueId();

            // Generate barcode image
            byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);

            // Call the service to update daily mail
            String message = dailyMailService.updateDailyMail(requestDailyMailDTO, barcodeImage, uniqueID);

            return ResponseBuilder.success(message, null);

        } catch (IOException | WriterException e) {
            // Log the error
            System.err.println("Error generating barcode image: " + e.getMessage());

            return ResponseBuilder.error("Error generating barcode image."+ e.getMessage(),null);

        } catch (Exception e) {
            // Log the error
            System.err.println("Error updating daily mail: " + e.getMessage());

            return ResponseBuilder.error("Error updating daily mail."+ e.getMessage(),null);
        }
    }

    @DeleteMapping(path = "/delete-daily-mail/{id}")
    public ResponseEntity<StandardResponse<String>> deleteDailyMail(@PathVariable(value = "id") int dailyMailId) {
        try {
            String message = dailyMailService.deleteDailyMail(dailyMailId);

            if (message != null) {
                return ResponseBuilder.success(message, null);
            } else {
                return ResponseBuilder.notFound("Daily mail with ID " + dailyMailId + " not found.");
            }
        } catch (Exception e) {
            // Log the error
            System.err.println("Error deleting daily mail: " + e.getMessage());

            return ResponseBuilder.error("Error deleting daily mail.",null);
        }
    }


    @GetMapping("/get-all-daily-mails")
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getAllDailyMails() {
        try {
            List<RequestDailyMailViewAllDTO> dailyMails = dailyMailService.getAllDailyMails();

            if (dailyMails == null || dailyMails.isEmpty()) {
                return ResponseBuilder.notFound("No daily mails found.");
            }

            return ResponseBuilder.success("Daily mails retrieved successfully.", dailyMails);

        } catch (Exception e) {
            // Log the error
            System.err.println("Error retrieving daily mails: " + e.getMessage());

            return ResponseBuilder.error("Error retrieving daily mails.", null);
        }
    }




    @GetMapping("/get-daily-mails-by-barcodeId/{barcodeId}")
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getAllDailyMailsByBarcodeId(@PathVariable String barcodeId) {
        try {
            List<RequestDailyMailViewAllDTO> allMailActivityByBarcodeId = dailyMailService.getAllDailyMailsByBarcodeId(barcodeId);

            if (allMailActivityByBarcodeId == null || allMailActivityByBarcodeId.isEmpty()) {
                return ResponseBuilder.notFound("No daily mails found for the given barcode ID: " + barcodeId);
            }

            return ResponseBuilder.success("Daily mails retrieved successfully by barcode ID: " + barcodeId, allMailActivityByBarcodeId);

        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error retrieving daily mails by barcode ID: " + e.getMessage());

            return ResponseBuilder.error("Error retrieving daily mails by barcode ID.", null);
        }
    }



    @GetMapping("/daily-mail-filter")
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getMailActivityByFilter(
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName,
            @RequestParam(required = false) String mailType,
            @RequestParam(required = false) String trackingNumber,
            @RequestParam(required = false) String branchName) {

        try {
            List<RequestDailyMailViewAllDTO> mailActivityDTOList = dailyMailService.filterDailyMail(senderName, receiverName, mailType, trackingNumber, branchName);

            if (mailActivityDTOList == null || mailActivityDTOList.isEmpty()) {
                return ResponseBuilder.notFound("No mail activities found with the provided filters.");
            }

            return ResponseBuilder.success("Mail activities retrieved successfully", mailActivityDTOList);

        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error retrieving mail activities by filter: " + e.getMessage());

            return ResponseBuilder.error("Error retrieving mail activities.", null);
        }
    }


}
