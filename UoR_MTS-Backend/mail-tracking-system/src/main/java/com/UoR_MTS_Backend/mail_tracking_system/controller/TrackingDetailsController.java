package com.UoR_MTS_Backend.mail_tracking_system.controller;

import com.UoR_MTS_Backend.mail_tracking_system.dto.TrackingDetailsDTO;
import com.UoR_MTS_Backend.mail_tracking_system.service.TrackingDetailsService;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.ResponseBuilder;
import com.UoR_MTS_Backend.mail_tracking_system.utill.response.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
