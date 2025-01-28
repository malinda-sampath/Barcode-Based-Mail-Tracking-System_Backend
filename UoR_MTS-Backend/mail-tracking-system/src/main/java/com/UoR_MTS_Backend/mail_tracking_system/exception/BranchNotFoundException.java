package com.UoR_MTS_Backend.mail_tracking_system.exception;


public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException(String message) {
        super(message);
    }
}

