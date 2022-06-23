package com.example.io_backend.service;

import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.AdditionalServices;
import com.example.io_backend.repository.AdditionalServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdditionalServicesService {

    private final AdditionalServicesRepository additionalServicesRepository;

    public List<AdditionalServices> getAdditionalServices(){
        return additionalServicesRepository.findAll();
    }

    public AdditionalServices getAdditionalServicesById(Integer id){
        return additionalServicesRepository.findById(id).orElseThrow(()-> new NotFoundException("No record with that ID"));
    }

    public AdditionalServices addAdditionalServices(AdditionalServices additionalServices){
        return additionalServicesRepository.save(additionalServices);
    }

    public void updateAdditionalServices(AdditionalServices additionalServices, Integer id) {
        var a = additionalServicesRepository.findById(id).orElseThrow(()-> new NotFoundException("No record with that ID"));

        a.setId(additionalServices.getId());
        a.setDate(additionalServices.getDate());
        a.setAdditionalServicesType(additionalServices.getAdditionalServicesType());
        a.setJustification(additionalServices.getJustification());

        additionalServicesRepository.save(a);
    }

    public void deleteAdditionalServices(Integer id) {
        var a = additionalServicesRepository.findById(id).orElseThrow(()-> new NotFoundException("No record with that ID"));

        additionalServicesRepository.delete(a);
    }

}
