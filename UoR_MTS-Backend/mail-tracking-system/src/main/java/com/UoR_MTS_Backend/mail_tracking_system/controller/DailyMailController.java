package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.service.DailyMailService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.generator.BarcodeIDGenerator;
import com.UoR_MTS_Backend.mail_tracking_system.utill.generator.BarcodeImageGenerator;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/daily-mail")
@CrossOrigin
public class DailyMailController {

    @Autowired
    private DailyMailService dailyMailService;

    @PostMapping("/add-daily-mail")
    public String addDailyMail(@RequestBody DailyMailDTO dailyMailDTO) {
        try{
            String uniqueID = BarcodeIDGenerator.generateUniqueId();

            byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);

            String message = dailyMailService.addDailyMail(dailyMailDTO, barcodeImage, uniqueID);

            return message;

        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update-daily-mail")
    public String updateDailyMail(@RequestBody RequestDailyMailDTO requestDailyMailDTO) {
        try{
            String message = dailyMailService.updateDailyMail(requestDailyMailDTO);

            return message;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(path = "/delete-daily-mail/{id}")
    public String deleteDailyMail(@PathVariable(value = "id") int dailyMailId) {

        String message = dailyMailService.deleteDailyMail(dailyMailId);

        return message;
    }

    @GetMapping
    public List<RequestDailyMailViewAllDTO> getAllDailyMails() {
        List<RequestDailyMailViewAllDTO> dailyMails = dailyMailService.getAllDailyMails();
        return dailyMails;
    }

    @GetMapping("/get-daily-mails-by-barcodeId/{barcodeId}")
    public List<RequestDailyMailViewAllDTO> getAllDailyMailsByBarcodeId(@PathVariable(value = "barcodeId") String barcodeId) {

        List<RequestDailyMailViewAllDTO> allMailActivityByBarcodeId = dailyMailService.getAllDailyMailsByBarcodeId(barcodeId);
        return allMailActivityByBarcodeId;
    }

    @GetMapping("/daily-mail-filter")
    public List<RequestDailyMailViewAllDTO> getMailActivityByFilter(
            @RequestParam(required = false) String senderName,
            @RequestParam(required = false) String receiverName,
            @RequestParam(required = false)String mailType,
            @RequestParam(required = false)String trackingNumber,
            @RequestParam(required = false)String branchName) {

        List<RequestDailyMailViewAllDTO> mailActivityDTOList = dailyMailService.filterDailyMail(senderName, receiverName, mailType, trackingNumber, branchName);
        return mailActivityDTOList;
    }
}
