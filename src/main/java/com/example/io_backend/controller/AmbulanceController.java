package com.example.io_backend.controller;

import com.example.io_backend.model.Ambulance;
import com.example.io_backend.model.dto.AmbulanceAvailabilityDto;
import com.example.io_backend.model.dto.AmbulanceDto;
import com.example.io_backend.model.dto.response.AmbulanceResponse;
import com.example.io_backend.model.dto.response.EquipmentLogResponse;
import com.example.io_backend.model.dto.response.EquipmentResponse;
import com.example.io_backend.model.enums.AmbulanceKind;
import com.example.io_backend.model.enums.AmbulanceType;
import com.example.io_backend.service.AmbulanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/ambulances")
public class AmbulanceController {
    private final AmbulanceService ambulanceService;

    @PostMapping("/{id}/availability")
    @Operation(
            method = "POST",
            summary = "changes status of ambulance given by id"
    )
    public void changeStatus(
            @PathVariable
            Integer id,
            @Valid
            @RequestBody
            AmbulanceAvailabilityDto dto) {
        ambulanceService.setStatus(id, dto);
    }

    @PostMapping("/{id}/equipment/{eqid}")
    @Operation(
            method = "POST",
            summary = "Assigns equipment of id to ambulance of given id"
    )
    public ResponseEntity<EquipmentLogResponse> assignEquipment(
            @NonNull
            @PathVariable Integer id,
            @NonNull
            @PathVariable Long eqid) {
        return ResponseEntity.ok(ambulanceService.assignEquipment(id, eqid));
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Gets all ambulances"
    )
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ambulanceService.getAmbulances());
    }

    @GetMapping("{id}")
    @Operation(
            method = "GET",
            summary = "Gets ambulance by id"
    )
    public ResponseEntity<?> getById(
            @NonNull
            @PathVariable Integer id) {
        AmbulanceResponse dto = ambulanceService.getAmbulance(id);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ambulance " + id + " not found");
        } else return ResponseEntity.ok(dto);
    }

    @GetMapping("/by/{seats}")
    @Operation(
            method = "GET",
            summary = "Queries for all ambulances with given number of seats"
    )
    public ResponseEntity<?> getByNumberOfSeats(
            @NonNull
            @PathVariable Integer seats) {
        return ResponseEntity.ok(ambulanceService.getAmbulances(seats));
    }

    @Operation(
            method = "GET",
            summary = "Queries for all ambulances of given type"
    )
    public ResponseEntity<?> getByType(@RequestParam AmbulanceType type) {
        return ResponseEntity.ok(ambulanceService.getAmbulances(type));
    }

    @GetMapping("/by/{kind}")
    @Operation(
            method = "GET",
            summary = "Queries for all ambulances of given kind"
    )
    public ResponseEntity<?> getByKind(@RequestParam AmbulanceKind kind) {
        return ResponseEntity.ok(ambulanceService.getAmbulances(kind));
    }

    @GetMapping("/plates/{plates}")
    @Operation(
            method = "GET",
            summary = "Get an ambulance with given license plates"
    )
    public ResponseEntity<?> getByLicensePlate(@PathVariable String plates) {
        AmbulanceResponse dto = ambulanceService.getAmbulance(plates);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ambulance " + plates + " not found");
        } else return ResponseEntity.ok(dto);
    }

    @GetMapping("/available")
    @Operation(
            method = "GET",
            summary = "Gets all available ambulances"
    )
    public ResponseEntity<?> getAvailable() {
        List<AmbulanceResponse> responses = ambulanceService.getAvailable();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Adds new ambulance"
    )
    public ResponseEntity<?> addAmbulance(@Valid @RequestBody AmbulanceDto ambulance) {
        return ResponseEntity.ok(ambulanceService.addAmbulance(ambulance));
    }

    @PutMapping("/{id}")
    @Operation(
            method = "PUT",
            summary = "Updates an ambulance"
    )
    public ResponseEntity<?> updateAmbulance(@Valid @RequestBody AmbulanceDto ambulance, @PathVariable Integer id){
        ambulanceService.updateAmbulance(ambulance, id);
        return ResponseEntity.ok().body("Ambulance " + id + "updated");
    }

    @DeleteMapping("/{id}")
    @Operation(
            method = "DELETE",
            summary = "Deleted an ambulance"
    )
    public void deleteAmbulance(@PathVariable Integer id) {
        ambulanceService.deleteAmbulance(id);
    }

}
