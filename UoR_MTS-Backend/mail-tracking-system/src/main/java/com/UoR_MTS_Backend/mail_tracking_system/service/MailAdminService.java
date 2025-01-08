package com.UoR_MTS_Backend.mail_tracking_system.service;


import com.UoR_MTS_Backend.mail_tracking_system.dto.MailAdminDTO;

import java.util.List;

public interface MailAdminService {
    String saveMailAdmin(MailAdminDTO mailAdminDTO);

    String updateMailAdmin(long id, MailAdminDTO mailAdminDTO);

    String deleteMailAdmin(long id);

    List<MailAdminDTO> getAllMailAdmins();

}

