package com.UoR_MTS_Backend.mail_tracking_system.controllers;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailActivityService;
import org.springframework.web.bind.annotation.*;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("api/mail-activity")
@AllArgsConstructor
public class MailActivityController {

    private final MailActivityService mailActivityService;

    @GetMapping
    public ResponseEntity<StandardResponse<List<MailActivityDTO>>> getAllMailActivity() {
        List<MailActivityDTO> mailActivityDTOList = mailActivityService.getAllMailActivity();
        return ResponseBuilder.success("Mail activities retrieved successfully", mailActivityDTOList);
    }

    @GetMapping("/get/{barcodeId}")
    public ResponseEntity<StandardResponse<List<MailActivityDTO>>> getMailActivityByBarcodeId(@PathVariable(value = "barcodeId") String barcodeId) {
        List<MailActivityDTO> mailActivityDTOList = mailActivityService.getAllMailActivityByBarcodeId(barcodeId);
        return ResponseBuilder.success("Mail activities retrieved successfully by barcode ID", mailActivityDTOList);
    }



    @GetMapping("/filter")
    public ResponseEntity<StandardResponse<List<MailActivityDTO>>> getMailActivityByFilter(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String branchName,
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName) {

        List<MailActivityDTO> mailActivityDTOList = mailActivityService.filterMailActivities(
                userName, activityType, branchName, senderName, receiverName);

        return ResponseBuilder.success("Mail activities retrieved successfully", mailActivityDTOList);
    }
}
