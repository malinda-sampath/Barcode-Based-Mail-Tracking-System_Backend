package com.UoR_MTS_Backend.mail_tracking_system.testServices;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.WebSocketController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.RequestBranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.websocketResponse.WCBranchUpdateDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.exception.AlreadyExistsException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.BranchNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.IMPL.BranchServiceIMPL;
import com.UoR_MTS_Backend.mail_tracking_system.utils.tableID.IDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.*;

import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BranchServiceIMPLTest {

    @Mock
    private BranchRepo branchRepo;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private WebSocketController webSocketController;

    @Mock
    private IDGenerator idGenerator;

    @InjectMocks
    private BranchServiceIMPL branchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Save branch - success
    @Test
    void testBranchSave_Success() {
        RequestBranchDTO dto = new RequestBranchDTO();
        dto.setBranchName("NewBranch");

        Branch branch = new Branch();
        Branch savedBranch = new Branch();
        BranchDTO branchDTO = new BranchDTO();

        when(branchRepo.findAllByBranchName("NewBranch")).thenReturn(Optional.empty());
        when(idGenerator.generateID(any(), anyInt())).thenReturn("BR001");
        when(branchRepo.findByBranchCode("BR001")).thenReturn(null);
        when(modelMapper.map(dto, Branch.class)).thenReturn(branch);
        when(branchRepo.save(any(Branch.class))).thenReturn(savedBranch);
        when(modelMapper.map(savedBranch, BranchDTO.class)).thenReturn(branchDTO);

        String result = branchService.branchSave(dto);

        verify(jdbcTemplate).execute(anyString());
        verify(webSocketController).sendBranchUpdate(any(WCBranchUpdateDTO.class));
        assertEquals("Branch saved successfully.", result);
    }

    // Save branch - already exists
    @Test
    void testBranchSave_AlreadyExists() {
        RequestBranchDTO dto = new RequestBranchDTO();
        dto.setBranchName("ExistingBranch");

        when(branchRepo.findAllByBranchName("ExistingBranch")).thenReturn(Optional.of(new Branch()));

        assertThrows(AlreadyExistsException.class, () -> branchService.branchSave(dto));
    }

    // Get all branches
    @Test
    void testGetAllBranches_Success() {
        Branch branch = new Branch();
        branch.setBranchCode("BR001");
        List<Branch> branchList = Collections.singletonList(branch);
        BranchDTO branchDTO = new BranchDTO();

        when(branchRepo.findAll()).thenReturn(branchList);
        when(modelMapper.map(branch, BranchDTO.class)).thenReturn(branchDTO);

        List<BranchDTO> result = branchService.getAllBranches();
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllBranches_Empty() {
        when(branchRepo.findAll()).thenReturn(Collections.emptyList());
        assertThrows(BranchNotFoundException.class, () -> branchService.getAllBranches());
    }

    // Get branch by ID
    @Test
    void testGetBranchById_Success() {
        Branch branch = new Branch();
        branch.setId("123");
        BranchDTO branchDTO = new BranchDTO();

        when(branchRepo.findById("123")).thenReturn(Optional.of(branch));
        when(modelMapper.map(branch, BranchDTO.class)).thenReturn(branchDTO);

        BranchDTO result = branchService.getBranchById("123");
        assertNotNull(result);
    }

    @Test
    void testGetBranchById_NotFound() {
        when(branchRepo.findById("999")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> branchService.getBranchById("999"));
    }

    // Update branch by ID
    @Test
    void testUpdateBranchById_Success() {
        String id = "123";
        Branch branch = new Branch();
        branch.setId(id);
        branch.setBranchName("OldBranch");

        RequestBranchDTO dto = new RequestBranchDTO();
        dto.setBranchName("NewBranch");
        dto.setBranchDescription("Updated");

        when(branchRepo.findById(id)).thenReturn(Optional.of(branch));
        when(branchRepo.save(any(Branch.class))).thenReturn(branch);
        when(modelMapper.map(branch, BranchDTO.class)).thenReturn(new BranchDTO());

        String result = branchService.updateBranchById(id, dto);

        verify(jdbcTemplate).execute(anyString());
        verify(webSocketController).sendBranchUpdate(any(BranchDTO.class));
        assertTrue(result.contains("updated successfully"));
    }

    @Test
    void testUpdateBranchById_NotFound() {
        RequestBranchDTO dto = new RequestBranchDTO();
        when(branchRepo.findById("999")).thenReturn(Optional.empty());
        assertThrows(BranchNotFoundException.class, () -> branchService.updateBranchById("999", dto));
    }

    @Test
    void testUpdateBranchById_DuplicateName() {
        String id = "123";
        Branch branch = new Branch();
        branch.setId(id);
        branch.setBranchName("SameName");

        RequestBranchDTO dto = new RequestBranchDTO();
        dto.setBranchName("SameName");

        when(branchRepo.findById(id)).thenReturn(Optional.of(branch));

        assertThrows(AlreadyExistsException.class, () -> branchService.updateBranchById(id, dto));
    }

    // Delete branch
    @Test
    void testDeleteBranch_Success() {
        Branch branch = new Branch();
        branch.setBranchCode("BR001");

        when(branchRepo.findByBranchCode("BR001")).thenReturn(branch);
        when(modelMapper.map(branch, BranchDTO.class)).thenReturn(new BranchDTO());

        String result = branchService.deleteBranchByBranchCode("BR001");

        verify(branchRepo).deleteBranchByBranchCode("BR001");
        verify(webSocketController).sendBranchUpdate(any(WCBranchUpdateDTO.class));
        assertEquals("Branch deleted successfully.", result);
    }

    @Test
    void testDeleteBranch_NotFound() {
        when(branchRepo.findByBranchCode("BR999")).thenReturn(null);
        assertThrows(BranchNotFoundException.class, () -> branchService.deleteBranchByBranchCode("BR999"));
    }
}
