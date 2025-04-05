package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.MailRecordController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailRecordDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailRecord;
import com.UoR_MTS_Backend.mail_tracking_system.exception.NoMailActivitiesFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailRecordService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MailRecordControllerTest {

    @Mock
    private MailRecordService mailRecordService;

    @InjectMocks
    private MailRecordController mailRecordController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testTransferDailyMailsToMainCart() {
        when(mailRecordService.transferDailyMailsToMainCart()).thenReturn("Transfer successful");

        ResponseEntity<StandardResponse<String>> response = mailRecordController.transferDailyMailsToMainCart();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Transfer successful", response.getBody().getMessage());
        verify(mailRecordService, times(1)).transferDailyMailsToMainCart();
    }

    @Test
    void testFilterMailRecordsSuccess() {
        MailRecordDTO dto = new MailRecordDTO();
        Page<MailRecordDTO> page = new PageImpl<>(List.of(dto));

        when(mailRecordService.filterMailRecords(
                any(), any(), any(), any(), any(), any(PageRequest.class)))
                .thenReturn(page);

        ResponseEntity<StandardResponse<Page<MailRecordDTO>>> response =
                mailRecordController.filterMailRecords("sender", "receiver", "type", "track123", "branch", 0);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
        assertEquals("Mail records retrieved successfully.", response.getBody().getMessage());
        verify(mailRecordService, times(1)).filterMailRecords(any(), any(), any(), any(), any(), any());
    }

    @Test
    void testFilterMailRecordsEmptyThrowsException() {
        when(mailRecordService.filterMailRecords(any(), any(), any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        assertThrows(NoMailActivitiesFoundException.class, () -> {
            mailRecordController.filterMailRecords(null, null, null, null, null, 0);
        });

        verify(mailRecordService, times(1)).filterMailRecords(any(), any(), any(), any(), any(), any());
    }

    @Test
    void testSearchMailByBarcodeId() {
        MailRecord mailRecord = new MailRecord();
        when(mailRecordService.searchMailByBarcodeId("abc123")).thenReturn(mailRecord);

        ResponseEntity<StandardResponse<MailRecord>> response =
                mailRecordController.searchMailByBarcodeId("abc123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getData());
        assertEquals("Mail record found successfully.", response.getBody().getMessage());
        verify(mailRecordService, times(1)).searchMailByBarcodeId("abc123");
    }

    @Test
    void testGetAllMailRecordsSuccess() {
        Page<MailRecord> mailRecordPage = new PageImpl<>(List.of(new MailRecord()));
        when(mailRecordService.getAllMailRecords(any())).thenReturn(mailRecordPage);

        ResponseEntity<?> response = mailRecordController.getAllMailRecords(0);

        assertEquals(200, response.getStatusCodeValue());
        verify(mailRecordService, times(1)).getAllMailRecords(any());
    }

    @Test
    void testGetAllMailRecordsEmpty() {
        Page<MailRecord> emptyPage = new PageImpl<>(Collections.emptyList());
        when(mailRecordService.getAllMailRecords(any())).thenReturn(emptyPage);

        ResponseEntity<?> response = mailRecordController.getAllMailRecords(0);

        assertEquals(404, response.getStatusCodeValue());
        verify(mailRecordService, times(1)).getAllMailRecords(any());
    }
}
