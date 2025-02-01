package com.UoR_MTS_Backend.mail_tracking_system.controller;
import org.springframework.http.ResponseEntity;
import com.UoR_MTS_Backend.mail_tracking_system.dto.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;

import java.util.List;

@RestController
@RequestMapping("api/mail-activity")
@CrossOrigin
public class MailActivityController {
    @Autowired
    private MailActivityService mailActivityService;

    @GetMapping
    public ResponseEntity<StandardResponse<List<MailActivityDTO>>> getAllMailActivity() {
        List<MailActivityDTO> mailActivityDTOList = mailActivityService.getAllMailActivity();
        return ResponseBuilder.success("Mail activities retrieved successfully", mailActivityDTOList);
    }



    @GetMapping("/get-mail-activity-by-barcodeId/{barcodeId}")
    public ResponseEntity<StandardResponse<List<MailActivityDTO>>> getMailActivityByBarcodeId(@PathVariable(value = "barcodeId") String barcodeId) {
        List<MailActivityDTO> mailActivityDTOList = mailActivityService.getAllMailActivityByBarcodeId(barcodeId);
        return ResponseBuilder.success("Mail activities retrieved successfully by barcode ID", mailActivityDTOList);
    }



    @GetMapping("/mail-activity-filter")
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
