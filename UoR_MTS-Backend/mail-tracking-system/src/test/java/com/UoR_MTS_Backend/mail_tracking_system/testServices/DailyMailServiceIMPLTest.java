package com.UoR_MTS_Backend.mail_tracking_system.testServices;

import com.UoR_MTS_Backend.mail_tracking_system.configs.ModelMapperConfig;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.DailyMailDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.dailymail.RequestDailyMailViewAllDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailActivity;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.DailyMailRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailActivityRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.specification.DailyMailSpecification;
import com.UoR_MTS_Backend.mail_tracking_system.services.IMPL.DailyMailServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DailyMailServiceIMPLTest {

    @Mock
    private DailyMailRepo dailyMailRepo;

    @Mock
    private MailActivityRepo mailActivityRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapperConfig modelMapperConfig;

    @Mock
    private Authentication authentication;

    @Mock
    private DailyMailSpecification dailyMailSpecification;

    @InjectMocks
    private DailyMailServiceIMPL dailyMailServiceIMPL;

    private DailyMailDTO dailyMailDTO;
    private DailyMail dailyMail;
    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Create a sample DailyMailDTO
        dailyMailDTO = new DailyMailDTO();
        dailyMailDTO.setSenderName("Sender");
        dailyMailDTO.setReceiverName("Receiver");

        // Create a sample DailyMail entity
        dailyMail = new DailyMail();
        dailyMail.setId(1);
        dailyMail.setSenderName("Sender");
        dailyMail.setReceiverName("Receiver");

        // Create a sample User
        user = new User();
        user.setEmail("test@example.com");

        // Mock behavior for modelMapper
        when(modelMapperConfig.modelMapper().map(dailyMailDTO, DailyMail.class)).thenReturn(dailyMail);
    }

    @Test
    public void testAddDailyMail_success() {
        // Mock dependencies
        when(dailyMailRepo.save(any(DailyMail.class))).thenReturn(dailyMail);
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Call the method
        String result = dailyMailServiceIMPL.addDailyMail(dailyMailDTO, new byte[0], "uniqueID", authentication);

        // Verify result and interactions
        assertNotNull(result);
        assertTrue(result.contains("saved with barcode image"));
        verify(dailyMailRepo, times(1)).save(any(DailyMail.class));
        verify(mailActivityRepo, times(1)).save(any(MailActivity.class));
    }

    @Test
    public void testAddDailyMail_invalidDTO() {
        dailyMailDTO.setSenderName(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dailyMailServiceIMPL.addDailyMail(dailyMailDTO, new byte[0], "uniqueID", authentication);
        });

        assertEquals("Sender and Receiver names cannot be null.", exception.getMessage());
    }

    @Test
    public void testUpdateDailyMail_success() {
        when(dailyMailRepo.existsById(anyInt())).thenReturn(true);
        when(dailyMailRepo.getReferenceById(anyInt())).thenReturn(dailyMail);
        when(dailyMailRepo.save(any(DailyMail.class))).thenReturn(dailyMail);

        String result = dailyMailServiceIMPL.updateDailyMail(1, dailyMailDTO, new byte[0], "uniqueID");

        assertTrue(result.contains("updated successfully"));
        verify(dailyMailRepo, times(1)).save(any(DailyMail.class));
        verify(mailActivityRepo, times(1)).save(any(MailActivity.class));
    }

    @Test
    public void testUpdateDailyMail_notFound() {
        when(dailyMailRepo.existsById(anyInt())).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            dailyMailServiceIMPL.updateDailyMail(1, dailyMailDTO, new byte[0], "uniqueID");
        });

        assertEquals("No data found for that ID", exception.getMessage());
    }

    @Test
    public void testDeleteDailyMail_success() {
        when(dailyMailRepo.existsById(anyInt())).thenReturn(true);
        when(dailyMailRepo.findById(anyInt())).thenReturn(Optional.of(dailyMail));

        String result = dailyMailServiceIMPL.deleteDailyMail(1);

        assertTrue(result.contains("deleted successfully"));
        verify(dailyMailRepo, times(1)).deleteById(anyInt());
        verify(mailActivityRepo, times(1)).save(any(MailActivity.class));
    }

    @Test
    public void testDeleteDailyMail_notFound() {
        when(dailyMailRepo.existsById(anyInt())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dailyMailServiceIMPL.deleteDailyMail(1);
        });

        assertEquals("No data found for that id", exception.getMessage());
    }

    @Test
    public void testGetAllDailyMails_success() {
        List<DailyMail> dailyMails = Arrays.asList(dailyMail, dailyMail);
        when(dailyMailRepo.findAll()).thenReturn(dailyMails);

        List<RequestDailyMailViewAllDTO> result = dailyMailServiceIMPL.getAllDailyMails();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllDailyMails_empty() {
        when(dailyMailRepo.findAll()).thenReturn(Arrays.asList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            dailyMailServiceIMPL.getAllDailyMails();
        });

        assertEquals("No daily mails found in the database.", exception.getMessage());
    }

    @Test
    public void testFilterDailyMail_success() {
        List<DailyMail> dailyMails = Arrays.asList(dailyMail);
        when(dailyMailRepo.findAll((Example<DailyMail>) any())).thenReturn(dailyMails);

        List<RequestDailyMailViewAllDTO> result = dailyMailServiceIMPL.filterDailyMail("Sender", "Receiver", "Type", "Tracking", "Branch");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testFilterDailyMail_empty() {
        when(dailyMailRepo.findAll((Example<DailyMail>) any())).thenReturn(Arrays.asList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            dailyMailServiceIMPL.filterDailyMail("Sender", "Receiver", "Type", "Tracking", "Branch");
        });

        assertEquals("No mail activities found with the provided filters.", exception.getMessage());
    }
}
