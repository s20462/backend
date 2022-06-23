package com.example.io_backend.repository;

import com.example.io_backend.model.AccidentReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccidentReportRepository extends JpaRepository<AccidentReport, Integer> {
    List<AccidentReport> getAccidentReportByApprovedIsFalse();
    List<AccidentReport> getAccidentReportByApprovedIsTrue();
}