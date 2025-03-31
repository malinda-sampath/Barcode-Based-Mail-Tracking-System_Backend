package com.UoR_MTS_Backend.mail_tracking_system.exception;


public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
