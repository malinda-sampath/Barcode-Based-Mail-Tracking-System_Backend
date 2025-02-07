package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "branch_users")
public class BranchManager {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="branch_manager_id")
    private String branchManagerId;

    @Column(name = "branch_user_name")
    private String branchManagerName;
    @Column(name = "branch_user_password")
    private String branchManagerPassword;
    @Column(name = "branch_code")
    private String branchCode;

    public BranchManager(String branchManagerName, String branchManagerPassword, String branchCode) {
    }
}
