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

    @Column()
    private String email;

    @Column
    private LocalDateTime insertedAt;

    @Column(name = "mail_type")
    private String mailType;

    @Column(name = "mail_tracking_number")
    private int mailTrackingNumber;

}
