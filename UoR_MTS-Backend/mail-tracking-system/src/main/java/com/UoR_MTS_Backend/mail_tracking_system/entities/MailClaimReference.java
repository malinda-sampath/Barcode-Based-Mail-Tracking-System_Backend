package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "mail_reference_details")
public class MailClaimReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String referenceNumber;

    @ManyToOne
    @JoinColumn(name = "pickup_person_id", referencedColumnName = "personID")
    private MailClaimPerson pickupPerson;

    public MailClaimReference(String referenceNumber, MailClaimPerson mailClaimPerson) {
        this.referenceNumber = referenceNumber;
        this.pickupPerson = mailClaimPerson;
    }
}
