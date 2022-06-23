package com.example.io_backend.service;

import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.Ambulance;
import com.example.io_backend.model.AmbulanceAvailability;
import com.example.io_backend.model.Equipment;
import com.example.io_backend.model.EquipmentLog;
import com.example.io_backend.model.dto.AmbulanceAvailabilityDto;
import com.example.io_backend.model.dto.AmbulanceDto;
import com.example.io_backend.model.dto.response.AmbulanceResponse;
import com.example.io_backend.model.dto.response.EquipmentLogResponse;
import com.example.io_backend.model.dto.response.EquipmentResponse;
import com.example.io_backend.model.enums.AmbulanceKind;
import com.example.io_backend.model.enums.AmbulanceType;
import com.example.io_backend.model.enums.AvailabilityType;
import com.example.io_backend.repository.AmbulanceAvailabilityRepository;
import com.example.io_backend.repository.AmbulanceRepository;
import com.example.io_backend.repository.EquipmentLogRepository;
import com.example.io_backend.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AmbulanceService {
    private final AmbulanceRepository ambulanceRepository;
    private final AmbulanceAvailabilityRepository availabilityRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentLogRepository equipmentLogRepository;

   @Transactional
    public EquipmentLogResponse assignEquipment(Integer ambulanceId, Integer equipmentId) { // unidirectional relations ????
        Equipment eq = equipmentRepository.findById(equipmentId).orElseThrow(() -> new NotFoundException("Equipment with id: " + equipmentId + " couldn't be found"));
        Ambulance ambulance = ambulanceRepository.findById(ambulanceId).orElseThrow(() -> new NotFoundException("Ambulance with id: " + ambulanceId + " couldn't be found"));
        List<EquipmentLog> equipmentLogList = equipmentLogRepository.findAll()
                .stream()
                .filter(x -> x.getEquipment().equals(eq) && x.getAmbulance().equals(ambulance))
                .toList();

        if (equipmentLogList.size() == 0) {
            throw new NotFoundException("No log for that ambulance and equipment");
        }

        EquipmentLog current = equipmentLogList.get(0);
        current.setEquipment(eq);

        current = equipmentLogRepository.save(current);

        return mapToResponse(current);
    }

    public void setStatus(Integer ambulanceId, AmbulanceAvailabilityDto availabilityDto) {
        Ambulance ambulance = ambulanceRepository.findById(ambulanceId).orElseThrow(NotFoundException::new);
        AmbulanceAvailability availability = AmbulanceAvailability
                .builder()
                .ambulance(ambulance)
                .availabilityType(availabilityDto.getAvailabilityType())
                .dateStart(availabilityDto.getSince())
                .dateEnd(availabilityDto.getTo())
                .details(availabilityDto.getDetails())
                .build();

        availabilityRepository.save(availability);
    }

    public List<AmbulanceResponse> getAmbulances() {
        List<Ambulance> ambulances = ambulanceRepository.findAll();

        return ambulances.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<AmbulanceResponse> getAmbulances(AmbulanceKind ambulanceKind) {
        List<Ambulance> ambulances = ambulanceRepository.getAllByAmbulanceKind(ambulanceKind);

        return ambulances.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<AmbulanceResponse> getAmbulances(AmbulanceType ambulanceType) {
        List<Ambulance> ambulances = ambulanceRepository.getAllByAmbulanceType(ambulanceType);

        return ambulances.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<AmbulanceResponse> getAmbulances(Integer numberOfSeats) {
        List<Ambulance> ambulances = ambulanceRepository.getAllByPeopleCapacity(numberOfSeats);

        return ambulances.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public AmbulanceResponse getAmbulance(String licensePlate) {
        Optional<Ambulance> ambulanceOptional = ambulanceRepository.findAmbulanceByPlates(licensePlate);

        return ambulanceOptional.map(this::mapToResponse).orElse(null);
    }

    public AmbulanceResponse getAmbulance(Integer id){
        return ambulanceRepository.findById(id).map(this::mapToResponse).orElse(null);
    }

    public AmbulanceResponse addAmbulance(AmbulanceDto ambulance)  {
        Ambulance a = mapToEntity(ambulance);

        a = ambulanceRepository.save(a);

        return mapToResponse(a);
    }

    public List<AmbulanceResponse> getAvailable() {
        Set<Ambulance> ambulanceSet = new HashSet<>(ambulanceRepository.findAll());
        List<AmbulanceAvailability> availabilityList = availabilityRepository.findAll();

        var availableAmbulances = availabilityList.stream()
                .filter(x -> ambulanceSet.contains(x.getAmbulance()))
                .filter(x -> x.getAvailabilityType().equals(AvailabilityType.AVAILABLE))
                .map(AmbulanceAvailability::getAmbulance)
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        availableAmbulances.forEach(x -> x.setAvailability(null)); // we ask for available ambulances not if that particular ambulance is available, so we set it to null to reduce redundancy

        return availableAmbulances;
    }

    public List<Ambulance> getAvailableAmbulances() {
        Set<Ambulance> ambulanceSet = new HashSet<>(ambulanceRepository.findAll());
        List<AmbulanceAvailability> availabilityList = availabilityRepository.findAll();

        return availabilityList.stream()
                .filter(x -> ambulanceSet.contains(x.getAmbulance()))
                .filter(x -> x.getAvailabilityType().equals(AvailabilityType.AVAILABLE))
                .map(AmbulanceAvailability::getAmbulance)
                .collect(Collectors.toList());
    }

    public boolean isAvailable(Integer id) {
        List<Ambulance> ambulances = getAvailableAmbulances();
        Optional<Integer> availableId = ambulances.stream().map(Ambulance::getId).filter(x -> x.equals(id)).findFirst();

        return availableId.isPresent();
    }

    public AmbulanceResponse updateAmbulance(AmbulanceDto ambulance, Integer id) {
        var a = ambulanceRepository.findById(id).orElseThrow(()-> new NotFoundException("No record with that ID"));

        a.setAmbulanceKind(ambulance.getKind());
        a.setAmbulanceType(ambulance.getType());
        a.setFuelCapacity(ambulance.getFuelTankCapacity());
        a.setPlates(ambulance.getLicensePlates());
        a.setPeopleCapacity(ambulance.getNumberOfSeats());

        a = ambulanceRepository.save(a);

        return mapToResponse(a);
    }

    public void deleteAmbulance(Integer id) {
        var a = ambulanceRepository.findById(id).orElseThrow(()-> new NotFoundException("No record with that ID"));

        ambulanceRepository.delete(a);
    }

    private AmbulanceResponse mapToResponse(Ambulance a) {
        var availability = availabilityRepository.findByAmbulanceId(a.getId()).orElse(null);

        return AmbulanceResponse
                .builder()
                .numberOfSeats(a.getPeopleCapacity())
                .licensePlates(a.getPlates())
                .fuelTankCapacity(a.getFuelCapacity())
                .kind(a.getAmbulanceKind())
                .type(a.getAmbulanceType())
                .availability(availability)
                .build();
    }

    private Ambulance mapToEntity(AmbulanceDto dto) {
        return Ambulance
                .builder()
                .ambulanceKind(dto.getKind())
                .ambulanceType(dto.getType())
                .id(null)
                .peopleCapacity(dto.getNumberOfSeats())
                .fuelCapacity(dto.getFuelTankCapacity())
                .plates(dto.getLicensePlates())
                .build();
    }

    private EquipmentLogResponse mapToResponse(EquipmentLog equipment) {
        return EquipmentLogResponse
                .builder()
                .measurement(equipment.getMeasurement())
                .equipment(equipment.getEquipment())
                .startingAmount(equipment.getStartingAmount())
                .currentAmount(equipment.getCurrentAmount())
                .dateStart(equipment.getDateStart())
                .dateEnd(equipment.getDateEnd())
                .ambulance(equipment.getAmbulance())
                .build();
    }
}
