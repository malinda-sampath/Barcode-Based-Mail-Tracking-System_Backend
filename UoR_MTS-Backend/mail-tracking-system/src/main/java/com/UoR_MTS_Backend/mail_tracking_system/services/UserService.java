package com.UoR_MTS_Backend.mail_tracking_system.services;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.LoginUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.ProfileUpdateRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public User loginUser(LoginUserDTO loginUserDTO);

    public String updateUser(String userID, ProfileUpdateRequestDTO profileUpdateRequestDTO, MultipartFile profilePicture);
}
