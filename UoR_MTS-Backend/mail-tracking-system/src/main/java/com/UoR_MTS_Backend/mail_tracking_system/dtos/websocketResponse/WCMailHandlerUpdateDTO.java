package com.UoR_MTS_Backend.mail_tracking_system.dtos.websocketResponse;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailHandlerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WCMailHandlerUpdateDTO {
    String action;
    MailHandlerResponseDTO mailHandler;
}
