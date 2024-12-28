package com.UoR_MTS_Backend.mail_tracking_system.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MailAdminDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "UserName is mandatory")
    private String userName;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
    private String contact;

    @NotBlank(message = "Role is mandatory")
    private String role;

    public MailAdminDTO() {
    }

    public MailAdminDTO(String name, String userName, String password, String email, String contact, String role) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.contact = contact;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MailAdminDTO{" +
                "name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
