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
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "branch_name", nullable = false, length = 100, unique = true)
    private String branchName;

    @Column(name = "branch_code", nullable = false, length = 10, unique = true)
    private String branchCode;

    @Column(name = "insert_date", nullable = false, updatable = false)
    private LocalDateTime insertDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
