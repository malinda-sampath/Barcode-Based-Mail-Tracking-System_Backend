package com.UoR_MTS_Backend.mail_tracking_system.advisor;

import com.UoR_MTS_Backend.mail_tracking_system.exception.MailActivityNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.MailAdminException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.ResourceNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.WriterException;
import com.UoR_MTS_Backend.mail_tracking_system.utill.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardResponse<String>> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(
                new StandardResponse<>(404, "Resource Not Found :"+ ex.getMessage(),null),
                HttpStatus.NOT_FOUND
        );
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardResponse<String>> handleIllegalArgument(IllegalArgumentException ex) {
        logger.error("Invalid argument provided: {}", ex.getMessage());
        return new ResponseEntity<>(
                new StandardResponse<>(400, "Bad Request :"+ ex.getMessage(),null),
                HttpStatus.BAD_REQUEST
        );
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse<String>> handleGlobalException(Exception ex) {
        // Log the exception with stack trace
        logger.error("Unexpected error occurred", ex);
        return ResponseBuilder.error("Unexpected error occurred: " + ex.getMessage(), null);
    }

    @ExceptionHandler(MailActivityNotFoundException.class)
    public ResponseEntity<StandardResponse<String>> handleMailActivityNotFoundException(MailActivityNotFoundException ex) {
        StandardResponse<String> response = new StandardResponse<>(404, "NOT_FOUND"+ ex.getMessage(),null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MailAdminException.class)
    public ResponseEntity<StandardResponse<Void>> handleMailAdminException(MailAdminException ex) {
        return ResponseBuilder.error(ex.getMessage(), null);
    }




}
