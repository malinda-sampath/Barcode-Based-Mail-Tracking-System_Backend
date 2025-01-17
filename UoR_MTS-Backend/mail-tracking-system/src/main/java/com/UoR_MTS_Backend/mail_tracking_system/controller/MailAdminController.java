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
        try {
            String message = mailAdminService.saveMailAdmin(mailAdminDTO); // Service method call
            return ResponseBuilder.success(message, null);
        } catch (Exception e) {

            System.err.println("Error saving mail admin: " + e.getMessage());
            return ResponseBuilder.error("Error saving mail admin: " + e.getMessage(), null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> mailAdminUpdate(@PathVariable long id, @RequestBody MailAdminDTO mailAdminDTO) {
        try {
            String message = mailAdminService.updateMailAdmin(id, mailAdminDTO);
            return ResponseBuilder.success(message, null);
        } catch (EntityNotFoundException e) {

            return ResponseBuilder.notFound(e.getMessage());
        } catch (IllegalArgumentException e) {

            return ResponseBuilder.error(e.getMessage(), null);
        } catch (Exception e) {

            System.err.println("Error updating mail admin: " + e.getMessage());
            return ResponseBuilder.error("An unexpected error occurred while updating mail admin.", null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> mailAdminDelete(@PathVariable long id) {
        try {
            String message = mailAdminService.deleteMailAdmin(id);

            // If the message indicates success, return a success response
            return ResponseBuilder.success(message, null);

        } catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound("Mail admin not found with ID: " + id);

        } catch (Exception e) {
            System.err.println("Error deleting mail admin: " + e.getMessage());
            return ResponseBuilder.error("Error deleting mail admin.", null);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<StandardResponse<List<MailAdminDTO>>> allMailAdminsGet() {
        try {
            List<MailAdminDTO> mailAdmins = mailAdminService.getAllMailAdmins(); // Calls the service layer

            if (mailAdmins == null || mailAdmins.isEmpty()) {
                return ResponseBuilder.notFound("No mail admins found.");
            }

            return ResponseBuilder.success("Mail admins retrieved successfully.", mailAdmins);

        } catch (Exception e) {
            System.err.println("Error retrieving mail admins: " + e.getMessage());
            return ResponseBuilder.error("Error retrieving mail admins.", null);
        }
    }
}