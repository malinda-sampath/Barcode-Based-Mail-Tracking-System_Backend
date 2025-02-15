package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.service.BranchMailService;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailRecordService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("branch-mail")
public class BranchMailController {

    @Autowired
    private BranchMailService branchMailService;

    @Autowired
    private MailRecordService mailRecordService;

    @PostMapping("/transfer-to-branch-cart")
    public ResponseEntity<String> transferMailToBranchCart(@RequestBody List<String> barcodeIds/*, @RequestBody MailRecordDTO mailRecordDTO*/) {

        for(String barcodeId : barcodeIds) {
            MailRecord mailRecord = mailRecordService.searchMailByBarcodeId(barcodeId);

            // Validate that the mail belongs to the selected branch
        /*if (!mailRecord.getBranchName().equals(mailRecordDTO.getBranchName()))
            return ResponseEntity.badRequest().body("Mail does not belong to the selected branch.");
        else {*/
            String addToBranchMailcart = branchMailService.transferMailToBranchCart(barcodeId);
            mailRecord.setUpdateDateTime(LocalDateTime.now());
            branchMailService.updateMailRecordList(barcodeId);
            return ResponseEntity.ok("Mail transferred successfully." + addToBranchMailcart);
        }
        return ResponseEntity.ok("Mail transferred successfully.");
        //}
    }

    @GetMapping("/get-all-mails-by-branch/{branch}")
    public ResponseEntity<List<MailRecordDTO>> getMailsByBranch(@PathVariable(value = "branch") String branch) {
        List<MailRecordDTO> mails = branchMailService.getMailsByBranch(branch);
        return ResponseEntity.ok(mails);
    }

    @GetMapping("/claimed-mails")
    public ResponseEntity<?> getClaimedMails(@RequestParam(defaultValue = "0") int page) {
        try {
            int size = 10;
            Pageable pageable = PageRequest.of(page, size);

            // Call the service method to fetch mail records
            Page<MailRecord> mailRecords = mailRecordService.getAllMailRecords(pageable);
            List<MailRecord> claimedMailRecords = new ArrayList<>();
            for (MailRecord mailRecord : mailRecords) {
                if(mailRecord.getUpdateDateTime() != null)
                    claimedMailRecords.add(mailRecord);
            }
            // Check if the result is empty
            if (claimedMailRecords.isEmpty()) {
                return ResponseBuilder.notFound("No mail records found.");
            }

            // Return successful response
            return ResponseBuilder.success(null, claimedMailRecords);
        } catch (Exception e) {
            // Log the exception and return error response
            return ResponseBuilder.error("Error retrieving mail records:" + e.getMessage(), null);
        }
    }
}
