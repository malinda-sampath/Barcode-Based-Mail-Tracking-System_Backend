package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailViewAllDTO;

import java.util.List;

public interface DailyMailService {
    public String addDailyMail(DailyMailDTO dailyMailDTO, byte[] barcodeImage, String uniqueID);

    public String updateDailyMail(RequestDailyMailDTO requestDailyMailDTO, byte[] barcodeImage, String uniqueID);

    public String deleteDailyMail(int dailyMailId);

    public List<RequestDailyMailViewAllDTO> getAllDailyMails();
  
    public List<RequestDailyMailViewAllDTO> getAllDailyMailsByBarcodeId(String barcodeId);

    public List<RequestDailyMailViewAllDTO> filterDailyMail(String senderName, String receiverName, String mailType, String trackingNumber, String branchName);
}
