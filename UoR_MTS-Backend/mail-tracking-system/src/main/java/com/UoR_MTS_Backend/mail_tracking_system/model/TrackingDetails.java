package com.UoR_MTS_Backend.mail_tracking_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "tracking_details")
public class TrackingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime insertedAt;

    @Column(name = "mail_type",nullable = false)
    private String mailType;

    @Column(name = "mail_tracking_number",nullable = false)
    private int mailTrackingNumber;

    public TrackingDetails(String email, LocalDateTime insertedAt, String mailType, int mailTrackingNumber) {
        this.email = email;
        this.insertedAt = insertedAt;
        this.mailType = mailType;
        this.mailTrackingNumber = mailTrackingNumber;
    }
}
