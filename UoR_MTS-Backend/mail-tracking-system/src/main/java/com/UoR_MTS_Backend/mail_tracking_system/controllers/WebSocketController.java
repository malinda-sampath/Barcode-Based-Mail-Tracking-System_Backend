package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendBranchUpdate(Object WCBranchUpdate) {
        messagingTemplate.convertAndSend("/topic/branch-updates", WCBranchUpdate);
    }

    public void sendMailHandlerUpdate(Object WCMailHandlerUpdate) {
        messagingTemplate.convertAndSend("/topic/mail-handler-updates", WCMailHandlerUpdate);
    }
}
