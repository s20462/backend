package com.example.io_backend.dto;

import com.example.io_backend.model.enums.BloodType;
import lombok.Data;

@Data
public class MedicalInfoDto {
    private Integer id;
    private BloodType bloodType;
    private String chronicDiseases;
    private String allergies;
}
