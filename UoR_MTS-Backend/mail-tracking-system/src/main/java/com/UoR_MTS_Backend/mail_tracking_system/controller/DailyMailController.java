package com.UoR_MTS_Backend.mail_tracking_system.controller;
import org.springframework.http.ResponseEntity;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.dto.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.DailyMailService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.barcode.BarcodeIDGenerator;
import com.UoR_MTS_Backend.mail_tracking_system.utill.barcode.BarcodeImageGenerator;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/daily-mail")
@CrossOrigin
public class DailyMailController {

    @Autowired
    private DailyMailService dailyMailService;

    @PostMapping("/add-daily-mail")
    public ResponseEntity<StandardResponse<String>> addDailyMail(@RequestBody DailyMailDTO dailyMailDTO) throws WriterException,IOException  {
        if (dailyMailDTO == null) {
            throw new IllegalArgumentException("DailyMailDTO cannot be null.");
        }

        String uniqueID = BarcodeIDGenerator.generateUniqueId();
        byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);


        String message = dailyMailService.addDailyMail(dailyMailDTO, barcodeImage, uniqueID);


        return ResponseBuilder.success(message, null);
    }



    @PutMapping("/update-daily-mail")
    public ResponseEntity<StandardResponse<String>> updateDailyMail(@RequestBody RequestDailyMailDTO requestDailyMailDTO)throws WriterException,IOException {

        String uniqueID = BarcodeIDGenerator.generateUniqueId();

        byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);


        String message = dailyMailService.updateDailyMail(requestDailyMailDTO, barcodeImage, uniqueID);

        return ResponseBuilder.success(message, null);
    }


    @DeleteMapping(path = "/delete-daily-mail/{id}")
    public ResponseEntity<StandardResponse<String>> deleteDailyMail(@PathVariable(value = "id") int dailyMailId) {
        String message = dailyMailService.deleteDailyMail(dailyMailId);

        if (message != null) {
            return ResponseBuilder.success(message, null);
        } else {
            return ResponseBuilder.notFound("Daily mail with ID " + dailyMailId + " not found.");
        }
    }



    @GetMapping("/get-all-daily-mails")
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getAllDailyMails() {

        List<RequestDailyMailViewAllDTO> dailyMails = dailyMailService.getAllDailyMails();

        if (dailyMails == null || dailyMails.isEmpty()) {
            return ResponseBuilder.notFound("No daily mails found.");
        }

        // Add the missing return statement here
        return ResponseBuilder.success("Daily mails retrieved successfully.", dailyMails);
    }





    @GetMapping("/get-daily-mails-by-barcodeId/{barcodeId}")
    public ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> getAllDailyMailsByBarcodeId(@PathVariable String barcodeId) {

        List<RequestDailyMailViewAllDTO> allMailActivityByBarcodeId = dailyMailService.getAllDailyMailsByBarcodeId(barcodeId);

        if (allMailActivityByBarcodeId == null || allMailActivityByBarcodeId.isEmpty()) {
            return ResponseBuilder.notFound("No daily mails found for the given barcode ID: " + barcodeId);
        }


        return ResponseBuilder.success("Daily mails retrieved successfully by barcode ID: " + barcodeId, allMailActivityByBarcodeId);
    }




    @GetMapping("/daily-mail-filter")
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
