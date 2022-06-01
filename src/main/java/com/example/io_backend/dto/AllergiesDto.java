package com.example.io_backend.dto;

import com.example.io_backend.model.enums.AllergyType;
import lombok.Data;

@Data
public class AllergiesDto {

    private Long id;
    private AllergyType allergyType;
    private String allergyName;
    private String additionalInfo;


}
