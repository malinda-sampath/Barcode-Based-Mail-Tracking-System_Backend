package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.LoginResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Role;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.RoleRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public List<User> getAllMailHandlers(){
        List<User> users = new ArrayList<>();

        try{
            modelMapper.map(userRepo.findAllByRole_Name(RoleEnum.SUPER_ADMIN), users);
        } catch (Exception e){
            throw new RuntimeException("Error occurred while fetching mail handlers");
        }
        return users;
    }
}
