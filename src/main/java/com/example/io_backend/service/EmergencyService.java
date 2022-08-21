package com.example.io_backend.service;

import com.example.io_backend.model.dto.UserDto;
import com.example.io_backend.exception.BadRequestException;
import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.*;
import com.example.io_backend.model.dto.request.ApproveEmergencyRequest;
import com.example.io_backend.model.dto.request.CreateEmergencyRequest;
import com.example.io_backend.model.dto.response.EmergencyResponse;
import com.example.io_backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmergencyService {
    private final AccidentReportRepository accidentRepository;
    private final ReportSurveyRepository surveyRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final AmbulanceService ambulanceService;

    @Transactional
    public EmergencyResponse newEmergency(CreateEmergencyRequest emergencyRequest) {
        ReportSurvey survey = getReportSurvey(emergencyRequest);

        survey =  surveyRepository.save(survey);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.error(auth.getName());
        User currentUser = userRepository.getById(auth.getName());

        AccidentReport report = new AccidentReport();
        report.setClosed(false);
        report.setApproved(false);
        report.setDate(LocalDate.now());
        report.setReportSurvey(survey);
        report.setId(null);
        report.setStaff(null);
        report.setUser(currentUser);

        report = accidentRepository.save(report);

        EmergencyResponse response = new EmergencyResponse();
        response.setDescription(survey.getDescription());
        response.setBreathing(survey.getVictimBreathing());
        response.setConscious(survey.getVictimConscious());
        response.setBloodType(survey.getBloodType());
        response.setDate(report.getDate());
        response.setId(report.getId());
        response.setUser(UserDto
                .builder()
                        .firstName(currentUser.getFirstName())
                        .lastName(currentUser.getLastName())
                        .phone(currentUser.getPhone())
                .build());

        return response;
    }

    public void approveEmergency(ApproveEmergencyRequest request, Integer id) {
        List<Ambulance> ambulances = new ArrayList<>();
        for (Integer ambulanceId : request.getAmbulanceIds()) {
            if (!ambulanceService.isAvailable(ambulanceId)) {
                throw new BadRequestException("Id " + ambulanceId + " is not available!");
            }

            ambulances.add(ambulanceRepository.findById(ambulanceId).orElseThrow(() -> new NotFoundException("Ambulance with id " + ambulanceId + " does not exists!")));
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Staff loggedStaff = staffRepository.getByUser_Id(auth.getName());

        AccidentReport report = accidentRepository.findById(id.longValue()).orElseThrow(() -> new NotFoundException("Cannot find accident report with that id"));
        report.setAmbulances(new HashSet<>(ambulances));
        report.setApproved(true);
        report.setDangerRating(request.getDangerRating());
        report.setStaff(loggedStaff);

        report = accidentRepository.save(report);
    }

    public List<EmergencyResponse> getUnapproved() {
        List<AccidentReport> accidents = accidentRepository.getAccidentReportByApprovedIsFalse();
        return getEmergencyResponses(accidents);
    }

    public List<EmergencyResponse> getApproved() {
        List<AccidentReport> accidents = accidentRepository.getAccidentReportByApprovedIsTrue();
        return getEmergencyResponses(accidents);
    }

    private List<EmergencyResponse> getEmergencyResponses(List<AccidentReport> accidents) {
        List<EmergencyResponse> emergencyResponses = new ArrayList<>();

        for (AccidentReport report : accidents) {
            EmergencyResponse response = new EmergencyResponse();
            response.setId(report.getId());
            response.setDescription(report.getReportSurvey().getDescription());
            response.setDate(report.getDate());
            response.setUser(UserDto.of(report.getUser()));
            response.setConscious(report.getReportSurvey().getVictimConscious());
            response.setBreathing(report.getReportSurvey().getVictimBreathing());
            response.setBloodType(report.getReportSurvey().getBloodType());

            emergencyResponses.add(response);
        }

        return emergencyResponses;
    }

    private ReportSurvey getReportSurvey(CreateEmergencyRequest emergencyRequest) {
        ReportSurvey survey = new ReportSurvey();
        survey.setDate(LocalDate.now());
        survey.setDescription(emergencyRequest.getDescription());
        survey.setBloodType(emergencyRequest.getBloodType());
        survey.setVictimBreathing(emergencyRequest.getBreathing());
        survey.setVictimConscious(emergencyRequest.getConscious());
        return survey;
    }
}
