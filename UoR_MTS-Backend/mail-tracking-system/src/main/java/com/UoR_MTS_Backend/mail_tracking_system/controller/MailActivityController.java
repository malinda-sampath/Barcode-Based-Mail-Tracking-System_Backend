package com.UoR_MTS_Backend.mail_tracking_system.controller;
import org.springframework.http.ResponseEntity;
import com.UoR_MTS_Backend.mail_tracking_system.dto.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.UoR_MTS_Backend.mail_tracking_system.utill.StandardResponse;
import com.UoR_MTS_Backend.mail_tracking_system.utill.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("api/mail-activity")
@CrossOrigin
public class MailActivityController {
    @Autowired
    private MailActivityService mailActivityService;

    @GetMapping
    public ResponseEntity<StandardResponse<List<MailActivityDTO>>> getAllMailActivity() {
        try {
            List<MailActivityDTO> mailActivityDTOList = mailActivityService.getAllMailActivity();

            if (mailActivityDTOList == null || mailActivityDTOList.isEmpty()) {
                return ResponseBuilder.notFound("No mail activities found.");
            }

            return ResponseBuilder.success("Mail activities retrieved successfully", mailActivityDTOList);
        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error retrieving all mail activities: " + e.getMessage());

            return ResponseBuilder.error("Error retrieving mail activities.", null);
        }
    }


    @GetMapping("/get-mail-activity-by-barcodeId/{barcodeId}")
    public ResponseEntity<StandardResponse<List<MailActivityDTO>>> getMailActivityByBarcodeId(@PathVariable(value = "barcodeId") String barcodeId) {
        try {
            List<MailActivityDTO> mailActivityDTOList = mailActivityService.getAllMailActivityByBarcodeId(barcodeId);

            if (mailActivityDTOList == null || mailActivityDTOList.isEmpty()) {
                return ResponseBuilder.notFound("No mail activities found for the provided barcode ID.");
            }

            return ResponseBuilder.success("Mail activities retrieved successfully by barcode ID", mailActivityDTOList);
        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error retrieving mail activities by barcode ID: " + e.getMessage());

            return ResponseBuilder.error("Error retrieving mail activities.", null);
        }
    }


    @GetMapping("/mail-activity-filter")
    public ResponseEntity<StandardResponse<List<MailActivityDTO>>> getMailActivityByFilter(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String branchName,
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName) {

        try {
            List<MailActivityDTO> mailActivityDTOList = mailActivityService.filterMailActivities(userName, activityType, branchName, senderName, receiverName);

            if (mailActivityDTOList == null || mailActivityDTOList.isEmpty()) {
                return ResponseBuilder.notFound("No mail activities found with the provided filters.");
            }

            return ResponseBuilder.success("Mail activities retrieved successfully", mailActivityDTOList);
        } catch (Exception e) {
            // Log the error for debugging purposes
            System.err.println("Error retrieving filtered mail activities: " + e.getMessage());

            return ResponseBuilder.error("Error retrieving mail activities.", null);
        }
    }

}
