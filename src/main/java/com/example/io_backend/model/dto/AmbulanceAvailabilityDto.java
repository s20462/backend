package com.example.io_backend.model.dto;

import com.example.io_backend.model.enums.AvailabilityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AmbulanceAvailabilityDto {
    @NotNull
    AvailabilityType availabilityType;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    LocalDate since;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    LocalDate to;
    @NotBlank
    String details;
}
