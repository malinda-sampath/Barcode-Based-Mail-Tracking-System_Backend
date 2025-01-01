package com.UoR_MTS_Backend.mail_tracking_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_code")
    private int branchCode;

    @Column(name = "branch_name", nullable = false, length = 100)
    private String branchName;

    @Column(name = "insert_date", nullable = false, updatable = false)
    private LocalDateTime insertDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    // Constructors
    public Branch() {
    }

    public Branch(int branchCode, String branchName, LocalDateTime insertDate, LocalDateTime updateDate) {
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
    }

    // Getters and Setters
    public int getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(int branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @PrePersist
    protected void onCreate() {
        this.insertDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchCode=" + branchCode +
                ", branchName='" + branchName + '\'' +
                ", insertDate=" + insertDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
