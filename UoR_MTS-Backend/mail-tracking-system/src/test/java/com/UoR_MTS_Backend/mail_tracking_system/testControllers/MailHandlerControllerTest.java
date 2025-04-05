package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.MailHandlerController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailHandlerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailHandlerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailHandlerService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailHandlerControllerTest {

    @Mock
    private MailHandlerService mailHandlerService;

    @InjectMocks
    private MailHandlerController mailHandlerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterMailHandler() {
        MailHandlerRequestDTO request = new MailHandlerRequestDTO(); // You can set fields if needed
        when(mailHandlerService.saveMailHandler(request)).thenReturn("Mail Handler saved successfully");

        ResponseEntity<StandardResponse<String>> response = mailHandlerController.registerMailHandler(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mail Handler saved successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(mailHandlerService, times(1)).saveMailHandler(request);
    }

    @Test
    void testMailHandlerUpdate() {
        long id = 1L;
        MailHandlerRequestDTO request = new MailHandlerRequestDTO(); // Add test data if needed
        when(mailHandlerService.updateMailHandler(id, request)).thenReturn("Mail Handler updated successfully");

        ResponseEntity<StandardResponse<String>> response = mailHandlerController.mailHandlerUpdate(id, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mail Handler updated successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(mailHandlerService, times(1)).updateMailHandler(id, request);
    }

    @Test
    void testMailHandlerDelete() {
        long id = 1L;
        when(mailHandlerService.deleteMailHandler(id)).thenReturn("Mail Handler deleted successfully");

        ResponseEntity<StandardResponse<String>> response = mailHandlerController.mailHandlerDelete(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mail Handler deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(mailHandlerService, times(1)).deleteMailHandler(id);
    }

    @Test
    void testAllMailHandlersGet() {
        List<MailHandlerResponseDTO> handlerList = Arrays.asList(new MailHandlerResponseDTO(), new MailHandlerResponseDTO());
        when(mailHandlerService.getAllMailHandlers()).thenReturn(handlerList);

        ResponseEntity<StandardResponse<List<MailHandlerResponseDTO>>> response = mailHandlerController.allMailHandlersGet();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mail Handlers retrieved successfully.", response.getBody().getMessage());
        assertEquals(2, response.getBody().getData().size());

        verify(mailHandlerService, times(1)).getAllMailHandlers();
    }
}
