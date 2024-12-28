package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailAdminDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailAdmin;
import com.UoR_MTS_Backend.mail_tracking_system.repo.MailAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MailAdminServiceIMPL implements MailAdminService {

    @Autowired
    private MailAdminRepo mailAdminRepo;



    @Override
    public void saveMailAdmin(MailAdminDTO mailAdminDTO) {

        if (mailAdminRepo.existsByEmail(mailAdminDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }


        if (mailAdminRepo.existsByUserName(mailAdminDTO.getUserName())) {
            throw new IllegalArgumentException("Username is already in use.");
        }


        if (!mailAdminDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // Create a new MailAdmin entity
        MailAdmin mailAdmin = new MailAdmin();
        mailAdmin.setName(mailAdminDTO.getName());
        mailAdmin.setEmail(mailAdminDTO.getEmail());
        mailAdmin.setUserName(mailAdminDTO.getUserName());
        mailAdmin.setPassword("12345");


        mailAdminRepo.save(mailAdmin);

        sendNotification(mailAdmin.getEmail(), mailAdmin.getPassword());
    }


    private void sendNotification(String email, String password) {
        System.out.println("Notification sent to " + email + " with password: " + password);
    }
    }
