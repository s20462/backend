package com.example.io_backend.service;

import com.example.io_backend.model.Ambulance;
import com.example.io_backend.model.AmbulanceAvailability;
import com.example.io_backend.model.Equipment;
import com.example.io_backend.model.EquipmentLog;
import com.example.io_backend.model.dto.AmbulanceDto;
import com.example.io_backend.model.dto.response.AmbulanceResponse;
import com.example.io_backend.model.enums.AmbulanceKind;
import com.example.io_backend.model.enums.AmbulanceType;
import com.example.io_backend.model.enums.AvailabilityType;
import com.example.io_backend.repository.AmbulanceAvailabilityRepository;
import com.example.io_backend.repository.AmbulanceRepository;
import com.example.io_backend.repository.EquipmentLogRepository;
import com.example.io_backend.repository.EquipmentRepository;
import com.example.io_backend.util.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AmbulanceServiceTest {

    private final AmbulanceRepository ambulanceRepository = mock(AmbulanceRepository.class);
    private final AmbulanceAvailabilityRepository availabilityRepository = mock(AmbulanceAvailabilityRepository.class);
    private final EquipmentRepository equipmentRepository = mock(EquipmentRepository.class);
    private final EquipmentLogRepository equipmentLogRepository = mock(EquipmentLogRepository.class);
    private final AmbulanceService ambulanceService = new AmbulanceService(ambulanceRepository, availabilityRepository, equipmentRepository ,equipmentLogRepository);

    @Test
    void assignEquipment() {

        Equipment eq = new Equipment(1, "eq1", null);

        Ambulance ambulance = Ambulance
                .builder()
                .id(1)
                .ambulanceKind(AmbulanceKind.N)
                .ambulanceType(AmbulanceType.A)
                .fuelCapacity(10)
                .peopleCapacity(9)
                .plates("plates")
                .build();

        EquipmentLog eqlog1 = EquipmentLog
                .builder()
                .id(1)
                .ambulance(ambulance)
                .equipment(eq)
                .currentAmount(10d)
                .startingAmount(10d)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(1))
                .measurement("m")
                .build();

        EquipmentLog eqlog2 = EquipmentLog
                .builder()
                .id(1)
                .ambulance(ambulance)

                .equipment(new Equipment(2, "eq2", null))
                .currentAmount(10d)
                .startingAmount(10d)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(1))
                .measurement("m")
                .build();

        when(equipmentRepository.findById(any(Integer.class))).thenReturn(Optional.of(eq));
        when(ambulanceRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(ambulance));
        when(equipmentLogRepository.findAll()).thenReturn(List.of(eqlog1, eqlog2));
        when(equipmentLogRepository.save(any(EquipmentLog.class))).thenAnswer(x -> x.getArguments()[0]);

        var result = ambulanceService.assignEquipment(1, 1);

        assertEquals(eqlog1.getEquipment(), result.getEquipment());
        assertEquals(eqlog1.getAmbulance(), result.getAmbulance());
        verify(equipmentLogRepository, times(1)).save(any(EquipmentLog.class));

    }

    @Test
    void setStatus() {
        Ambulance ambulance = Ambulance
                .builder()
                .id(1)
                .plates("plates")
                .peopleCapacity(9)
                .fuelCapacity(10)
                .ambulanceType(AmbulanceType.A)
                .ambulanceKind(AmbulanceKind.N)
                .build();

        when(ambulanceRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(ambulance));
        when(availabilityRepository.save(any(AmbulanceAvailability.class))).thenAnswer(x -> x.getArguments()[0]);

    }

    @Test
    void getAmbulancesShouldReturn() {
        when(ambulanceRepository.findAll()).thenReturn(getAmbulances());

        var ambulances = ambulanceService.getAmbulances();

        assertEquals(10, ambulances.size());
        assertNotNull(ambulances);
    }

    @Test
    void testGetAmbulancesByKindShouldReturn() {
        AmbulanceKind kind = AmbulanceKind.N;

        var expected = List.of(
                Ambulance.builder()
                .peopleCapacity(10)
                .id(1)
                .ambulanceType(AmbulanceType.A)
                .ambulanceKind(kind)
                .fuelCapacity(10)
                .plates("plates")
                .build());

        when(ambulanceRepository.getAllByAmbulanceKind(kind)).thenReturn(expected);

        var result = ambulanceService.getAmbulances(kind);

        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0).getAmbulanceKind(), AmbulanceKind.N);

    }

    @Test
    void testGetAmbulancesByTypeShouldReturn() {
        AmbulanceType type = AmbulanceType.A;

        var expected = List.of(
                Ambulance.builder()
                        .peopleCapacity(10)
                        .id(1)
                        .ambulanceType(type)
                        .ambulanceKind(AmbulanceKind.N)
                        .fuelCapacity(10)
                        .plates("plates")
                        .build());

        when(ambulanceRepository.getAllByAmbulanceType(type)).thenReturn(expected);

        var result = ambulanceService.getAmbulances(type);

        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0).getAmbulanceType(), AmbulanceType.A);
    }

    @Test
    void getAmbulancesByNumberOfSeatsShouldReturn() {
        int numberOfSeats = 5;

        var expected = List.of(
                Ambulance
                        .builder()
                        .id(1)
                        .plates("plates")
                        .fuelCapacity(1)
                        .ambulanceKind(AmbulanceKind.N)
                        .ambulanceType(AmbulanceType.A)
                        .peopleCapacity(numberOfSeats)
                        .build()
        );

        when(ambulanceRepository.getAllByPeopleCapacity(numberOfSeats)).thenReturn(expected);

        var result = ambulanceService.getAmbulances(numberOfSeats);

        assertEquals(expected.size(), result.size());
        assertEquals(5, result.get(0).getNumberOfSeats());
    }

    @Test
    void addAmbulanceShouldAdd() {
        AmbulanceDto newAmbulance = AmbulanceDto
                .builder()
                .fuelTankCapacity(10)
                .kind(AmbulanceKind.N)
                .type(AmbulanceType.A)
                .licensePlates("plates")
                .numberOfSeats(10)
                .build();

        AmbulanceResponse expected = AmbulanceResponse
                .builder()
                .numberOfSeats(10)
                .kind(AmbulanceKind.N)
                .type(AmbulanceType.A)
                .licensePlates("plates")
                .fuelTankCapacity(10)
                .build();

        when(ambulanceRepository.save(any(Ambulance.class))).thenAnswer(x -> x.getArguments()[0]);

        var result = ambulanceService.addAmbulance(newAmbulance);

        assertNotNull(result);
        assertEquals(expected.getKind(), result.getKind());
        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getFuelTankCapacity(), result.getFuelTankCapacity());
        assertEquals(expected.getLicensePlates(), result.getLicensePlates());
    }

    @Test
    void getAvailableShouldReturn() {
        Ambulance ambulance1 = Ambulance
                .builder()
                .ambulanceKind(AmbulanceKind.S)
                .ambulanceType(AmbulanceType.A)
                .plates("plates")
                .peopleCapacity(10)
                .fuelCapacity(10)
                .id(1)
                .build();

        Ambulance ambulance2 = Ambulance
                .builder()
                .ambulanceKind(AmbulanceKind.S)
                .ambulanceType(AmbulanceType.A)
                .plates("plates")
                .peopleCapacity(10)
                .fuelCapacity(10)
                .id(2)
                .build();

        AmbulanceAvailability availability1 = AmbulanceAvailability
                .builder()
                .ambulance(ambulance1)
                .availabilityType(AvailabilityType.AVAILABLE)
                .dateStart(LocalDate.of(2022, 4, 21))
                .dateEnd(LocalDate.of(2022, 4, 22))
                .details("details")
                .id(1)
                .build();

        AmbulanceAvailability availability2 = AmbulanceAvailability
                .builder()
                .ambulance(ambulance2)
                .availabilityType(AvailabilityType.ON_ACTION)
                .dateStart(LocalDate.of(2022, 4, 21))
                .dateEnd(LocalDate.of(2022, 4, 22))
                .details("details")
                .id(1)
                .build();

        var expected = AmbulanceResponse.of(availability1.getAmbulance());


        when(ambulanceRepository.findAll()).thenReturn(List.of(ambulance1, ambulance2));
        when(availabilityRepository.findAll()).thenReturn(List.of(availability1, availability2));

        var result = ambulanceService.getAvailable();

        assertEquals(1, result.size());
        assertNull(result.get(0).getAvailability());
        assertEquals("plates", result.get(0).getLicensePlates());
    }

    @Test
    void updateAmbulance() {
    }

    @Test
    void deleteAmbulance() {
    }


    private List<Ambulance> getAmbulances() {
        List<Ambulance> ambulances = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ambulances.add(
                    Ambulance
                            .builder()
                            .plates(getRandomStringOfLength(10))
                            .ambulanceType(EnumUtils.randomValue(AmbulanceType.class))
                            .ambulanceKind(EnumUtils.randomValue(AmbulanceKind.class))
                            .fuelCapacity(10)
                            .id(i)
                            .peopleCapacity(5)
                            .build()
            );
        }

        return ambulances;
    }

    private String getRandomStringOfLength(int length) {
        String alphabet = "1234567890qwertyuiopasdfghjklzxcvbnm";
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append(alphabet.charAt(ThreadLocalRandom.current().nextInt(alphabet.length())));
        }

        return builder.toString();
    }
}