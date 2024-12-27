package com.UoR_MTS_Backend.mail_tracking_system.dto;

import java.time.LocalDateTime;

public class BranchDTO {
    private int branchCode;
    private String branchName;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

    // Constructors
    public BranchDTO() {
    }

    public BranchDTO(int branchCode, String branchName, LocalDateTime insertDate, LocalDateTime updateDate) {
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
}
