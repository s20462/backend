package com.example.io_backend.controller;

import com.example.io_backend.model.ReportSurvey;
import com.example.io_backend.service.ReportSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/reportSurvey")
public class ReportSurveyController {
    private final ReportSurveyService reportSurveyService;

    @GetMapping("")
    public List<ReportSurvey> getAll() {
        return reportSurveyService.getSurveysReport();
    }

    @GetMapping("/{id}")
    public ReportSurvey getById(@PathVariable Integer id) {
        return reportSurveyService.getSurveysReportById(id);
    }

    @PostMapping("")
    public ReportSurvey add(@RequestBody ReportSurvey reportSurvey) {
        return reportSurveyService.addReportSurvay(reportSurvey);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody ReportSurvey reportSurvey, @PathVariable Integer id){
        reportSurveyService.updateReportSurvey(reportSurvey,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        reportSurveyService.deleteReportSurvey(id);
    }

}
