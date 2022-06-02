package com.example.io_backend.model;

import com.example.io_backend.model.enums.TutorialKind;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "tutorial")
@Entity
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated
    @Column(name = "tutorial_kind")
    private TutorialKind tutorialKind;

    @Column(name = "average")
    private Double average;

}
