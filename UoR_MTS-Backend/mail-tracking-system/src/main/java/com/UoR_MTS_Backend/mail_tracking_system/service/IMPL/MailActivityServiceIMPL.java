package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.config.ModelMapperConfig;
import com.UoR_MTS_Backend.mail_tracking_system.dto.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailActivity;
import com.UoR_MTS_Backend.mail_tracking_system.repo.MailActivityRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repo.specification.MailActivitySpecification;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailActivityServiceIMPL implements MailActivityService {

    @Autowired
    private MailActivityRepo mailActivityRepo;

    @Autowired
    private ModelMapperConfig modelMapper;

    @Override
    public List<MailActivityDTO> getAllMailActivity() {

        List<MailActivity> mailActivityList = mailActivityRepo.findAll();

        return mailActivityList.stream()
                .sorted(Comparator.comparing(MailActivity::getActivityLogId))
                .map(mailActivity -> new MailActivityDTO(
                        mailActivity.getActivityLogId(),
                        mailActivity.getUserId(),
                        mailActivity.getUserName(),
                        mailActivity.getActivityType(),
                        mailActivity.getBranchName(),
                        mailActivity.getSenderName(),
                        mailActivity.getReceiverName(),
                        mailActivity.getBarcodeId(),
                        mailActivity.getActivityDateTime()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<MailActivityDTO> getAllMailActivityByBarcodeId(String barcodeId) {

        List<MailActivity> mailActivityList = mailActivityRepo.findAllByBarcodeIdEquals(barcodeId);

        return mailActivityList.stream()
                .sorted(Comparator.comparing(MailActivity::getActivityLogId))
                .map(mailActivity -> new MailActivityDTO(
                        mailActivity.getActivityLogId(),
                        mailActivity.getUserId(),
                        mailActivity.getUserName(),
                        mailActivity.getActivityType(),
                        mailActivity.getBranchName(),
                        mailActivity.getSenderName(),
                        mailActivity.getReceiverName(),
                        mailActivity.getBarcodeId(),
                        mailActivity.getActivityDateTime()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<MailActivityDTO> filterMailActivities(
            String userName,
            String activityType,
            String branchName,
            String senderName,
            String receiverName) {

        List<MailActivity> filteredMailActivities = mailActivityRepo.findAll(
                MailActivitySpecification.filterBy(
                        userName,
                        activityType,
                        branchName,
                        senderName,
                        receiverName
                )
            );

        return filteredMailActivities.stream()
                .sorted(Comparator.comparing(MailActivity::getActivityLogId))
                .map(mailActivity -> new MailActivityDTO(
                        mailActivity.getActivityLogId(),
                        mailActivity.getUserId(),
                        mailActivity.getUserName(),
                        mailActivity.getActivityType(),
                        mailActivity.getBranchName(),
                        mailActivity.getSenderName(),
                        mailActivity.getReceiverName(),
                        mailActivity.getBarcodeId(),
                        mailActivity.getActivityDateTime()
                ))
                .collect(Collectors.toList());
    }
}
