package com.example.io_backend.model;

import com.example.io_backend.model.enums.BloodType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "medical_info")
@Entity
public class MedicalInfo {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;

    @ElementCollection
    @Column(name = "chronic_diseases")
    private List<String> chronicDiseases;

    @ManyToMany
    @JoinTable(name = "allergies",
            joinColumns = @JoinColumn(name = "medical_info_id"),
            inverseJoinColumns = @JoinColumn(name = "allergies_id"))
    private List<Allergy> allergies;

}
