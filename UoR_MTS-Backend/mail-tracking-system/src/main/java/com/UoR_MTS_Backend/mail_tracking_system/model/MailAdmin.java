package com.UoR_MTS_Backend.mail_tracking_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="MailAdmin")
public class MailAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long mailAdminId;

    @Column(name="Name",nullable = false)
    private String name;

    @Column(name="UserName",nullable = false, unique = true)
    private String userName;

    @Column(name = "Password",nullable = false)
    private String password;

    @Column(name="Email",nullable = false, unique = true)
    private String email;

    @Column(name="Contact_No")
    private String contact;

    @Column(name="Role")
    private String role;

    @Column(name="Insert_Date")
    private LocalDateTime insertDate;

    @Column(name="Update_date")
    private LocalDateTime updateDate;

}
