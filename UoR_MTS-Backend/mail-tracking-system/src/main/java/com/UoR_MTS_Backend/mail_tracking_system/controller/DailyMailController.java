package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailViewAllDTO;
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
            String uniqueID = BarcodeIDGenerator.generateUniqueId();

            byte[] barcodeImage = BarcodeImageGenerator.generateBarcode(uniqueID);

            String message = dailyMailService.updateDailyMail(requestDailyMailDTO, barcodeImage, uniqueID);

            return message;

        } catch (IOException | WriterException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(path = "/delete-daily-mail/{id}")
    public String deleteDailyMail(@PathVariable(value = "id") int dailyMailId) {

        String message = dailyMailService.deleteDailyMail(dailyMailId);

        return message;
    }

    @GetMapping("/get-all-daily-mails")
    public List<RequestDailyMailViewAllDTO> getAllDailyMails() {
        List<RequestDailyMailViewAllDTO> dailyMails = dailyMailService.getAllDailyMails();
        return dailyMails;
    }
}
