package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.LoginResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;

import java.util.List;

public interface UserService {
    public List<User> getAllMailHandlers();

    public String createUser(RegisterUserDTO input, RoleEnum roleEnum);

    public User loginUser(LoginUserDTO loginUserDTO);
}
