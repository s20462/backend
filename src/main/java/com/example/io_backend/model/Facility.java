package com.example.io_backend.model;

import com.example.io_backend.model.enums.FacilityType;
import com.example.io_backend.model.enums.HospitalType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "facility")
@Entity
public class Facility {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "hospital_type")
    private HospitalType hospitalType;

    @Column(name = "facility_type")
    private FacilityType facilityType;

    @ElementCollection
    @Column(name = "set_")
    @CollectionTable(name = "facility_set", joinColumns = @JoinColumn(name = "owner_id"))
    private Set<String> set = new LinkedHashSet<>();

    @Column(name = "maximum_beds")
    private Integer maximumBeds;

}
