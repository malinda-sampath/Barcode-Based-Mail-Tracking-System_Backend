package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailHandlerDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailHandler;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailHandlerException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailHandlerRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailHandlerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MailHandlerServiceIMPL implements MailHandlerService {

    private final MailHandlerRepo mailHandlerRepo;

    @Override
    public String saveMailHandler(MailHandlerDTO mailHandlerDTO) {
        if (mailHandlerRepo.existsByEmail(mailHandlerDTO.getEmail())) {
            throw new MailHandlerException("Email is already in use.");
        }

        if (mailHandlerRepo.existsByUserName(mailHandlerDTO.getUserName())) {
            throw new MailHandlerException("Username is already in use.");
        }

        if (!mailHandlerDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new MailHandlerException("Invalid email format.");
        }


        MailHandler mailHandler = new MailHandler();
        mailHandler.setName(mailHandlerDTO.getName());
        mailHandler.setEmail(mailHandlerDTO.getEmail());
        mailHandler.setUserName(mailHandlerDTO.getUserName());
        mailHandler.setPassword("12345");
        mailHandler.setRole(mailHandlerDTO.getRole());
        mailHandler.setContact(mailHandlerDTO.getContact());
        mailHandler.setInsertDate(LocalDateTime.now());






        try {
            mailHandlerRepo.save(mailHandler);

            return "Mail Handler saved successfully.";
        }catch(Exception e){

            throw new RuntimeException("Error Save Mail Handler"+e.getMessage());
        }
    }



    @Override
    public String updateMailHandler(long id, MailHandlerDTO mailHandlerDTO) {

        MailHandler mailHandler = mailHandlerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No MailHandler found with the provided ID: " + id));


        if (!mailHandler.getEmail().equals(mailHandlerDTO.getEmail()) && mailHandlerRepo.existsByEmail(mailHandlerDTO.getEmail())) {
            throw new MailHandlerException("Email is already in use.");
        }


        if (!mailHandler.getUserName().equals(mailHandlerDTO.getUserName()) && mailHandlerRepo.existsByUserName(mailHandlerDTO.getUserName())) {
            throw new MailHandlerException("Username is already in use.");
        }


        mailHandler.setName(mailHandlerDTO.getName());
        mailHandler.setUserName(mailHandlerDTO.getUserName());
        mailHandler.setEmail(mailHandlerDTO.getEmail());
        mailHandler.setContact(mailHandlerDTO.getContact());
        mailHandler.setPassword(mailHandlerDTO.getPassword());
        mailHandler.setRole(mailHandlerDTO.getRole());
        mailHandler.setUpdateDate(LocalDateTime.now());




        try {
            mailHandlerRepo.save(mailHandler);

            return "Mail Handler updated successfully.";


        }catch(Exception e){

            throw new RuntimeException("Error Update Mail Handler"+e.getMessage());
        }
    }



    @Override
    public String deleteMailHandler(long id) {
        if (!mailHandlerRepo.existsById(id)) {
            throw new UserNotFoundException("No MailHandler found with the provided id.");
        }



        try {
            mailHandlerRepo.deleteById(id);


            return "Mail Handler deleted successfully.";


        }catch(Exception e){

            throw new RuntimeException("Error Deleting Mail Handler"+e.getMessage());
        }



    }


    @Override
    public List<MailHandlerDTO> getAllMailHandlers() {
        List<MailHandler> mailHandlers = mailHandlerRepo.findAll();

        if (mailHandlers.isEmpty()) {
            throw new EntityNotFoundException("No mail Handlers found.");
        }

        return mailHandlers.stream().map(mailHandler -> {
            MailHandlerDTO dto = new MailHandlerDTO();
            dto.setName(mailHandler.getName());
            dto.setUserName(mailHandler.getUserName());
            dto.setEmail(mailHandler.getEmail());
            dto.setContact(mailHandler.getContact());
            dto.setPassword(mailHandler.getPassword());
            dto.setRole(mailHandler.getRole());
            dto.setInsertDate(mailHandler.getInsertDate());
            dto.setUpdateDate(mailHandler.getUpdateDate());
            return dto;
        }).collect(Collectors.toList());
    }

}
