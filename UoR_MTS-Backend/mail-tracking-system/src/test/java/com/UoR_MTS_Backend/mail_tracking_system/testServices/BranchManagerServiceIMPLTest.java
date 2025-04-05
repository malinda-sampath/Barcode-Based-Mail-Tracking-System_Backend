package com.UoR_MTS_Backend.mail_tracking_system.testServices;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.BranchUserRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.BranchUserResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Role;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.IMPL.BranchManagerServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BranchManagerServiceIMPLTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private BranchManagerServiceIMPL branchManagerService;

    private User user;
    private BranchUserRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("test-id");
        user.setPassword("oldPass");
        user.setRole(new Role("1", RoleEnum.BRANCH_MANAGER));

        requestDTO = new BranchUserRequestDTO();
        requestDTO.setPassword("newPassword");
    }

    @Test
    void testBranchManagerUpdate_Success() {
        when(userRepo.findById("test-id")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
        doAnswer(invocation -> {
            User updatedUser = invocation.getArgument(0);
            assertEquals("encodedPassword", updatedUser.getPassword());
            return null;
        }).when(userRepo).save(any(User.class));

        String result = branchManagerService.branchManagerUpdate("test-id", requestDTO);
        assertEquals("Branch Manager updated successfully.", result);
    }

    @Test
    void testBranchManagerUpdate_UserNotFound() {
        when(userRepo.findById("invalid-id")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> branchManagerService.branchManagerUpdate("invalid-id", requestDTO));
    }

    @Test
    void testBranchManagerDelete_Success() {
        when(userRepo.existsById("test-id")).thenReturn(true);
        doNothing().when(userRepo).deleteById("test-id");

        String result = branchManagerService.branchManagerDelete("test-id");
        assertEquals("Branch Manager Deleted Successfully", result);
    }

    @Test
    void testBranchManagerDelete_UserNotFound() {
        when(userRepo.existsById("invalid-id")).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> branchManagerService.branchManagerDelete("invalid-id"));
    }

    @Test
    void testGetAllBranchManagers_Success() {
        List<User> userList = Collections.singletonList(user);
        List<BranchUserResponseDTO> responseDTOList = Collections.singletonList(new BranchUserResponseDTO());

        when(userRepo.findAllByRole_Name(RoleEnum.BRANCH_MANAGER)).thenReturn(userList);
        when(modelMapper.map(userList, new TypeToken<List<BranchUserResponseDTO>>() {}.getType())).thenReturn(responseDTOList);

        List<BranchUserResponseDTO> result = branchManagerService.getAllBranchManagers();
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllBranchManagers_EmptyList() {
        when(userRepo.findAllByRole_Name(RoleEnum.BRANCH_MANAGER)).thenReturn(Collections.emptyList());
        assertThrows(UserNotFoundException.class, () -> branchManagerService.getAllBranchManagers());
    }

    @Test
    void testGetBranchManagerById_Success() {
        when(userRepo.findById("test-id")).thenReturn(Optional.of(user));
        User result = branchManagerService.getBranchManagerById("test-id");
        assertEquals("test-id", result.getId());
    }

    @Test
    void testGetBranchManagerById_UserNotFound() {
        when(userRepo.findById("invalid-id")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> branchManagerService.getBranchManagerById("invalid-id"));
    }
}
