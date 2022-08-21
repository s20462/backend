package com.example.io_backend.model;

import com.example.io_backend.model.enums.BloodType;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "chronic_diseases")
    private String chronicDiseases;

    @Column(name = "allergies")
    private String allergies;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
