package com.example.io_backend.service;

import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.ReportSurvey;
import com.example.io_backend.repository.ReportSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportSurveyService {

    private final ReportSurveyRepository reportSurveyRepository;

    public List<ReportSurvey> getSurveysReport() {
        return reportSurveyRepository.findAll();
    }

    public ReportSurvey getSurveysReportById(Integer id){
        return reportSurveyRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that id"));
    }

    public ReportSurvey addReportSurvay(ReportSurvey reportSurvey){
        return reportSurveyRepository.save(reportSurvey);
    }
    public void updateReportSurvey(ReportSurvey reportSurvey, Integer id) {
        var r = reportSurveyRepository.findById(id).orElseThrow(() -> new NotFoundException("Report survey Not found "));
        r.setId(reportSurvey.getId());
        r.setDate(reportSurvey.getDate());
        r.setBloodType(reportSurvey.getBloodType());
        r.setDescription(reportSurvey.getDescription());
        r.setVictimBreathing(reportSurvey.getVictimBreathing());
        r.setVictimConscious(reportSurvey.getVictimConscious());
        r.setDescription(reportSurvey.getDescription());

        reportSurveyRepository.save(r);
    }

    public void deleteReportSurvey(Integer id){
        var r = reportSurveyRepository.findById(id).orElseThrow(() -> new NotFoundException("Report survey Not found "));
        reportSurveyRepository.delete(r);
    }


}
