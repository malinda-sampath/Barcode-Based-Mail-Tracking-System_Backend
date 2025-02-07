package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.LoginResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.services.JWTService;
import com.UoR_MTS_Backend.mail_tracking_system.services.UserService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> userLogin(@RequestBody LoginUserDTO loginUserDTO){
        User authenticateUser = userService.loginUser(loginUserDTO);

        String jwtToken = jwtService.generateToken(authenticateUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO(
                jwtToken,
                jwtService.getExpirationTime()
        );

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register-mail-handler")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> registerMailHandler(@RequestBody RegisterUserDTO registerUserDTO){
        String message = userService.createUser(registerUserDTO, RoleEnum.MAIL_HANDLER);

        return ResponseEntity.ok(message);
    }

    @PostMapping("/register-branch-manager")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> registerBranchManager(@RequestBody RegisterUserDTO registerUserDTO){
        String message = userService.createUser(registerUserDTO, RoleEnum.BRANCH_MANAGER);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticateHandler(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
}
