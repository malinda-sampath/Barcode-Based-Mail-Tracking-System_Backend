package com.UoR_MTS_Backend.mail_tracking_system.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface RestoreService {
    String restoreDatabase(String s) throws IOException;
}
