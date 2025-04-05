package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.BranchController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.RequestBranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchService;
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

class BranchControllerTest {

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBranch() {
        RequestBranchDTO request = new RequestBranchDTO(); // add fields as needed
        when(branchService.branchSave(request)).thenReturn("Branch saved successfully");

        ResponseEntity<StandardResponse<String>> response = branchController.branchSave(request);

        assertEquals("Branch saved successfully", response.getBody().getData());
        verify(branchService, times(1)).branchSave(request);
    }

    @Test
    void testGetAllBranches() {
        BranchDTO b1 = new BranchDTO(); // add fields
        BranchDTO b2 = new BranchDTO(); // add fields
        List<BranchDTO> branches = Arrays.asList(b1, b2);

        when(branchService.getAllBranches()).thenReturn(branches);

        ResponseEntity<StandardResponse<List<BranchDTO>>> response = branchController.getAllBranches();

        assertEquals(2, response.getBody().getData().size());
        verify(branchService, times(1)).getAllBranches();
    }

    @Test
    void testGetBranchById() {
        String branchCode = "B001";
        BranchDTO branch = new BranchDTO(); // add fields
        when(branchService.getBranchById(branchCode)).thenReturn(branch);

        ResponseEntity<StandardResponse<BranchDTO>> response = branchController.getBranchById(branchCode);

        assertNotNull(response.getBody().getData());
        verify(branchService, times(1)).getBranchById(branchCode);
    }

    @Test
    void testUpdateBranch() {
        String id = "B001";
        RequestBranchDTO request = new RequestBranchDTO(); // add fields
        when(branchService.updateBranchById(id, request)).thenReturn("Branch updated successfully");

        ResponseEntity<StandardResponse<String>> response = branchController.branchUpdate(id, request);

        assertEquals("Branch updated successfully", response.getBody().getData());
        verify(branchService, times(1)).updateBranchById(id, request);
    }

    @Test
    void testDeleteBranch() {
        String branchCode = "B001";
        when(branchService.deleteBranchByBranchCode(branchCode)).thenReturn("Branch deleted successfully");

        ResponseEntity<StandardResponse<String>> response = branchController.deleteBranch(branchCode);

        assertEquals("Branch deleted successfully", response.getBody().getData());
        verify(branchService, times(1)).deleteBranchByBranchCode(branchCode);
    }
}
