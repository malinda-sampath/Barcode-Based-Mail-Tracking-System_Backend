package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.MailActivityController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailActivityService;
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

class MailActivityControllerTest {

    @Mock
    private MailActivityService mailActivityService;

    @InjectMocks
    private MailActivityController mailActivityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMailActivity() {
        List<MailActivityDTO> mockList = Arrays.asList(new MailActivityDTO(), new MailActivityDTO());

        when(mailActivityService.getAllMailActivity()).thenReturn(mockList);

        ResponseEntity<StandardResponse<List<MailActivityDTO>>> response = mailActivityController.getAllMailActivity();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(mailActivityService, times(1)).getAllMailActivity();
    }

    @Test
    void testGetMailActivityByBarcodeId() {
        String barcodeId = "ABC123";
        List<MailActivityDTO> mockList = Arrays.asList(new MailActivityDTO());

        when(mailActivityService.getAllMailActivityByBarcodeId(barcodeId)).thenReturn(mockList);

        ResponseEntity<StandardResponse<List<MailActivityDTO>>> response =
                mailActivityController.getMailActivityByBarcodeId(barcodeId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getData().size());
        verify(mailActivityService, times(1)).getAllMailActivityByBarcodeId(barcodeId);
    }

    @Test
    void testGetMailActivityByFilter() {
        List<MailActivityDTO> mockList = Arrays.asList(new MailActivityDTO(), new MailActivityDTO());

        when(mailActivityService.filterMailActivities("user", "type", "branch", "sender", "receiver"))
                .thenReturn(mockList);

        ResponseEntity<StandardResponse<List<MailActivityDTO>>> response =
                mailActivityController.getMailActivityByFilter("user", "type", "branch", "sender", "receiver");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(mailActivityService, times(1))
                .filterMailActivities("user", "type", "branch", "sender", "receiver");
    }
}
