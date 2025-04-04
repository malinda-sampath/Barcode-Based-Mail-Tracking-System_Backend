package com.UoR_MTS_Backend.mail_tracking_system.branchService;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.WebSocketController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.RequestBranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.websocketResponse.WCBranchUpdateDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.exception.AlreadyExistsException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.BranchService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.tableID.IDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @InjectMocks
    private BranchService branchService;

    @Mock
    private BranchRepo branchRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private IDGenerator idGenerator;

    @Mock
    private WebSocketController webSocketController;

    // Mocked DTOs and entities
    private RequestBranchDTO requestBranchDTO;
    private Branch branchEntity;
    private Branch savedBranch;
    private BranchDTO branchDTO;

    @BeforeEach
    void setUp() {
        requestBranchDTO = new RequestBranchDTO();
        requestBranchDTO.setBranchName("Test Branch");

        branchEntity = new Branch();
        branchEntity.setBranchName("Test Branch");

        savedBranch = new Branch();
        savedBranch.setBranchName("Test Branch");
        savedBranch.setBranchCode("BR1234");
        savedBranch.setInsertDate(LocalDateTime.now());

        branchDTO = new BranchDTO();
        branchDTO.setBranchName("Test Branch");
        branchDTO.setBranchCode("BR1234");
    }

    @Test
    void testBranchSave_Success() {
        when(branchRepo.findAllByBranchName("Test Branch")).thenReturn(Optional.empty());
        when(idGenerator.generateID("BR", 4)).thenReturn("BR1234");
        when(branchRepo.findByBranchCode("BR1234")).thenReturn(null);
        when(modelMapper.map(requestBranchDTO, Branch.class)).thenReturn(branchEntity);
        when(branchRepo.save(branchEntity)).thenReturn(savedBranch);
        when(modelMapper.map(savedBranch, BranchDTO.class)).thenReturn(branchDTO);

        String result = branchService.branchSave(requestBranchDTO);

        assertEquals("Branch saved successfully.", result);
        verify(branchRepo).save(branchEntity);
        verify(webSocketController).sendBranchUpdate(any(WCBranchUpdateDTO.class));
    }

    @Test
    void testBranchSave_BranchNameAlreadyExists() {
        Branch existingBranch = new Branch();
        existingBranch.setBranchName("Test Branch");

        when(branchRepo.findAllByBranchName("Test Branch")).thenReturn(Optional.of(existingBranch));

        AlreadyExistsException exception = assertThrows(
                AlreadyExistsException.class,
                () -> branchService.branchSave(requestBranchDTO)
        );

        assertEquals("Branch name 'Test Branch' already exists.", exception.getMessage());
        verify(branchRepo, never()).save(any());
    }

    @Test
    void testBranchSave_IDCollisionRetry() {
        when(branchRepo.findAllByBranchName("Test Branch")).thenReturn(Optional.empty());

        // First code is a duplicate, second one is unique
        when(idGenerator.generateID("BR", 4))
                .thenReturn("BR1234")
                .thenReturn("BR5678");

        when(branchRepo.findByBranchCode("BR1234")).thenReturn(new Branch()); // Collision
        when(branchRepo.findByBranchCode("BR5678")).thenReturn(null);         // No collision

        branchEntity.setBranchCode("BR5678");
        when(modelMapper.map(requestBranchDTO, Branch.class)).thenReturn(branchEntity);
        when(branchRepo.save(branchEntity)).thenReturn(savedBranch);
        when(modelMapper.map(savedBranch, BranchDTO.class)).thenReturn(branchDTO);

        String result = branchService.branchSave(requestBranchDTO);

        assertEquals("Branch saved successfully.", result);
        verify(idGenerator, times(2)).generateID("BR", 4);
        verify(branchRepo).save(branchEntity);
    }

    @Test
    void testBranchSave_SaveFails_ThrowsRuntimeException() {
        when(branchRepo.findAllByBranchName("Test Branch")).thenReturn(Optional.empty());
        when(idGenerator.generateID("BR", 4)).thenReturn("BR1234");
        when(branchRepo.findByBranchCode("BR1234")).thenReturn(null);
        when(modelMapper.map(requestBranchDTO, Branch.class)).thenReturn(branchEntity);
        when(branchRepo.save(branchEntity)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                branchService.branchSave(requestBranchDTO)
        );

        assertTrue(exception.getMessage().contains("Error while processing the branch"));
        assertTrue(exception.getMessage().contains("Database error"));
    }
}
