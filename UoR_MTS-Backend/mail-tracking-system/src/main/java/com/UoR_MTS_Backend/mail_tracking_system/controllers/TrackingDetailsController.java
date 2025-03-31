package com.UoR_MTS_Backend.mail_tracking_system.controllers;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.TrackingDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.services.TrackingDetailsService;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utils.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/tracking")
@AllArgsConstructor
public class TrackingDetailsController {

    private final TrackingDetailsService trackingDetailsService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse<String>> saveTrackingDetails(@RequestBody TrackingDetailsDTO trackingDetailsDTO) {

        String message = trackingDetailsService.saveTrackingDetails(trackingDetailsDTO);
        return ResponseBuilder.success(message, null);
    }
}
