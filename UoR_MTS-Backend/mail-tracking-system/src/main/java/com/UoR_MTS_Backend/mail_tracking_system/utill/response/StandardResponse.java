package com.UoR_MTS_Backend.mail_tracking_system.utill.response;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponse<T> {
    private int status;
    private String message;
    private T data;


}

