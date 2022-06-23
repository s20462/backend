package com.example.io_backend.service;

import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.AmbulanceAvailability;
import com.example.io_backend.repository.AmbulanceAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmbulanceAvailabilityService {

    private final AmbulanceAvailabilityRepository ambulanceAvailabilityRepository;

    public List<AmbulanceAvailability> getAmbulanceAvailabilities(){
        return ambulanceAvailabilityRepository.findAll();
    }

    public AmbulanceAvailability getAmbulanceAvailabilityById(Integer id){
        return ambulanceAvailabilityRepository.findById(id).orElseThrow(()-> new NotFoundException("No record with that ID"));
    }

    public AmbulanceAvailability addAmbulanceAvailability(AmbulanceAvailability ambulanceAvailability){
        return ambulanceAvailabilityRepository.save(ambulanceAvailability);
    }

    public void updateAmbulanceAvailability(AmbulanceAvailability ambulanceAvailability, Integer id) {
        var a = ambulanceAvailabilityRepository.findById(id).orElseThrow(()-> new NotFoundException("No record with that ID"));

        a.setId(ambulanceAvailability.getId());
        a.setAmbulance(ambulanceAvailability.getAmbulance());
        a.setAvailabilityType(ambulanceAvailability.getAvailabilityType());
        a.setDateStart(ambulanceAvailability.getDateStart());
        a.setDateEnd(ambulanceAvailability.getDateEnd());
        a.setDetails(ambulanceAvailability.getDetails());

        ambulanceAvailabilityRepository.save(a);
    }

    public void deleteAmbulanceAvailability(Integer id) {
        var a = ambulanceAvailabilityRepository.findById(id).orElseThrow(()-> new NotFoundException("No record with that ID"));

        ambulanceAvailabilityRepository.delete(a);
    }
}
