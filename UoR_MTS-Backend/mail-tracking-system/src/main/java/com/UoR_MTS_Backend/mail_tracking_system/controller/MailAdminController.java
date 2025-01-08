package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.MailAdminDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.MailAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/mail-admin")
public class MailAdminController {

  @Autowired
    private MailAdminService mailAdminService;

    // Method to save a new MailAdmin
    @PostMapping("/save")
    public ResponseEntity<String> mailAdminSave(@RequestBody MailAdminDTO mailAdminDTO) {
        try {
            mailAdminService.saveMailAdmin(mailAdminDTO); // Calls the service layer
            return ResponseEntity.ok("Mail admin saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/Update/{id}")
    public ResponseEntity<String> MailAdminUpdate(@PathVariable long id, @RequestBody MailAdminDTO mailAdminDTO) {
        try {
            mailAdminService.updateMailAdmin(id, mailAdminDTO); // Calls the service layer
            return ResponseEntity.ok("Mail admin updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>MailAdminDelete(@PathVariable long id) {
        try {
            mailAdminService.deleteMailAdmin(id); // Calls the service layer
            return ResponseEntity.ok("Mail admin deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<?>AllMailAdminsGet() {
        try {
            return ResponseEntity.ok(mailAdminService.getAllMailAdmins()); // Calls the service layer
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}