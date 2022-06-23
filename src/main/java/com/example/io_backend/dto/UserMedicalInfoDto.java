package com.example.io_backend.dto;

import com.example.io_backend.model.MedicalInfo;
import com.example.io_backend.model.enums.BloodType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserMedicalInfoDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private LocalDate birthDate;
    private String phone;
    private String bandCode;
    private MedicalInfo medicalInfo;
    private Integer medicalInfoId;
    private BloodType bloodType;
    private String chronicDiseases;
    private String allergies;
}
