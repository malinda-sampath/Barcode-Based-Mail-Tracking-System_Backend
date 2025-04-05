package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.EmailVerificationController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.EmailVerificationResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.EmailVerificationService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailVerificationControllerTest {

    @Mock
    private EmailVerificationService emailVerificationService;

    @InjectMocks
    private EmailVerificationController emailVerificationController;

    public EmailVerificationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifyEmailSuccess() {
        String email = "test@example.com";
        EmailVerificationResponseDTO mockResponse = new EmailVerificationResponseDTO("123456", email);

        when(emailVerificationService.verifyEmail(email)).thenReturn(mockResponse);

        ResponseEntity<StandardResponse<EmailVerificationResponseDTO>> response =
                emailVerificationController.verifyEmail(email);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("OTP Send Successfully!", response.getBody().getMessage());
        assertEquals("123456", response.getBody().getData().getOtp());
        assertEquals(email, response.getBody().getData().getEmail());

        verify(emailVerificationService, times(1)).verifyEmail(email);
    }

    @Test
    void testVerifyEmailFailure() {
        String email = "error@example.com";

        when(emailVerificationService.verifyEmail(email)).thenThrow(new RuntimeException("Failed to send OTP"));

        ResponseEntity<StandardResponse<EmailVerificationResponseDTO>> response =
                emailVerificationController.verifyEmail(email);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error Occur When OTP Send", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(emailVerificationService, times(1)).verifyEmail(email);
    }
}
