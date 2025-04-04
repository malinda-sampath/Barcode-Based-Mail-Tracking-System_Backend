package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.BranchManagerController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.BranchUserRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.BranchUserResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchManagerService;
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

class BranchManagerControllerTest {

    @Mock
    private BranchManagerService branchManagerService;

    @InjectMocks
    private BranchManagerController branchManagerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBranchManagerUpdate() {
        String id = "123";
        BranchUserRequestDTO requestDTO = new BranchUserRequestDTO(); // populate fields if needed
        when(branchManagerService.branchManagerUpdate(id, requestDTO)).thenReturn("Updated successfully");

        ResponseEntity<StandardResponse<String>> response = branchManagerController.branchManagerUpdate(id, requestDTO);

        assertEquals("Updated successfully", response.getBody().getData());
        verify(branchManagerService, times(1)).branchManagerUpdate(id, requestDTO);
    }

    @Test
    void testBranchManagerDelete() {
        String id = "123";
        when(branchManagerService.branchManagerDelete(id)).thenReturn("Deleted successfully");

        ResponseEntity<StandardResponse<String>> response = branchManagerController.branchManagerDelete(id);

        assertEquals("Deleted successfully", response.getBody().getData());
        verify(branchManagerService, times(1)).branchManagerDelete(id);
    }

    @Test
    void testGetAllBranchManagers() {
        BranchUserResponseDTO manager1 = new BranchUserResponseDTO(); // add values if needed
        BranchUserResponseDTO manager2 = new BranchUserResponseDTO();
        List<BranchUserResponseDTO> managers = Arrays.asList(manager1, manager2);

        when(branchManagerService.getAllBranchManagers()).thenReturn(managers);

        ResponseEntity<StandardResponse<List<BranchUserResponseDTO>>> response = branchManagerController.getAllBranchManagers();

        assertEquals(2, response.getBody().getData().size());
        assertEquals("Branch managers retrieved successfully.", response.getBody().getMessage());
        verify(branchManagerService, times(1)).getAllBranchManagers();
    }

    @Test
    void testGetBranchManagerById() {
        String id = "123";
        User user = new User(); // add fields if needed
        when(branchManagerService.getBranchManagerById(id)).thenReturn(user);

        ResponseEntity<StandardResponse<User>> response = branchManagerController.getBranchManagerById(id);

        assertNotNull(response.getBody().getData());
        assertEquals("Branch manager retrieved successfully.", response.getBody().getMessage());
        verify(branchManagerService, times(1)).getBranchManagerById(id);
    }
}
