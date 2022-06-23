package com.example.io_backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
@Entity
public class User {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "band_code")
    private String bandCode;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "medical_info_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MedicalInfo medicalInfo;

    @OneToMany(mappedBy = "user_id")
    private Set<AccidentReport> accidentReports;
}
