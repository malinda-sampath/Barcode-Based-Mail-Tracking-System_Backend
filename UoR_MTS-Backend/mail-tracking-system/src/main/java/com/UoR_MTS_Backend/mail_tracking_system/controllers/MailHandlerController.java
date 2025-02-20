package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailHandlerDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailHandlerService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mail-Handler")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('MAIL_HANDLER')")
public class MailHandlerController {

    private final MailHandlerService mailHandlerService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> mailHandlerSave(@RequestBody MailHandlerDTO mailHandlerDTO) {
        String message = mailHandlerService.saveMailHandler(mailHandlerDTO);
        return ResponseBuilder.success(message, null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> mailHandlerUpdate(@PathVariable long id, @RequestBody MailHandlerDTO mailHandlerDTO) {
        String message = mailHandlerService.updateMailHandler(id, mailHandlerDTO);
        return ResponseBuilder.success(message, null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse<String>> mailHandlerDelete(@PathVariable long id) {
        String message = mailHandlerService.deleteMailHandler(id);
        return ResponseBuilder.success(message, null);
    }

    @GetMapping("get")
    public ResponseEntity<StandardResponse<List<MailHandlerDTO>>> allMailHandlersGet() {

        List<MailHandlerDTO> mailHandlers = mailHandlerService.getAllMailHandlers(); // Calls the service layer
        return ResponseBuilder.success("Mail Handlers retrieved successfully.", mailHandlers);

    }
}