package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface MailRecordService {
    /**
     * Transfer all daily mails to the main mail cart.
     * @return Success or failure message.
     */
    String transferDailyMailsToMainCart();

    /**
     * Search and filter mails based on the provided criteria.
     * @param cartType The type of the cart (daily or main).
     * @param branchName The branch name to filter by.
     * @param date The date to filter by.
     * @param page The page number for pagination.
     * @param size The size of each page for pagination.
     * @return Paginated list of MailRecord matching the criteria.
     */
    Page<MailRecord> searchAndFilterMails(String cartType, String branchName, LocalDateTime date, int page, int size);

    /**
     * Add a new mail record.
     * @param mailRecordDTO The DTO containing mail record details.
     * @return The created mail record.
     */
    MailRecord addMailRecord(MailRecordDTO mailRecordDTO);
}
