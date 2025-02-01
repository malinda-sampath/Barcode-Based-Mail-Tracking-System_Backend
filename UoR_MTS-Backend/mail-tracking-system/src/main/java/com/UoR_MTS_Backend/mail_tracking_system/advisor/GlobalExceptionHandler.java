package com.UoR_MTS_Backend.mail_tracking_system.advisor;

import com.UoR_MTS_Backend.mail_tracking_system.exception.*;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

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



    @ExceptionHandler(BranchAlreadyExistsException.class)
    public ResponseEntity<Object> handleBranchAlreadyExistsException(BranchAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoDailyMailsFoundException.class)
    public ResponseEntity<StandardResponse<String>> handleNoDailyMailsFound(NoDailyMailsFoundException e) {
        // Use ResponseBuilder for consistent structure
        return ResponseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(NoMailActivitiesFoundException.class)
    public ResponseEntity<StandardResponse<String>> handleNoMailActivitiesFound(NoMailActivitiesFoundException e) {
        // Use ResponseBuilder for consistent structure
        return ResponseBuilder.notFound(e.getMessage());
    }

    @ExceptionHandler(BranchNotFoundException.class)
    public ResponseEntity<StandardResponse<String>> handleBranchNotFound(NoDailyMailsFoundException e) {
        // Use ResponseBuilder for consistent structure
        return ResponseBuilder.notFound(e.getMessage());
    }


}
