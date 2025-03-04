package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

//    @MessageMapping("/branch-updates")
//    @SendTo("/topic/branch-updates")
//    public BranchDTO handleBranchUpdate(@RequestBody BranchDTO branchDTO) {
//        return branchDTO;
//    }

    public void sendBranchUpdate(Object WCBranchUpdate) {
        messagingTemplate.convertAndSend("/topic/branch-updates", WCBranchUpdate);
    }
}
