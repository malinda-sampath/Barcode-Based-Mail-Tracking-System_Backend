package com.UoR_MTS_Backend.mail_tracking_system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "claim_person_details")
public class ClaimPersonDetails {
    @Id
    private String pickupPersonID;
    private String personName;
    private String personContact;
    private String NIC;
    private String note;
}
