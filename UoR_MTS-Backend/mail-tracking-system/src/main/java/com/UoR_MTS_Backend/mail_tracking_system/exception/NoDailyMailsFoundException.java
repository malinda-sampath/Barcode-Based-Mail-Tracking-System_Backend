package com.UoR_MTS_Backend.mail_tracking_system.exception;


public class NoDailyMailsFoundException extends RuntimeException {
    public NoDailyMailsFoundException(String message) {
        super(message);
    }
}

