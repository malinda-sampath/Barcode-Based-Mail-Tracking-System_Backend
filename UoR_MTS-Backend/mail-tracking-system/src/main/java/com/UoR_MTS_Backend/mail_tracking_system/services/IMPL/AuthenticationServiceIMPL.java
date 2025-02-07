//package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;
//
//import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
//import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
//import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
//import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
//import com.UoR_MTS_Backend.mail_tracking_system.services.AuthenticationService;
//import lombok.AllArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class AuthenticationServiceIMPL implements AuthenticationService {
//    private final UserRepo userRepo;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private ModelMapper modelMapper;
//
//    public User signup(RegisterUserDTO input){
//        User user = modelMapper.map(input, User.class);
//        user.setPassword(passwordEncoder.encode(input.getPassword()));
//        return userRepo.save(user);
//    }
//
//    public User authenticate(LoginUserDTO input){
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        input.getEmail(),
//                        input.getPassword()
//                )
//        );
//
//        return userRepo.findByEmail(input.getEmail())
//                .orElseThrow();
//    }
//}
