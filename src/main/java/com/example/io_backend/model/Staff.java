package com.example.io_backend.model;

import com.example.io_backend.model.enums.StaffType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Table(name = "staff")
@Entity
public class Staff {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.ORDINAL)
    private StaffType staffType;

    @OneToMany(mappedBy = "staff_id")
    private Set<DispositorDutyEntry> dispositorDutyEntries;

    @OneToMany(mappedBy = "staff_id")
    private Set<AccidentReport> accidentReports;
}
