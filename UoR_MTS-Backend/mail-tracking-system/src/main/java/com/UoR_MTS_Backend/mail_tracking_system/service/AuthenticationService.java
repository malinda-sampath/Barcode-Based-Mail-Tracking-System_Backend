package com.UoR_MTS_Backend.mail_tracking_system.service;

import com.UoR_MTS_Backend.mail_tracking_system.dto.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dto.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.model.User;

public interface AuthenticationService {
    public User signup(RegisterUserDTO input);
    public User authenticate(LoginUserDTO input);
}
