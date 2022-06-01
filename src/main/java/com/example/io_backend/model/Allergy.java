package com.example.io_backend.model;

import com.example.io_backend.model.enums.AllergyType;
import com.example.io_backend.model.enums.AmbulanceType;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "allergy")
@Entity
public class Allergy {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @Column(name = "allergy_type")
    private AllergyType allergyType;

    @Column(name = "allergy_name")
    private String allergyName;

    @Column(name = "additionalInfo")
    private String additionalInfo;
}
