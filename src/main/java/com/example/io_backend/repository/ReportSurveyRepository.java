package com.example.io_backend.repository;

import com.example.io_backend.model.ReportSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportSurveyRepository extends JpaRepository<ReportSurvey, Integer> {
}