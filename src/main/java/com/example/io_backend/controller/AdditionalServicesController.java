package com.example.io_backend.controller;

import com.example.io_backend.model.AdditionalServices;
import com.example.io_backend.service.AdditionalServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/additionalServices")
@RequiredArgsConstructor
public class AdditionalServicesController {

    private final AdditionalServicesService additionalServicesService;

    @GetMapping("")
    public List<AdditionalServices> getAll() {
        return additionalServicesService.getAdditionalServices();
    }

    @GetMapping("{id}")
    public AdditionalServices getById(@PathVariable Integer id) {
        return additionalServicesService.getAdditionalServicesById(id);
    }

    @PostMapping("")
    public AdditionalServices add(@RequestBody AdditionalServices additionalServices) {
        return additionalServicesService.addAdditionalServices(additionalServices);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody AdditionalServices additionalServices,@PathVariable Integer id){
        additionalServicesService.updateAdditionalServices(additionalServices,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        additionalServicesService.deleteAdditionalServices(id);
    }

}
