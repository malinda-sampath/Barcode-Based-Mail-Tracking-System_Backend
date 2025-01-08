package com.UoR_MTS_Backend.mail_tracking_system.service;


import com.UoR_MTS_Backend.mail_tracking_system.dto.MailAdminDTO;

import java.util.List;

public interface MailAdminService {
    void saveMailAdmin(MailAdminDTO mailAdminDTO);

    void updateMailAdmin(long id, MailAdminDTO mailAdminDTO);

    void deleteMailAdmin(long id);

    List<MailAdminDTO> getAllMailAdmins();

}

