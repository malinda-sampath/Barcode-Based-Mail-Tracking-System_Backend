package com.UoR_MTS_Backend.mail_tracking_system.exception;

public class MailRecordNotFoundException extends RuntimeException {
    public MailRecordNotFoundException(String message) {
        super(message);
    }
}

