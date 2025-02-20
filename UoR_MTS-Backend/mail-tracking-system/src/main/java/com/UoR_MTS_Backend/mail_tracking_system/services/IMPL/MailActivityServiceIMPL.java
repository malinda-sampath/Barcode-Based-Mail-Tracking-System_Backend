package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailActivityNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailActivity;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailActivityRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailActivityService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MailActivityServiceIMPL implements MailActivityService {

    private final MailActivityRepo mailActivityRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<MailActivityDTO> getAllMailActivity() {

        List<MailActivity> mailActivityList = mailActivityRepo.findAll();


        if (mailActivityList.isEmpty()) {
            throw new ResourceNotFoundException("No mail activities found.");
        }

        return mailActivityList.stream()
                .map(mailActivity-> modelMapper.map(mailActivity, MailActivityDTO.class))
                .collect(Collectors.toList());

    }


    @Override
    public List<MailActivityDTO> getAllMailActivityByBarcodeId(String barcodeId) {
        List<MailActivity> mailActivityList = mailActivityRepo.findAllByBarcodeIdEquals(barcodeId);

        if (mailActivityList == null || mailActivityList.isEmpty()) {
            throw new MailActivityNotFoundException("No mail activities found for the provided barcode ID: " + barcodeId);
        }

        return mailActivityList.stream()
                .sorted(Comparator.comparing(MailActivity::getId))
                .map(mailActivity -> modelMapper.map(mailActivity, MailActivityDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<MailActivityDTO> filterMailActivities(String userName, String activityType, String branchCode, String senderName, String receiverName) {
        List<MailActivity> mailActivityList = mailActivityRepo.findAll().stream()
                .filter(mailActivity -> (userName == null || mailActivity.getUser().getUsername().equalsIgnoreCase(userName)) &&
                        (activityType == null || mailActivity.getActivityType().equalsIgnoreCase(activityType)) &&
                        (branchCode == null || mailActivity.getBranchCode().equalsIgnoreCase(branchCode)) &&
                        (senderName == null || mailActivity.getSenderName().equalsIgnoreCase(senderName)) &&
                        (receiverName == null || mailActivity.getReceiverName().equalsIgnoreCase(receiverName)))
                .sorted(Comparator.comparing(MailActivity::getId))
                .collect(Collectors.toList());

        if (mailActivityList.isEmpty()) {
            throw new MailActivityNotFoundException("No mail activities found with the provided filters.");
        }

        return mailActivityList.stream()
                .map(mailActivity -> modelMapper.map(mailActivity, MailActivityDTO.class))
                .collect(Collectors.toList());
    }

}
