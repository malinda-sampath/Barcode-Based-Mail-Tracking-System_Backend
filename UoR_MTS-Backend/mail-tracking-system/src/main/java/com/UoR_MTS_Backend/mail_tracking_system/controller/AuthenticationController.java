package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.response.LoginResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.User;
import com.UoR_MTS_Backend.mail_tracking_system.service.AuthenticationService;
import com.UoR_MTS_Backend.mail_tracking_system.service.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@AllArgsConstructor

public class AuthenticationController {
    private final JWTService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDTO){
        User registeredUser = authenticationService.signup(registerUserDTO);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginUserDTO loginUserDTO){
        User authenticatedUser = authenticationService.authenticate(loginUserDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
                jwtToken,
                jwtService.getExpirationTime()
        );

        return ResponseEntity.ok(loginResponseDTO);
    }
}
