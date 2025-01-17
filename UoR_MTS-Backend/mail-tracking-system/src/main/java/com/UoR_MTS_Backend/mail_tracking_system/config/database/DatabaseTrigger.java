package com.UoR_MTS_Backend.mail_tracking_system.config.database;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseTrigger {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTrigger.class);
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initializeDatabaseTriggerAndEvent() {
        String enableEventSchedulerSQL = "SET GLOBAL event_scheduler = ON";
        String dropEventSQL = "DROP EVENT IF EXISTS transfer_daily_mail_event";

        String createEventSQL = """
            CREATE EVENT IF NOT EXISTS transfer_daily_mail_event
            ON SCHEDULE EVERY 1 DAY STARTS '2025-01-18 23:59:00'
            DO
            BEGIN
                -- Insert data from daily_mail to mail_record
                INSERT INTO uor_mts.mail_record (
                    branch_code,
                    branch_name,
                    sender_name,
                    receiver_name,
                    mail_type,
                    tracking_number,
                    mail_description,
                    barcode_id,
                    barcode_image,
                    insert_date_time,
                    update_date_time
                )
                SELECT
                    branch_code,
                    branch_name,
                    sender_name,
                    receiver_name,
                    mail_type,
                    tracking_number,
                    mail_description,
                    barcode_id,
                    barcode_image,
                    insert_date_time,
                    update_date_time
                FROM uor_mts.daily_mail;
            
                -- Check if the insertion was successful and delete if it was
                IF ROW_COUNT() > 0 THEN
                    DELETE FROM uor_mts.daily_mail;
                END IF;
            END
        """;

        try {
            // Enable the event scheduler
            jdbcTemplate.execute(enableEventSchedulerSQL);
            logger.info("MySQL event scheduler enabled successfully.");

            // Drop the existing event if it exists
            jdbcTemplate.execute(dropEventSQL);
            logger.info("Old event dropped successfully.");

            // Create the new event
            jdbcTemplate.execute(createEventSQL);
            logger.info("Database event created successfully.");
        } catch (Exception e) {
            logger.error("Error creating or dropping event: " + e.getMessage(), e);
        }
    }
}