package com.example.io_backend.controller;

import com.example.io_backend.model.AmbulanceAvailability;
import com.example.io_backend.service.AmbulanceAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/ambulanceAvailability")
@RequiredArgsConstructor
public class AmbulanceAvailabilityController {
    private final AmbulanceAvailabilityService ambulanceAvailabilityService;

    @GetMapping("")
    public ResponseEntity<List<AmbulanceAvailability>> getAll() {
        return new ResponseEntity<>(ambulanceAvailabilityService.getAmbulanceAvailabilities(), HttpStatus.OK) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<AmbulanceAvailability> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(ambulanceAvailabilityService.getAmbulanceAvailabilityById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AmbulanceAvailability> add(@RequestBody AmbulanceAvailability ambulanceAvailability) {
        return new ResponseEntity<>(ambulanceAvailabilityService.addAmbulanceAvailability(ambulanceAvailability), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody AmbulanceAvailability ambulanceAvailability,@PathVariable Integer id){
        ambulanceAvailabilityService.updateAmbulanceAvailability(ambulanceAvailability, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        ambulanceAvailabilityService.deleteAmbulanceAvailability(id);
    }

}
