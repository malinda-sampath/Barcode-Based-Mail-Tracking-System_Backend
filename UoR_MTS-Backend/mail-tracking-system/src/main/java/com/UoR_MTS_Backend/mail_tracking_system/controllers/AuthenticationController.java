//package com.UoR_MTS_Backend.mail_tracking_system.controllers;
//
//import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
//import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
//import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.LoginResponseDTO;
//import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
//import com.UoR_MTS_Backend.mail_tracking_system.services.AuthenticationService;
//import com.UoR_MTS_Backend.mail_tracking_system.services.JWTService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequestMapping("/api/auth")
//@RestController
//@AllArgsConstructor
//
//public class AuthenticationController {
//    private final JWTService jwtService;
//    private final AuthenticationService authenticationService;
//
//    @PostMapping("/signup")
//    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDTO){
//        User registeredUser = authenticationService.signup(registerUserDTO);
//
//        return ResponseEntity.ok(registeredUser);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginUserDTO loginUserDTO){
//        User authenticatedUser = authenticationService.authenticate(loginUserDTO);
//
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//
//        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
//                jwtToken,
//                jwtService.getExpirationTime()
//        );
//
//
//        return ResponseEntity.ok(loginResponseDTO);
//    }
//}
