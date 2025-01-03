package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailActivityDTO;

import java.util.List;

public interface MailActivityService {
    public List<MailActivityDTO> getAllMailActivity();

    public List<MailActivityDTO> getAllMailActivityByBarcodeId(String barcodeId);

    public List<MailActivityDTO> filterMailActivities(String userName, String activityType, String branchName, String senderName, String receiverName);
}
