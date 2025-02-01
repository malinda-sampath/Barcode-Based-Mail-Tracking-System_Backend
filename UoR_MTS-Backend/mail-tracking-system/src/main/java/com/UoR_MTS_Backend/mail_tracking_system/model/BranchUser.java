package com.UoR_MTS_Backend.mail_tracking_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "branch_users")
public class BranchUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="branch_user_id")
    private String branchUserId;

    @Column(name = "branch_user_name")
    private String branchUserName;
    @Column(name = "branch_user_password")
    private String branchUserPassword;
    @Column(name = "branch_coge")
    private String branchCode;

    public BranchUser(String branchUserName, String branchUserPassword, String branchCode) {
    }
}
