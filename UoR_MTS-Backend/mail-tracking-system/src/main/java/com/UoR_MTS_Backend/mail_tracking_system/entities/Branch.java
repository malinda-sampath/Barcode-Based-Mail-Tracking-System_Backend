package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "insert_date")
    private LocalDateTime insertDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "description", length = 500)
    private String description;

    @PrePersist
    public void generateId() {
        if (this.branchCode == null) {
            int randomNum = new Random().nextInt(9000) + 1000; // Generates a number between 100-999
            this.branchCode = "BR-" + randomNum;
        }
    }
}
