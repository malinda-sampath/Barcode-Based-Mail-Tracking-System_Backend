package com.UoR_MTS_Backend.mail_tracking_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="MailAdmin")
public class MailAdmin {
    @Id
    @Column(name="Id",length=45)
    private String mailAdminId;

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

    @Column(name="Insert_Date",nullable = false)
    private LocalDateTime insertDate;

    @Column(name="Update_date")
    private LocalDateTime updateDate;

    public MailAdmin() {
    }

    public MailAdmin(String mailAdminId, String name, String userName, String password, String email, String contact, String role, LocalDateTime insertDate, LocalDateTime updateDate) {
        this.mailAdminId = mailAdminId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.contact = contact;
        this.role = role;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
    }

    public String getMailAdminId() {
        return mailAdminId;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setMailAdminId(String mailAdminId) {
        this.mailAdminId = mailAdminId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "MailAdmin{" +
                "mailAdminId=" + mailAdminId +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", role='" + role + '\'' +
                ", insertDate=" + insertDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
