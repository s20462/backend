package com.example.io_backend.model;

import com.example.io_backend.model.enums.AdditionalServicesType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Table(name = "additional_services")
@Entity
public class AdditionalServices {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(name = "additional_services_type")
    private AdditionalServicesType additionalServicesType;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "justification")
    private String justification;

}
