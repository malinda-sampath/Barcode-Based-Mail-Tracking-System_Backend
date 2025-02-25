package com.UoR_MTS_Backend.mail_tracking_system.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    public String extractUsername(String token);

    public boolean isTokenValid(String token, UserDetails userDetails);

    public String generateToken(UserDetails userDetails);

    public long getExpirationTime();
}
