package com.UoR_MTS_Backend.mail_tracking_system.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // ✅ WebSocket endpoint
                .setAllowedOrigins("http://localhost:3000") // ✅ Allow frontend connection
                .withSockJS(); // ✅ Enable SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Clients will subscribe to "/topic"
        registry.setApplicationDestinationPrefixes("/app"); // Clients will send messages with "/app"
    }
}