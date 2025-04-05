package com.UoR_MTS_Backend.mail_tracking_system.testServices;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.EmailVerificationResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.IMPL.EmailVerificationServiceIMPL;
import com.UoR_MTS_Backend.mail_tracking_system.utils.email.EmailBody;
import com.UoR_MTS_Backend.mail_tracking_system.utils.email.OTPgenerator;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailVerificationServiceIMPLTest {

    @Mock
    private OTPgenerator otpGenerator;

    @Mock
    private EmailBody emailBody;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailVerificationServiceIMPL emailVerificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifyEmail_Success() throws MessagingException {
        // Arrange
        String email = "test@example.com";
        String fakeOtp = "123456";
        String renderedHtml = "<html>Your OTP is 123456</html>";

        when(otpGenerator.OTP()).thenReturn(fakeOtp);
        when(templateEngine.process(eq("otpEmailTemplate"), any(Context.class)))
                .thenReturn(renderedHtml);

        // Act
        EmailVerificationResponseDTO response = emailVerificationService.verifyEmail(email);

        // Assert
        assertNotNull(response);
        assertEquals(email, response.getEmail());
        assertEquals(fakeOtp, response.getOtp());

        // Verify methods
        verify(otpGenerator, times(1)).OTP();
        verify(templateEngine, times(1)).process(eq("otpEmailTemplate"), any(Context.class));
        verify(emailBody, times(1)).sendEmailWithHtmlContent(
                eq("Confidential: OTP for Email Verification"),
                eq(email),
                eq(renderedHtml)
        );
    }

    @Test
    void testVerifyEmail_ThrowsException() throws MessagingException {
        // Arrange
        String email = "test@example.com";
        String fakeOtp = "123456";

        when(otpGenerator.OTP()).thenReturn(fakeOtp);
        when(templateEngine.process(eq("otpEmailTemplate"), any(Context.class)))
                .thenReturn("someHtml");

        doThrow(new RuntimeException("SMTP error"))
                .when(emailBody)
                .sendEmailWithHtmlContent(anyString(), anyString(), anyString());

        // Act + Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            emailVerificationService.verifyEmail(email);
        });

        assertTrue(thrown.getMessage().contains("Error sending OTP"));
    }
}
