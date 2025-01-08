package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailAdminDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailAdmin;
import com.UoR_MTS_Backend.mail_tracking_system.repo.MailAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        mailAdmin.setRole(mailAdminDTO.getRole());
        mailAdmin.setContact(mailAdminDTO.getContact());
        mailAdmin.setInsertDate(LocalDateTime.now());

        mailAdminRepo.save(mailAdmin);
    }

    @Override
    public void updateMailAdmin(long id, MailAdminDTO mailAdminDTO) {
        // Fetch the existing MailAdmin entity by id
        MailAdmin mailAdmin = mailAdminRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No MailAdmin found with the provided id."));

        mailAdmin.setName(mailAdminDTO.getName());
        mailAdmin.setUserName(mailAdminDTO.getUserName());
        mailAdmin.setEmail(mailAdminDTO.getEmail());
        mailAdmin.setContact(mailAdminDTO.getContact());
        mailAdmin.setPassword(mailAdminDTO.getPassword());
        mailAdmin.setRole(mailAdminDTO.getRole());
        mailAdmin.setUpdateDate(LocalDateTime.now());

        mailAdminRepo.save(mailAdmin);
    }

    @Override
    public void deleteMailAdmin(long id) {
        if (!mailAdminRepo.existsById(id)) {
            throw new IllegalArgumentException("No MailAdmin found with the provided id.");
        }

        mailAdminRepo.deleteById(id);
    }

    @Override
    public List<MailAdminDTO> getAllMailAdmins() {
        List<MailAdmin> mailAdmins = mailAdminRepo.findAll();
        List<MailAdminDTO> mailAdminDTOs = new ArrayList<>();

        for (MailAdmin mailAdmin : mailAdmins) {
            MailAdminDTO dto = new MailAdminDTO();

            dto.setName(mailAdmin.getName());
            dto.setUserName(mailAdmin.getUserName());
            dto.setEmail(mailAdmin.getEmail());
            dto.setContact(mailAdmin.getContact());
            dto.setPassword(mailAdmin.getPassword());
            dto.setRole(mailAdmin.getRole());
            dto.setInsertDate(mailAdmin.getInsertDate());
            dto.setUpdateDate(mailAdmin.getUpdateDate());
            mailAdminDTOs.add(dto);
        }

        return mailAdminDTOs;
    }
}
