package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Add this
public class AuthController {

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Clear token cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0); // Delete cookie
        cookie.setPath("/");
        cookie.setHttpOnly(true); // Recommended for security
        cookie.setSecure(false); // Set to true if using HTTPS
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}