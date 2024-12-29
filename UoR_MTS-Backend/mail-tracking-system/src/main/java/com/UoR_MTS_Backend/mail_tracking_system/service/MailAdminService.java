package com.UoR_MTS_Backend.mail_tracking_system.service;


import com.UoR_MTS_Backend.mail_tracking_system.dto.MailAdminDTO;

public interface MailAdminService {
        void saveMailAdmin(MailAdminDTO mailAdminDTO);

    void updateMailAdmin(String id, MailAdminDTO mailAdminDTO);

    void deleteMailAdmin(String id);
    Object getAllMailAdmins();
}

