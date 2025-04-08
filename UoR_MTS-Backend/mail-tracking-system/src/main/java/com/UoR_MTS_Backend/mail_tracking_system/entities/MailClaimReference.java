package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class MailClaimReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String referenceNumber;
    private String personID;


    public MailClaimReference(String referenceNumber, String personID) {
        this.referenceNumber = referenceNumber;
        this.personID = personID;
    }
}
