package com.example.io_backend.controller;

import com.example.io_backend.model.Facility;
import com.example.io_backend.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/facility")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping("")
    public List<Facility> getAll() {
        return facilityService.getFacilities();
    }

    @GetMapping("{id}")
    public Facility getById(@PathVariable Integer id) {
        return facilityService.getFacilitybyId(id);
    }

    @PostMapping("")
    public Facility add(@RequestBody Facility facility) {
        return facilityService.addFacility(facility);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Facility facility,@PathVariable Integer id){
        facilityService.updateFacility(facility,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        facilityService.deleteFacility(id);
    }

}
