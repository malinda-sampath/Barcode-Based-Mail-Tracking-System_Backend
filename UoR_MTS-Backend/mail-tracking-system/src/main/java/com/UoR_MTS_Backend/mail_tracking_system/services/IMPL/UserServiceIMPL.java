package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.ProfileUpdateRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Role;
import com.UoR_MTS_Backend.mail_tracking_system.exception.AlreadyExistsException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.RoleRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceIMPL implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private ModelMapper modelMapper;

    @Override
    public User loginUser(LoginUserDTO loginResponseDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginResponseDTO.getEmail(),
                        loginResponseDTO.getPassword()
                )
        );

        return userRepo.findByEmail(loginResponseDTO.getEmail())
                .orElseThrow();
    }

    @Override
    public String createUser(RegisterUserDTO input, RoleEnum roleEnum) {
        if (userRepo.findByEmail(input.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Optional<Role> optionalRole = roleRepo.findByName(roleEnum);

        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role not found");
        }

        User user = modelMapper.map(input, User.class);
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());
        userRepo.save(user);
        return "User created successfully";
    }

    @Override
    public String updateUser(String userID, ProfileUpdateRequestDTO profileUpdateRequestDTO, MultipartFile profilePicture) {
        User user = userRepo.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure email uniqueness
        Optional<User> existingUser = userRepo.findByEmail(profileUpdateRequestDTO.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            throw new AlreadyExistsException("User already exists with this email");
        }

        // Update basic fields
        user.setFullName(profileUpdateRequestDTO.getName());
        user.setContact(profileUpdateRequestDTO.getContact());
        user.setEmail(profileUpdateRequestDTO.getEmail());

        // Update password only if provided
        if (profileUpdateRequestDTO.getPassword() != null && !profileUpdateRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(profileUpdateRequestDTO.getPassword()));
        }

        // Handle profile picture update (Convert MultipartFile to byte array)
        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                user.setProfilePicture(profilePicture.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to process profile picture", e);
            }
        }

        userRepo.save(user);
        return "User updated successfully";
    }
}
