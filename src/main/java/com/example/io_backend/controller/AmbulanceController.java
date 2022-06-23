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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/ambulances")
public class AmbulanceController {

    private final AmbulanceService ambulanceService;

    @PostMapping("/{id}/availability")
    public void changeStatus(@PathVariable Integer id, @RequestBody AmbulanceAvailabilityDto dto) {
        ambulanceService.setStatus(id, dto);
    }

    @PostMapping("/{id}/equipment/{eqid}")
    public ResponseEntity<EquipmentLogResponse> assignEquipment(@PathVariable Integer id, @PathVariable Integer eqid) {
        return ResponseEntity.ok(ambulanceService.assignEquipment(id, eqid));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ambulanceService.getAmbulances());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        AmbulanceResponse dto = ambulanceService.getAmbulance(id);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ambulance " + id + " not found");
        } else return ResponseEntity.ok(dto);
    }

    @GetMapping("/by/{seats}")
    public ResponseEntity<?> getByNumberOfSeats(@PathVariable Integer seats) {
        return ResponseEntity.ok(ambulanceService.getAmbulances(seats));
    }

    @GetMapping("/by/{type}")
    public ResponseEntity<?> getByType(@RequestParam AmbulanceType type) {
        return ResponseEntity.ok(ambulanceService.getAmbulances(type));
    }

    @GetMapping("/by/{kind}")
    public ResponseEntity<?> getByKind(@RequestParam AmbulanceKind kind) {
        return ResponseEntity.ok(ambulanceService.getAmbulances(kind));
    }

    @GetMapping("/plates/{plates}")
    public ResponseEntity<?> getByLicensePlate(@PathVariable String plates) {
        AmbulanceResponse dto = ambulanceService.getAmbulance(plates);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ambulance " + plates + " not found");
        } else return ResponseEntity.ok(dto);
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailable() {
        List<AmbulanceResponse> responses = ambulanceService.getAvailable();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<?> addAmbulance(@RequestBody AmbulanceDto ambulance) {
        return ResponseEntity.ok(ambulanceService.addAmbulance(ambulance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAmbulance(@RequestBody AmbulanceDto ambulance, @PathVariable Integer id){
        ambulanceService.updateAmbulance(ambulance, id);
        return ResponseEntity.ok().body("Ambulance " + id + "updated");
    }

    @DeleteMapping("/{id}")
    public void deleteAmbulance(@PathVariable Integer id) {
        ambulanceService.deleteAmbulance(id);
    }

}
