package com.example.io_backend.model.dto;

import com.example.io_backend.model.Ambulance;
import com.example.io_backend.model.ReportSurvey;
import com.example.io_backend.model.Staff;
import com.example.io_backend.model.User;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class AccidentReportDto {
    private Long id;
    private Short dangerRating;
    private Date date;
    private Boolean closed;
    private Staff staff;
    private User user;
    private Set<Ambulance> ambulances;
    private ReportSurvey reportSurvey;
}
