package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailHandlerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailHandlerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;

import java.util.List;

public interface MailHandlerService {
    String saveMailHandler(MailHandlerRequestDTO mailHandlerRequestDTO);

    String updateMailHandler(long id, MailHandlerRequestDTO mailHandlerRequestDTO);

    String deleteMailHandler(String userID);

    List<MailHandlerResponseDTO> getAllMailHandlers();
}

