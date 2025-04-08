package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailClaimDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailClaimPerson;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailClaimReference;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailClaimPersonRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailClaimRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailRecordRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailClaimService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MailClaimServiceIMPL implements MailClaimService {

    private final MailClaimRepo mailClaimRepo;
    private final MailClaimPersonRepo mailClaimPersonRepo;
    private final MailRecordRepo mailRecordRepo;

    @Override
    @Transactional
    public String ClaimMailDetailsSave(MailClaimDetailsDTO claimDetailsDTO) {

        List<MailRecord> claimMails = mailRecordRepo.findAllByBarcodeIdIn(claimDetailsDTO.getBarcodeID());

        if (claimMails.isEmpty()) {
            throw new ResourceNotFoundException("Mail records not found for the provided barcode IDs.");
        }

        String status = claimDetailsDTO.getStatus();

        for (MailRecord mailRecord : claimMails) {

            mailRecord.setReferenceNumber(claimDetailsDTO.getReferenceNumber());

            if ("picked".equalsIgnoreCase(status)) {
                mailRecord.setStatus("picked");
                mailRecord.setLocation(claimDetailsDTO.getBranchCode());



            } else if ("claimed".equalsIgnoreCase(status)) {
                mailRecord.setStatus("claimed");
                mailRecord.setLocation(claimDetailsDTO.getPersonName()); // Cleared since it's been claimed

            } else if ("returned".equalsIgnoreCase(status)) {
                mailRecord.setStatus("returned");
                mailRecord.setLocation(claimDetailsDTO.getBranchCode()); // Reset location if returned
            } else {
                return "Invalid status provided.";
            }

            mailRecordRepo.save(mailRecord);
        }

            MailClaimPerson mailClaimPerson = new MailClaimPerson(
                    claimDetailsDTO.getPersonName(),
                    claimDetailsDTO.getPersonContact(),
                    claimDetailsDTO.getNIC(),
                    claimDetailsDTO.getNote()
            );

            mailClaimPersonRepo.save(mailClaimPerson);

            MailClaimReference mailClaimReference = new MailClaimReference(
                    claimDetailsDTO.getReferenceNumber(),
                    mailClaimPerson
            );

            mailClaimRepo.save(mailClaimReference);

        return "Mail claim details saved successfully.";
    }

}
