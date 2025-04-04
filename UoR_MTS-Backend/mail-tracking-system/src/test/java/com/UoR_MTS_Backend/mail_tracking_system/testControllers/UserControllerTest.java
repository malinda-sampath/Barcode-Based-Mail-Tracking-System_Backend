package com.UoR_MTS_Backend.mail_tracking_system.testControllers;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.UserController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.LoginResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.services.JWTService;
import com.UoR_MTS_Backend.mail_tracking_system.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JWTService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLoginUser() throws Exception {
        LoginUserDTO loginDTO = new LoginUserDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");

        User mockUser = new User();
        mockUser.setFullName("Test User");

        Mockito.when(userService.loginUser(Mockito.any(LoginUserDTO.class))).thenReturn(mockUser);
        Mockito.when(jwtService.generateToken(mockUser)).thenReturn("mock-token");
        Mockito.when(jwtService.getExpirationTime()).thenReturn(Date.from(Instant.now().plusSeconds(3600)).getTime());

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Welcome back! You have successfully logged in."))
                .andExpect(jsonPath("$.data.token").value("mock-token"));
    }

    @Test
    void testRegisterBranchManager() throws Exception {
        RegisterUserDTO registerDTO = new RegisterUserDTO();
        registerDTO.setFullName("Branch Manager");
        registerDTO.setEmail("manager@example.com");

        Mockito.when(userService.createUser(Mockito.any(RegisterUserDTO.class), Mockito.eq(RoleEnum.BRANCH_MANAGER)))
                .thenReturn("Branch Manager registered successfully");

        mockMvc.perform(post("/api/user/register-branch-manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Branch Manager registered successfully"));
    }

    @Test
    void testAuthenticateHandler() throws Exception {
        // This test assumes the SecurityContext already returns a mock user.
        // But since SecurityContextHolder is used directly in your controller,
        // you may need a custom AuthenticationPrincipal setup or slice test for this.

        mockMvc.perform(get("/api/user/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("Hello")));
    }
}
