package com.UoR_MTS_Backend.mail_tracking_system.exception;

public class NoMailActivitiesFoundException extends RuntimeException {
    public NoMailActivitiesFoundException(String message) {
        super(message);
    }
}
