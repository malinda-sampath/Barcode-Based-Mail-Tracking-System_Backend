package com.UoR_MTS_Backend.mail_tracking_system.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailClaimDetailsDTO {
    private List<String> barcodeID;
    private String branchCode;
    private String referenceNumber;
    private String personName;
    private String personContact;
    private String status;
    private String NIC;
    private String note;
}
