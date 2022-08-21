package com.example.io_backend.model.dto.request;


import com.example.io_backend.model.enums.BloodType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CreateEmergencyRequest {
    @NotBlank
    private String description;
    @NotNull
    private Boolean breathing;
    @NotNull
    private Boolean conscious;
    private BloodType bloodType;
}
