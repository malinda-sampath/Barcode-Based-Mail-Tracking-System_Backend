package com.UoR_MTS_Backend.mail_tracking_system.configs.WebSocket;

import com.UoR_MTS_Backend.mail_tracking_system.services.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@AllArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000")
                .addInterceptors(new WebSocketAuthInterceptor(jwtService,userDetailsService)) // ✅ Add JWT authentication
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Clients will subscribe to "/topic"
        registry.setApplicationDestinationPrefixes("/app"); // Clients will send messages with "/app"
    }
}