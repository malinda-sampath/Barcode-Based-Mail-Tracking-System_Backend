package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailClaimDetailsDTO;

import java.sql.SQLException;


public interface MailClaimService {
    String ClaimMailDetailsSave(MailClaimDetailsDTO claimDetailsDTO) throws SQLException;
}
