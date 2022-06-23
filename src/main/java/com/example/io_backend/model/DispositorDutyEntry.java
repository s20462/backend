package com.example.io_backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "dispositor_duty_entries")
@Entity
public class DispositorDutyEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "duty_start")
    private Integer dutyStart;

    @Column(name = "duty_end")
    private Integer dutyEnd;

    @Column(name = "comment")
    String comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

}
