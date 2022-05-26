package com.example.io_backend.model.dto.response;

import com.example.io_backend.model.dto.UserDto;
import com.example.io_backend.model.enums.BloodType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmergencyResponse {
    private Short dangerRating;
    private String description;
    private Boolean breathing;
    private Boolean conscious;
    private BloodType bloodType;
    private LocalDate date;
    private Long id;
    private UserDto user;
}
