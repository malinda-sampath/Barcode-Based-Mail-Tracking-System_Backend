package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "claim_person_details")
public class MailClaimPerson {
    @Id
    private String personID;
    private String personName;
    private String personContact;
    private String NIC;
    private String note;

    @PrePersist
    public void generatePersonID() {
        this.personID = "PID-" + System.currentTimeMillis();
    }

    public MailClaimPerson(String personName, String personContact, String NIC, String note) {
        this.personName = personName;
        this.personContact = personContact;
        this.NIC = NIC;
        this.note = note;
    }
}
