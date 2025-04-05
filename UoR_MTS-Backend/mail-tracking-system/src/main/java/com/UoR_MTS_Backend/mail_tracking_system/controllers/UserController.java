package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailHandlerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.ProfileUpdateRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.HeaderResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.LoginResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.ProfileResponseDTO;
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
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StandardResponse<ProfileResponseDTO>> authenticateHandler(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        ProfileResponseDTO profileResponseDTO = new ProfileResponseDTO(
                currentUser.getId(),
                currentUser.getFullName(),
                currentUser.getContact(),
                currentUser.getEmail(),
                currentUser.getProfilePicture()
        );

        return ResponseBuilder.success("Hello, "+currentUser.getFullName() + "! Here is your profile summary.",profileResponseDTO);
    }

    @PutMapping("/update/{userID}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StandardResponse<String>> updateProfile(
            @PathVariable String userID,
            @RequestParam String name,
            @RequestParam String contact,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) MultipartFile profilePicture) {
        ProfileUpdateRequestDTO profileUpdateRequestDTO = new ProfileUpdateRequestDTO(name, contact, email, password);

        String message = userService.updateUser(userID, profileUpdateRequestDTO, profilePicture);
        return ResponseBuilder.success(message, null);
    }

    @GetMapping("/header-details")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StandardResponse<HeaderResponseDTO>> getHeaderDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        HeaderResponseDTO headerResponseDTO = new HeaderResponseDTO(
                currentUser.getFullName(),
                currentUser.getProfilePicture()
        );

        return ResponseBuilder.success("Here are your header details.", headerResponseDTO);
    }
}
