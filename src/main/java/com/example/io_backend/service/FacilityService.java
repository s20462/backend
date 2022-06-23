package com.example.io_backend.service;

import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.Facility;
import com.example.io_backend.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public List<Facility> getFacilities() {
        return facilityRepository.findAll();
    }

    public Facility getFacilitybyId(Integer id) {
        return facilityRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that Id"));
    }

    public Facility addFacility(Facility facility){
        return facilityRepository.save(facility);
    }

    public void updateFacility(Facility facility, Integer id){
        var f = facilityRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that Id"));
        f.setId(facility.getId());
        f.setFacilityType(facility.getFacilityType());
        f.setHospitalType(facility.getHospitalType());
        f.setName(facility.getName());
        f.setSet(facility.getSet());
        f.setMaximumBeds(facility.getMaximumBeds());

        facilityRepository.save(f);
    }

    public void deleteFacility(Integer id){
        var f = facilityRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that Id"));
        facilityRepository.delete(f);
    }
}
