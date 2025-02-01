package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailAdminDTO;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailAdminException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailAdmin;
import com.UoR_MTS_Backend.mail_tracking_system.repo.MailAdminRepo;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailAdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailAdminServiceIMPL implements MailAdminService {

    @Autowired
    private MailAdminRepo mailAdminRepo;

    @Override
    public String saveMailAdmin(MailAdminDTO mailAdminDTO) {
        if (mailAdminRepo.existsByEmail(mailAdminDTO.getEmail())) {
            throw new MailAdminException("Email is already in use.");
        }

        if (mailAdminRepo.existsByUserName(mailAdminDTO.getUserName())) {
            throw new MailAdminException("Username is already in use.");
        }

        if (!mailAdminDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new MailAdminException("Invalid email format.");
        }


        MailAdmin mailAdmin = new MailAdmin();
        mailAdmin.setName(mailAdminDTO.getName());
        mailAdmin.setEmail(mailAdminDTO.getEmail());
        mailAdmin.setUserName(mailAdminDTO.getUserName());
        mailAdmin.setPassword("12345");
        mailAdmin.setRole(mailAdminDTO.getRole());
        mailAdmin.setContact(mailAdminDTO.getContact());
        mailAdmin.setInsertDate(LocalDateTime.now());






        try {
            mailAdminRepo.save(mailAdmin);

            return "Mail admin saved successfully.";
        }catch(Exception e){

            throw new RuntimeException("Error Save Mail Admin"+e.getMessage());
        }
    }



    @Override
    public String updateMailAdmin(long id, MailAdminDTO mailAdminDTO) {

        MailAdmin mailAdmin = mailAdminRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No MailAdmin found with the provided ID: " + id));


        if (!mailAdmin.getEmail().equals(mailAdminDTO.getEmail()) && mailAdminRepo.existsByEmail(mailAdminDTO.getEmail())) {
            throw new MailAdminException("Email is already in use.");
        }


        if (!mailAdmin.getUserName().equals(mailAdminDTO.getUserName()) && mailAdminRepo.existsByUserName(mailAdminDTO.getUserName())) {
            throw new MailAdminException("Username is already in use.");
        }


        mailAdmin.setName(mailAdminDTO.getName());
        mailAdmin.setUserName(mailAdminDTO.getUserName());
        mailAdmin.setEmail(mailAdminDTO.getEmail());
        mailAdmin.setContact(mailAdminDTO.getContact());
        mailAdmin.setPassword(mailAdminDTO.getPassword());
        mailAdmin.setRole(mailAdminDTO.getRole());
        mailAdmin.setUpdateDate(LocalDateTime.now());




        try {
            mailAdminRepo.save(mailAdmin);

            return "Mail admin updated successfully.";


        }catch(Exception e){

            throw new RuntimeException("Error Update Mail Admin"+e.getMessage());
        }
    }



    @Override
    public String deleteMailAdmin(long id) {
        if (!mailAdminRepo.existsById(id)) {
            throw new UserNotFoundException("No MailAdmin found with the provided id.");
        }



        try {
            mailAdminRepo.deleteById(id);


            return "Mail admin deleted successfully.";


        }catch(Exception e){

            throw new RuntimeException("Error Deleting Mail Admin"+e.getMessage());
        }



    }


    @Override
    public List<MailAdminDTO> getAllMailAdmins() {
        List<MailAdmin> mailAdmins = mailAdminRepo.findAll();

        if (mailAdmins.isEmpty()) {
            throw new EntityNotFoundException("No mail admins found.");
        }

        return mailAdmins.stream().map(mailAdmin -> {
            MailAdminDTO dto = new MailAdminDTO();
            dto.setName(mailAdmin.getName());
            dto.setUserName(mailAdmin.getUserName());
            dto.setEmail(mailAdmin.getEmail());
            dto.setContact(mailAdmin.getContact());
            dto.setPassword(mailAdmin.getPassword());
            dto.setRole(mailAdmin.getRole());
            dto.setInsertDate(mailAdmin.getInsertDate());
            dto.setUpdateDate(mailAdmin.getUpdateDate());
            return dto;
        }).collect(Collectors.toList());
    }

}
