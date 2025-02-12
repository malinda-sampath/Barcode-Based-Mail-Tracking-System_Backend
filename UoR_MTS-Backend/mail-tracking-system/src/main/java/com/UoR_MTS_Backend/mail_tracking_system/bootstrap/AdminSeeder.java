package com.UoR_MTS_Backend.mail_tracking_system.bootstrap;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Role;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.RoleRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createAdmin();
    }

    private void createAdmin() {
        RegisterUserDTO userDTO = new RegisterUserDTO();
        userDTO.setFullName("Malinda Sampath");
        userDTO.setEmail("malindasampath45@gmail.com");
        userDTO.setPassword("admin");
        userDTO.setContact("071234567");
        userDTO.setBranchCode("BR-0001");

        Optional<Role> optionalRole = roleRepo.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepo.findByEmail(userDTO.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User();
        modelMapper.map(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(optionalRole.get());

        try{
        userRepo.save(user);
            } catch (Exception e) {
                System.out.println(e);
        }
    }
}
