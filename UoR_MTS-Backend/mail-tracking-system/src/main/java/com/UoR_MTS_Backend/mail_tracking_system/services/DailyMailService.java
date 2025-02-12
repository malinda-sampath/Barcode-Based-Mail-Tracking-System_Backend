package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail.RequestDailyMailViewAllDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DailyMailService {
    public String addDailyMail(DailyMailDTO dailyMailDTO, byte[] barcodeImage, String uniqueID, Authentication authentication);

    public String updateDailyMail(int id,DailyMailDTO dailyMailDTO, byte[] barcodeImage, String uniqueID);

    public String deleteDailyMail(int dailyMailId);

    public List<RequestDailyMailViewAllDTO> getAllDailyMails();
  
    public List<RequestDailyMailViewAllDTO> getAllDailyMailsByBarcodeId(String barcodeId);

    public List<RequestDailyMailViewAllDTO> filterDailyMail(String senderName, String receiverName, String mailType, String trackingNumber, String branchName);
}
