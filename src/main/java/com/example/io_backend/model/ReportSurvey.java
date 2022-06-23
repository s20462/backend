package com.example.io_backend.model;

import com.example.io_backend.model.enums.BloodType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Table(name = "report_survey")
@Entity
public class ReportSurvey {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "victim_breathing")
    private Boolean victimBreathing;

    @Column(name = "victim_conscious")
    private Boolean victimConscious;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @ElementCollection
    @Column(name = "file_url")
    private List<String> fileUrl;

    @Enumerated
    @Column(name = "blood_type")
    private BloodType bloodType;

}
