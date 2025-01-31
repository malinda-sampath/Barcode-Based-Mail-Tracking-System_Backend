package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.service.BackupService;
import com.UoR_MTS_Backend.mail_tracking_system.service.ClearService;
import com.UoR_MTS_Backend.mail_tracking_system.service.RestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/database")
public class DatabaseController {
    @Autowired
    private BackupService backupService;

    @Autowired
    private ClearService clearService;

    @Autowired
    private RestoreService restoreService;

    @GetMapping("/backup")
    public String backupDatabase() {
        try {
            backupService.backupDatabase("src/main/resources/backup/backup.sql");
            return "Database backup successful!";
        } catch (IOException e) {
            return "Failed to backup database: " + e.getMessage();
        }
    }

    @DeleteMapping("/clear")
    public String clearDatabase() {
        String clearence = clearService.clearDatabase();
        return clearence;
    }

    @PostMapping("/restore")
    public String restoreDatabase() {
        try {
            String restoration = restoreService.restoreDatabase("src/main/resources/backup/backup.sql");
            return restoration;
        } catch (IOException e) {
            return "Failed to restore database: " + e.getMessage();
        }
    }
}
