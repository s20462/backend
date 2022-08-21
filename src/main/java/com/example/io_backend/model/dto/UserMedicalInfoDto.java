package com.example.io_backend.model.dto;

import com.example.io_backend.model.Ambulance;
import com.example.io_backend.model.MedicalInfo;
import com.example.io_backend.model.enums.AvailabilityType;
import com.example.io_backend.model.enums.BloodType;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

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
    private Long medicalInfoId;
    private BloodType bloodType;
    private String chronicDiseases;
    private String allergies;

    @Data
    public static class AmbulanceAvailabilityDto {

        private Long id;
        private Ambulance ambulance;
        private AvailabilityType availabilityType;
        private Date dateStart;
        private Date dateEnd;
        private String details;





    }
}
