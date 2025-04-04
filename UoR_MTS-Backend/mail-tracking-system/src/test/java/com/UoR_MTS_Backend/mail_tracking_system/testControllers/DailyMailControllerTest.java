package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.DailyMailController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.DailyMailService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DailyMailControllerTest {

    @Mock
    private DailyMailService dailyMailService;

    @InjectMocks
    private DailyMailController dailyMailController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testAddDailyMail() throws WriterException, IOException {
        DailyMailDTO dto = new DailyMailDTO();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(dailyMailService.addDailyMail(any(), any(), any(), any())).thenReturn("Mail added");

        ResponseEntity<StandardResponse<String>> response = dailyMailController.addDailyMail(dto);

        assertEquals("Mail added", response.getBody().getData());
        verify(dailyMailService, times(1)).addDailyMail(any(), any(), any(), any());
    }

    @Test
    void testUpdateDailyMail() throws WriterException, IOException {
        DailyMailDTO dto = new DailyMailDTO();
        when(dailyMailService.updateDailyMail(eq(1), any(), any(), any())).thenReturn("Updated");

        ResponseEntity<StandardResponse<String>> response = dailyMailController.updateDailyMail(1, dto);

        assertEquals("Updated", response.getBody().getData());
        verify(dailyMailService).updateDailyMail(eq(1), any(), any(), any());
    }

    @Test
    void testDeleteDailyMail_Success() {
        when(dailyMailService.deleteDailyMail(1)).thenReturn("Deleted");

        ResponseEntity<StandardResponse<String>> response = dailyMailController.deleteDailyMail(1);

        assertEquals("Deleted", response.getBody().getData());
        verify(dailyMailService).deleteDailyMail(1);
    }

    @Test
    void testDeleteDailyMail_NotFound() {
        when(dailyMailService.deleteDailyMail(1)).thenReturn(null);

        ResponseEntity<StandardResponse<String>> response = dailyMailController.deleteDailyMail(1);

        assertEquals("Daily mail with ID 1 not found.", response.getBody().getMessage());
        verify(dailyMailService).deleteDailyMail(1);
    }

    @Test
    void testGetAllDailyMails_Found() {
        List<RequestDailyMailViewAllDTO> list = Arrays.asList(new RequestDailyMailViewAllDTO(), new RequestDailyMailViewAllDTO());
        when(dailyMailService.getAllDailyMails()).thenReturn(list);

        ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> response = dailyMailController.getAllDailyMails();

        assertEquals(2, response.getBody().getData().size());
        assertEquals("Daily mails retrieved successfully.", response.getBody().getMessage());
    }

    @Test
    void testGetAllDailyMails_NotFound() {
        when(dailyMailService.getAllDailyMails()).thenReturn(Collections.emptyList());

        ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> response = dailyMailController.getAllDailyMails();

        assertEquals("No daily mails found.", response.getBody().getMessage());
    }

    @Test
    void testGetAllDailyMailsByBarcodeId_Found() {
        String barcodeId = "BAR123";
        List<RequestDailyMailViewAllDTO> list = Arrays.asList(new RequestDailyMailViewAllDTO());
        when(dailyMailService.getAllDailyMailsByBarcodeId(barcodeId)).thenReturn(list);

        ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> response = dailyMailController.getAllDailyMailsByBarcodeId(barcodeId);

        assertEquals("Daily mails retrieved successfully by barcode ID: BAR123", response.getBody().getMessage());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllDailyMailsByBarcodeId_NotFound() {
        String barcodeId = "NOT_FOUND";
        when(dailyMailService.getAllDailyMailsByBarcodeId(barcodeId)).thenReturn(Collections.emptyList());

        ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> response = dailyMailController.getAllDailyMailsByBarcodeId(barcodeId);

        assertEquals("No daily mails found for the given barcode ID: NOT_FOUND", response.getBody().getMessage());
    }

    @Test
    void testFilterDailyMail() {
        when(dailyMailService.filterDailyMail(any(), any(), any(), any(), any()))
                .thenReturn(Arrays.asList(new RequestDailyMailViewAllDTO(), new RequestDailyMailViewAllDTO()));

        ResponseEntity<StandardResponse<List<RequestDailyMailViewAllDTO>>> response = dailyMailController.getMailActivityByFilter(
                "sender", "receiver", "type", "track123", "branch1"
        );

        assertEquals("Mail activities retrieved successfully", response.getBody().getMessage());
        assertEquals(2, response.getBody().getData().size());
    }
}
