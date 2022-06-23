package com.example.io_backend.controller;


import com.example.io_backend.model.dto.request.ApproveEmergencyRequest;
import com.example.io_backend.model.dto.request.CreateEmergencyRequest;
import com.example.io_backend.model.dto.response.EmergencyResponse;
import com.example.io_backend.service.EmergencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emergency")
@RequiredArgsConstructor
@Slf4j
public class EmergencyController {
    private final EmergencyService emergencyService;

    @PreAuthorize("hasRole('user')")
    @Operation(summary = "Creates new emergency report", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/new")
    public ResponseEntity<?> newEmergency(@RequestBody CreateEmergencyRequest emergencyRequest) {
        return ResponseEntity.ok(emergencyService.newEmergency(emergencyRequest));
    }

    @PreAuthorize("hasRole('dispositor')")
    @Operation(summary = "Currently logged dispatcher approves request of given id", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/approve/{id}")
    public void approveEmergency(@RequestBody ApproveEmergencyRequest request, @PathVariable Integer id) {
        emergencyService.approveEmergency(request, id);
    }

    @PreAuthorize("hasRole('dispositor')")
    @Operation(summary = "Get all unapproved emergencies", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/unapproved")
    public ResponseEntity<List<EmergencyResponse>> getUnapproved() {
        return ResponseEntity.ok(emergencyService.getUnapproved());
    }

    @PreAuthorize("hasRole('dispositor')")
    @Operation(summary = "Get all approved emergencies", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/approved")
    public ResponseEntity<List<EmergencyResponse>> getApproved() {
        return ResponseEntity.ok(emergencyService.getApproved());
    }

    @PreAuthorize("hasRole('dispositor')")
    @Operation(summary = "Get all closed emergency reports", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/closed")
    public ResponseEntity<?> getClosed() {
        return ResponseEntity.ok(emergencyService.getClosed());
    }
}
