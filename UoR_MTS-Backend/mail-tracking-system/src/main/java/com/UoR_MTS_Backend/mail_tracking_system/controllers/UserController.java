package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailHandlerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.LoginResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.services.JWTService;
import com.UoR_MTS_Backend.mail_tracking_system.services.UserService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<LoginResponseDTO>> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        User authenticateUser = userService.loginUser(loginUserDTO);

        String jwtToken = jwtService.generateToken(authenticateUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO(
                jwtToken,
                jwtService.getExpirationTime()
        );

        return ResponseBuilder.success("Welcome back! You have successfully logged in.",loginResponse);
    }

    @PostMapping("/register-branch-manager")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<String>> registerBranchManager(@RequestBody RegisterUserDTO registerUserDTO){
        String message = userService.createUser(registerUserDTO, RoleEnum.BRANCH_MANAGER);

        return ResponseBuilder.success(message,null);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StandardResponse<User>> authenticateHandler(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseBuilder.success("Hello, "+currentUser.getFullName() + "! Here is your profile summary.",currentUser);
    }
}
