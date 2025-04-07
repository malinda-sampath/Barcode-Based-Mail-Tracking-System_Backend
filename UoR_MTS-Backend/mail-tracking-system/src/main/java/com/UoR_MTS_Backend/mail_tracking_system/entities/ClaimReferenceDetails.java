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
@Table(name = "mail_reference_details")
public class ClaimReferenceDetails{
    @Id
    private String referenceNumber;
    private String pickupPersonID;
    private String claimedPersonID;
}
