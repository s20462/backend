package com.example.io_backend.dto;

import com.example.io_backend.model.Ambulance;
import com.example.io_backend.model.enums.AvailabilityType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AmbulanceAvailabilityDto {

    private Integer id;
    private Ambulance ambulance;
    private AvailabilityType availabilityType;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String details;





}
