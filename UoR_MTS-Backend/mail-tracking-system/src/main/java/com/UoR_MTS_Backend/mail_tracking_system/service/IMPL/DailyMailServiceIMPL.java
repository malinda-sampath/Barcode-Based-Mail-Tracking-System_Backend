package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.config.ModelMapperConfig;
import com.UoR_MTS_Backend.mail_tracking_system.dto.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.repo.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.DailyMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyMailServiceIMPL implements DailyMailService {

    @Autowired
    private ModelMapperConfig modelMapper;

    @Autowired
    private DailyMailRepo dailyMailRepo;

    @Override
    public String addDailyMail(DailyMailDTO dailyMailDTO, byte[] barcodeImage, String uniqueID) {
        LocalDateTime now = LocalDateTime.now();

        DailyMail dailyMail = modelMapper.modelMapper().map(dailyMailDTO, DailyMail.class);
        dailyMail.setBarcodeId("MAIL - " + uniqueID);
        dailyMail.setBarcodeImage(barcodeImage);
        dailyMail.setInsertDateTime(now);

        dailyMailRepo.save(dailyMail);

        return dailyMail.getBarcodeId() + " saved with barcode!";
    }

    @Override
    public String updateDailyMail(RequestDailyMailDTO requestDailyMailDTO, byte[] barcodeImage, String uniqueID) {

        if(dailyMailRepo.existsById(requestDailyMailDTO.getDailyMailId())){

            LocalDateTime now = LocalDateTime.now();
            DailyMail dailyMail = modelMapper.modelMapper().map(requestDailyMailDTO, DailyMail.class);

            dailyMail.setBarcodeId("MAIL - " + uniqueID);
            dailyMail.setBarcodeImage(barcodeImage);
            dailyMail.setUpdateDateTime(now);

            dailyMailRepo.save(dailyMail);
            return "Mail id "+dailyMail.getDailyMailId()+ " updated successfully";

        } else {
            throw new RuntimeException("No data found for that id");
        }
    }

    @Override
    public String deleteDailyMail(int dailyMailId) {
        if (dailyMailRepo.existsById(dailyMailId)){

            dailyMailRepo.deleteById(dailyMailId);
            return "Mail id "+dailyMailId+ " deleted successfully";

        } else {
            throw new RuntimeException("No data found for that id");
        }
    }

    @Override
    public List<RequestDailyMailViewAllDTO> getAllDailyMails() {
        List<DailyMail> mailList = dailyMailRepo.findAll();
        return mailList.stream()
                .sorted(Comparator.comparing(DailyMail::getDailyMailId))
                .map(mail -> new RequestDailyMailViewAllDTO(
                        mail.getDailyMailId(),
                        mail.getBranchCode(),
                        mail.getBranchName(),
                        mail.getSenderName(),
                        mail.getReceiverName(),
                        mail.getMailType(),
                        mail.getTrackingNumber(),
                        mail.getMailDescription(),
                        mail.getBarcodeId(),
                        mail.getBarcodeImage(),
                        mail.getInsertDateTime(),
                        mail.getUpdateDateTime()
                ))
                .collect(Collectors.toList());
    }
}
