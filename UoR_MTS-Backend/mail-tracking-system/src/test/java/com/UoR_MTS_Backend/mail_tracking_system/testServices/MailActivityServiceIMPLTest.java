package com.UoR_MTS_Backend.mail_tracking_system.testServices;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.MailActivityDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.MailActivity;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailActivityNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.MailActivityRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.IMPL.MailActivityServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MailActivityServiceIMPLTest {

    private MailActivityRepo mailActivityRepo;
    private ModelMapper modelMapper;
    private MailActivityServiceIMPL mailActivityService;

    @BeforeEach
    void setUp() {
        mailActivityRepo = mock(MailActivityRepo.class);
        modelMapper = new ModelMapper();
        mailActivityService = new MailActivityServiceIMPL(mailActivityRepo, modelMapper);
    }

    @Test
    void testGetAllMailActivity_success() {
        MailActivity activity = new MailActivity();
        activity.setId(1);

        when(mailActivityRepo.findAll()).thenReturn(List.of(activity));

        List<MailActivityDTO> result = mailActivityService.getAllMailActivity();

        assertEquals(1, result.size());
        verify(mailActivityRepo, times(1)).findAll();
    }

    @Test
    void testGetAllMailActivity_emptyList() {
        when(mailActivityRepo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> mailActivityService.getAllMailActivity());
    }

    @Test
    void testGetAllMailActivityByBarcodeId_success() {
        MailActivity activity = new MailActivity();
        activity.setId(1);
        activity.setBarcodeId("ABC123");

        when(mailActivityRepo.findAllByBarcodeIdEquals("ABC123")).thenReturn(List.of(activity));

        List<MailActivityDTO> result = mailActivityService.getAllMailActivityByBarcodeId("ABC123");

        assertEquals(1, result.size());
        assertEquals("ABC123", activity.getBarcodeId());
        verify(mailActivityRepo, times(1)).findAllByBarcodeIdEquals("ABC123");
    }

    @Test
    void testGetAllMailActivityByBarcodeId_empty() {
        when(mailActivityRepo.findAllByBarcodeIdEquals("ABC123")).thenReturn(Collections.emptyList());

        assertThrows(MailActivityNotFoundException.class,
                () -> mailActivityService.getAllMailActivityByBarcodeId("ABC123"));
    }

    @Test
    void testFilterMailActivities_success() {
        MailActivity activity1 = new MailActivity();
        activity1.setId(1);
        activity1.setActivityType("SEND");
        activity1.setBranchCode("BR01");
        activity1.setSenderName("Alice");
        activity1.setReceiverName("Bob");
        activity1.setUser(mockUser("john"));

        MailActivity activity2 = new MailActivity(); // Not matching filter

        when(mailActivityRepo.findAll()).thenReturn(Arrays.asList(activity1, activity2));

        List<MailActivityDTO> result = mailActivityService.filterMailActivities("john", "SEND", "BR01", "Alice", "Bob");

        assertEquals(1, result.size());
        verify(mailActivityRepo, times(1)).findAll();
    }

    @Test
    void testFilterMailActivities_noMatch() {
        MailActivity activity = new MailActivity();
        activity.setUser(mockUser("john"));
        activity.setActivityType("SEND");
        activity.setBranchCode("BR01");
        activity.setSenderName("Alice");
        activity.setReceiverName("Bob");

        when(mailActivityRepo.findAll()).thenReturn(List.of(activity));

        assertThrows(MailActivityNotFoundException.class,
                () -> mailActivityService.filterMailActivities("wrongUser", null, null, null, null));
    }

    private User mockUser(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }
}
