package com.UoR_MTS_Backend.mail_tracking_system.service.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.service.RestoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class RestoreServiceIMPL implements RestoreService {
    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public String restoreDatabase(String backupPath) throws IOException {
        String command = "cmd.exe /c mysql -u "+username+" -p"+password+" uor_mts < " + backupPath;
        Process process = Runtime.getRuntime().exec(command);
        // Consume the process's output and error streams
        Thread outputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading process output", e);
            }
        });

        Thread errorThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println(line); // Log error if needed
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading process error", e);
            }
        });

        outputThread.start();
        errorThread.start();

        try {
            process.waitFor(); // Wait for the process to complete
            outputThread.join();
            errorThread.join();
            return "Database restored successfully!";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Restore process interrupted", e);
        }
    }
}
