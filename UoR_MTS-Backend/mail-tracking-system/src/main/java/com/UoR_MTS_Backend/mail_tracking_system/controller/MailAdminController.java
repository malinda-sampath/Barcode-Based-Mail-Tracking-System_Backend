package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailAdminDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailAdminService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mail-admin")
public class MailAdminController {

  @Autowired
    private MailAdminService mailAdminService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> mailAdminSave(@RequestBody MailAdminDTO mailAdminDTO) {
        String message = mailAdminService.saveMailAdmin(mailAdminDTO);
        return ResponseBuilder.success(message, null);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> mailAdminUpdate(@PathVariable long id, @RequestBody MailAdminDTO mailAdminDTO) {
        String message = mailAdminService.updateMailAdmin(id, mailAdminDTO);
        return ResponseBuilder.success(message, null);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> mailAdminDelete(@PathVariable long id) {
        String message = mailAdminService.deleteMailAdmin(id);
        return ResponseBuilder.success(message, null);
    }


    @GetMapping("/all/{id}")
    public ResponseEntity<StandardResponse<List<MailAdminDTO>>> allMailAdminsGet() {

            List<MailAdminDTO> mailAdmins = mailAdminService.getAllMailAdmins(); // Calls the service layer
            return ResponseBuilder.success("Mail admins retrieved successfully.", mailAdmins);

    }
}