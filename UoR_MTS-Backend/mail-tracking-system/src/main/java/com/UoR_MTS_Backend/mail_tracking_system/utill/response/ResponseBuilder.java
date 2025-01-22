package com.UoR_MTS_Backend.mail_tracking_system.utill.response;
import org.springframework.http.ResponseEntity;


public class ResponseBuilder {
    public static <T> ResponseEntity<StandardResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(new StandardResponse<>(200, message, data));
    }

    public static <T> ResponseEntity<StandardResponse<T>> error(String message, T data) {
        return ResponseEntity.status(500).body(new StandardResponse<>(500, message, data));
    }

    public static <T> ResponseEntity<StandardResponse<T>> notFound(String message) {
        return ResponseEntity.status(404).body(new StandardResponse<>(404, message, null));
    }

    public static <T> ResponseEntity<StandardResponse<T>> badRequest(String message, T data) {

        return ResponseEntity.status(400).body(new StandardResponse<>(400, message, data));
    }


}