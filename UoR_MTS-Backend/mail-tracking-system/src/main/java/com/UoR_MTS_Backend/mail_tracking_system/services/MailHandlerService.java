package com.UoR_MTS_Backend.mail_tracking_system.services;


import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailHandlerDTO;

import java.util.List;

public interface MailHandlerService {
    String saveMailHandler(MailHandlerDTO mailHandlerDTO);

    String updateMailHandler(long id, MailHandlerDTO mailHandlerDTO);

    String deleteMailHandler(long id);

    List<MailHandlerDTO> getAllMailHandlers();
}

