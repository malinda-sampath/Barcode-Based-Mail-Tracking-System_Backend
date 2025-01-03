package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.DailyMailService;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mail-activity")
@CrossOrigin
public class MailActivityController {
    @Autowired
    private MailActivityService mailActivityService;

    @GetMapping
    public List<MailActivityDTO> getAllMailActivity() {

        List<MailActivityDTO> mailActivityDTOList = mailActivityService.getAllMailActivity();
        return mailActivityDTOList;
    }

    @GetMapping("/get-mail-activity-by-barcodeId/{barcodeId}")
    public List<MailActivityDTO> getMailActivityByBarcodeId(@PathVariable(value = "barcodeId") String barcodeId) {

        List<MailActivityDTO> mailActivityDTOList = mailActivityService.getAllMailActivityByBarcodeId(barcodeId);
        return mailActivityDTOList;
    }

    @GetMapping("/mail-activity-filter")
    public List<MailActivityDTO> getMailActivityByFilter(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) String branchName,
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName) {

        List<MailActivityDTO> mailActivityDTOList = mailActivityService.filterMailActivities(userName, activityType, branchName, senderName, receiverName);
        return mailActivityDTOList;
    }
}
