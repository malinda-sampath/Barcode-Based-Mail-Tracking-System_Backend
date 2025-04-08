package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class MailClaimPerson {

    @Id
    private String personID;

    private String personName;
    private String personContact;
    private String NIC;
    private String note;

    public MailClaimPerson(String personName, String personContact, String NIC, String note) {
        this.personName = personName;
        this.personContact = personContact;
        this.NIC = NIC;
        this.note = note;

        int randomNum = new Random().nextInt(900) + 100;
        this.personID = "PID-" + Math.abs(UUID.randomUUID().hashCode() % 1000000) + "-" + randomNum;
    }
}
