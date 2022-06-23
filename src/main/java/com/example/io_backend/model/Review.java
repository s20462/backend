package com.example.io_backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "review")
@Entity
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "content")
    private String content;

}
