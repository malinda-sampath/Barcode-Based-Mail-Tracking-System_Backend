package com.UoR_MTS_Backend.mail_tracking_system.dtos.websocketResponse;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WCBranchUpdateDTO {
    String action;
    BranchDTO branch;
}
