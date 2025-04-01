package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailHandlerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailHandlerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailHandlerService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mail-handler")
@AllArgsConstructor
public class MailHandlerController {

    private final MailHandlerService mailHandlerService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<String>> registerMailHandler(@RequestBody MailHandlerRequestDTO mailHandlerRequestDTO){
        String message = mailHandlerService.saveMailHandler(mailHandlerRequestDTO);
        return ResponseBuilder.success(message,null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StandardResponse<String>> mailHandlerUpdate(@PathVariable long id, @RequestBody MailHandlerRequestDTO mailHandlerRequestDTO) {
        String message = mailHandlerService.updateMailHandler(id, mailHandlerRequestDTO);
        return ResponseBuilder.success(message, null);
    }

    @DeleteMapping("/delete/{userID}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<String>> mailHandlerDelete(@PathVariable String userID) {
        String message = mailHandlerService.deleteMailHandler(userID);
        return ResponseBuilder.success(message, null);
    }

    @GetMapping("get")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<List<MailHandlerResponseDTO>>> allMailHandlersGet() {
        List<MailHandlerResponseDTO> mailHandlers = mailHandlerService.getAllMailHandlers(); // Calls the service layer
        return ResponseBuilder.success("Mail Handlers retrieved successfully.", mailHandlers);
    }
}