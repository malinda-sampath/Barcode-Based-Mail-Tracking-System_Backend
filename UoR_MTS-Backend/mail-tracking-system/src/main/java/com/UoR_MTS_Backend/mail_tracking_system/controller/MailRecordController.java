package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailRecordService;
//import org.hibernate.query.Page;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/mailRecord")
public class MailRecordController {
    @Autowired
    private MailRecordService mailRecordService;

    @PostMapping("/transfer")
    public String transferDailyMailsToMainCart() {
        return mailRecordService.transferDailyMailsToMainCart();
    }

    @GetMapping("/search")
    public Page<MailRecord> searchAndFilterMails(
            @RequestParam(required = false) String branchName,
            @RequestParam(required = false) LocalDateTime date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return mailRecordService.searchAndFilterMails("main", branchName, date, page, size);
    }
}
