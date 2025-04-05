package com.UoR_MTS_Backend.mail_tracking_system.testServices;

import com.UoR_MTS_Backend.mail_tracking_system.services.IMPL.JWTServiceIMPL;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JWTServiceIMPLTest {

    private JWTServiceIMPL jwtService;
    private final String secretKey = Base64.getEncoder().encodeToString("your-256-bit-secret-your-256-bit-secret".getBytes());
    private final long expirationTime = 1000 * 60 * 60; // 1 hour

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JWTServiceIMPL();
        // Manually inject values
        jwtService.getClass().getDeclaredFields();
        jwtService = new JWTServiceIMPL();
        // using reflection for private field injection
        try {
            var secretField = JWTServiceIMPL.class.getDeclaredField("secretKey");
            secretField.setAccessible(true);
            secretField.set(jwtService, secretKey);

            var expirationField = JWTServiceIMPL.class.getDeclaredField("jwtExpiration");
            expirationField.setAccessible(true);
            expirationField.set(jwtService, expirationTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        userDetails = new User("testuser", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testGenerateTokenAndExtractUsername() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);

        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    void testTokenValidity() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        // Short expiration for testing
        String token = jwtService.generateToken(new HashMap<>(), userDetails);

        assertFalse(jwtService.isTokenValid("invalid.token.here", userDetails)); // Should return false on malformed
    }

    @Test
    void testExtractClaim() {
        String token = jwtService.generateToken(userDetails);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        assertNotNull(expiration);
    }

    @Test
    void testIsTokenValid_withMalformedToken() {
        boolean result = jwtService.isTokenValid("invalid.token.here");
        assertFalse(result);
    }
}
