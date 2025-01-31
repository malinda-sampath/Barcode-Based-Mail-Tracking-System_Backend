package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.service.BackupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BackupServiceIMPL implements BackupService {
    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void backupDatabase(String backupPath) throws IOException {
        String command = "mysqldump -u "+username+" -p"+password+" uor_mts -r " + backupPath;
        Process process = Runtime.getRuntime().exec(command);
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Backup process interrupted", e);
        }
    }
}
