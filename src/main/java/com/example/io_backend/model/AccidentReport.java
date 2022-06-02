package com.example.io_backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "accident_report")
@Entity
public class AccidentReport {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "danger_rating")
    private Short dangerRating;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "closed")
    private Boolean closed;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "accident_report_ambulances",
            joinColumns = @JoinColumn(name = "accident_report_id"),
            inverseJoinColumns = @JoinColumn(name = "ambulances_id"))
    private Set<Ambulance> ambulances = new LinkedHashSet<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "report_survey_id")
    private ReportSurvey reportSurvey;

}
