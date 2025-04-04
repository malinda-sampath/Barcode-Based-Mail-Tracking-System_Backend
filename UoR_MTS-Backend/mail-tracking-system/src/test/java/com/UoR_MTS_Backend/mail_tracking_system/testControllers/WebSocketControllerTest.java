package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.WebSocketController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

public class WebSocketControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private WebSocketController webSocketController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendBranchUpdate() {
        // Given
        Object WCBranchUpdate = "Branch update message";

        // When
        webSocketController.sendBranchUpdate(WCBranchUpdate);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend("/topic/branch-updates", WCBranchUpdate);
    }

    @Test
    public void testSendMailHandlerUpdate() {
        // Given
        Object WCMailHandlerUpdate = "Mail handler update message";

        // When
        webSocketController.sendMailHandlerUpdate(WCMailHandlerUpdate);

        // Then
        verify(messagingTemplate, times(1)).convertAndSend("/topic/mail-handler-updates", WCMailHandlerUpdate);
    }
}
