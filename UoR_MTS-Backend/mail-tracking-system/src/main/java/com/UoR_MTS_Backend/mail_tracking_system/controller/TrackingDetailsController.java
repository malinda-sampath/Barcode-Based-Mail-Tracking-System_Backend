package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.TrackingDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.TrackingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/tracking")
public class TrackingDetailsController {

    @Autowired
    private TrackingDetailsService trackingDetailsService;

    @PostMapping("/save")
    public String saveTrackingDetails(@RequestBody TrackingDetailsDTO trackingDetailsDTO){
        String message = trackingDetailsService.saveTrackingDetails(trackingDetailsDTO);

        return message;
    }
}