package com.example.io_backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "victim")
@Entity
public class Victim {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_id")
    private String documentId;

    @ManyToOne
    @JoinColumn(name = "medical_info_id")
    private MedicalInfo medicalInfo;

}
