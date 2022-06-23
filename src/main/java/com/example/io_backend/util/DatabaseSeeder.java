package com.example.io_backend.util;

import com.example.io_backend.model.*;
import com.example.io_backend.model.dto.request.CreateStaffUserRequest;
import com.example.io_backend.model.dto.request.CreateUserRequest;
import com.example.io_backend.model.enums.*;
import com.example.io_backend.repository.*;
import com.example.io_backend.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements ApplicationRunner {
    private final UserRepository userRepository;
    private final MedicalInfoRepository medicalInfoRepository;
    private final StaffRepository staffRepository;
    private final VictimRepository victimRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final TutorialRepository tutorialRepository;
    private final ReviewRepository reviewRepository;
    private final EquipmentRepository equipmentRepository;
    private final FacilityRepository facilityRepository;
    private final ReportSurveyRepository reportSurveyRepository;
    private final AccidentReportRepository accidentReportRepository;
    private final AdditionalServicesRepository additionalServicesRepository;
    private final DispositorDutyEntryRepository dispositorDutyEntryRepository;
    private final AmbulanceAvailabilityRepository ambulanceAvailabilityRepository;
    private final EquipmentLogRepository equipmentLogRepository;
    private final LocationRepository locationRepository;

    private final KeycloakService keycloakService;

    private final int entitiesToGenerate = 10;

    @Override
    public void run(ApplicationArguments args) {
        seedUsers();
      if (args.getNonOptionArgs().contains("seed")) {
          seedDatabase();
      } else log.info("Database seeding not enabled");
    }

    private void seedUsers() {
        log.info("creating users");
        for (int i = 0; i < entitiesToGenerate; i++) {
            CreateUserRequest request = CreateUserRequest
                    .builder()
                    .firstName("test " + i)
                    .lastName("test " + i)
                    .userName("test"+i)
                    .phoneNumber("123321123")
                    .email("test"+i)
                    .password("123")
                    .birthDate(LocalDate.of(2000,01,01))
                    .build();

            keycloakService.createUser(request);
        }
        log.info("Creating staff user (dispositor)");
        CreateStaffUserRequest staffUserRequest = CreateStaffUserRequest
                .builder()
                .firstName("test")
                .lastName("test")
                .userName("dispositor")
                .email("test@dispositor.com")
                .birthDate(LocalDate.now())
                .phone("123321123")
                .staffType(StaffType.DISPOSITOR)
                .password("123")
                .build();
        keycloakService.createUser(staffUserRequest);
    }

    private void seedDatabase() {
        medicalInfoRepository.saveAll(generateMedicalInfos(entitiesToGenerate));
        //userRepository.saveAll(generateUsers(entitiesToGenerate));
        //staffRepository.saveAll(generateStaff(entitiesToGenerate));
        victimRepository.saveAll(generateVictims(entitiesToGenerate));
        ambulanceRepository.saveAll(generateAmbulances(entitiesToGenerate));
        tutorialRepository.saveAll(generateTutorials(entitiesToGenerate));
        reviewRepository.saveAll(generateReviews(entitiesToGenerate));
        equipmentRepository.saveAll(generateEquipment(entitiesToGenerate));
        facilityRepository.saveAll(generateFacilities(entitiesToGenerate));
        reportSurveyRepository.saveAll(generateSurveys(entitiesToGenerate));
        accidentReportRepository.saveAll(generateReports(entitiesToGenerate));
        additionalServicesRepository.saveAll(generateAdditionalServices(entitiesToGenerate));
        dispositorDutyEntryRepository.saveAll(generateDispositorDuties(entitiesToGenerate));
        ambulanceAvailabilityRepository.saveAll(generateAmbulanceAvailability(entitiesToGenerate));
        equipmentLogRepository.saveAll(generateEquipmentLog(entitiesToGenerate));

        log.info("Database seeding finished");
    }

    private List<MedicalInfo> generateMedicalInfos(int length) {
        List<MedicalInfo> medicalInfos = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            MedicalInfo medicalInfo = new MedicalInfo();
            medicalInfo.setAllergies(allergies.get(ThreadLocalRandom.current().nextInt(allergies.size())));
            medicalInfo.setBloodType(EnumUtils.randomValue(BloodType.class));
            medicalInfo.setChronicDiseases(chronicDiseases.get(ThreadLocalRandom.current().nextInt(chronicDiseases.size())));
            medicalInfo.setId(null);

            medicalInfos.add(medicalInfo);
        }

        return medicalInfos;
    }

    private List<User> generateUsers(int length) {
        List<User> users = new ArrayList<>();
        List<MedicalInfo> medicalInfos = medicalInfoRepository.findAll();

        for (int i = 0; i < length; i++) {
            User user = new User();
            user.setId(null);
            user.setBirthDate(LocalDate.of(2000, 1, 1));
            user.setBandCode(UUID.randomUUID().toString());
            user.setFirstName(names.get(ThreadLocalRandom.current().nextInt(names.size())).split(" ")[0]);
            user.setLastName(names.get(ThreadLocalRandom.current().nextInt(names.size())).split(" ")[1]);
            user.setMedicalInfo(medicalInfos.get(ThreadLocalRandom.current().nextInt(medicalInfos.size())));
            user.setPhone(String.format("%09d", ThreadLocalRandom.current().nextInt(1000000000)));

            users.add(user);
        }

        return users;
    }

    private List<Staff> generateStaff(int length) {
        List<Staff> staffList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Staff staff = new Staff();
            staff.setId(null);
            staff.setStaffType(EnumUtils.randomValue(StaffType.class));


            staffList.add(staff);
        }

        return staffList;
    }

    private List<Victim> generateVictims(int length) {
        List<Victim> victims = new ArrayList<>();
        List<MedicalInfo> medicalInfos = medicalInfoRepository.findAll();

        for (int i = 0; i < length; i++) {
            Victim victim = new Victim();
            victim.setId(null);
            victim.setFirstName(names.get(ThreadLocalRandom.current().nextInt(names.size())).split(" ")[0]);
            victim.setLastName(names.get(ThreadLocalRandom.current().nextInt(names.size())).split(" ")[1]);
            victim.setMedicalInfo(medicalInfos.get(ThreadLocalRandom.current().nextInt(medicalInfos.size())));
            victim.setDocumentId(UUID.randomUUID().toString());
            victim.setDocumentName(documentNames.get(ThreadLocalRandom.current().nextInt(documentNames.size())));

            victims.add(victim);
        }

        return victims;
    }

    private List<Ambulance> generateAmbulances(int length) {
        List<Ambulance> ambulances = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Ambulance ambulance = new Ambulance();
            ambulance.setAmbulanceKind(EnumUtils.randomValue(AmbulanceKind.class));
            ambulance.setAmbulanceType(EnumUtils.randomValue(AmbulanceType.class));
            ambulance.setId(null);
            ambulance.setPeopleCapacity(5);
            ambulance.setPlates(numberPlates.get(ThreadLocalRandom.current().nextInt(numberPlates.size())));
            ambulance.setFuelCapacity(400000);

            ambulances.add(ambulance);
        }

        return ambulances;
    }

    private List<Tutorial> generateTutorials(int length) {
        List<Tutorial> tutorials = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Tutorial tutorial = new Tutorial();
            tutorial.setTutorialKind(EnumUtils.randomValue(TutorialKind.class));
            tutorial.setId(null);
            tutorial.setAverage(Double.valueOf(String.format(Locale.ROOT,"%.2f", Math.random())));
            tutorial.setName("Tutorial " + i);

            tutorials.add(tutorial);
        }

        return tutorials;
    }

    private List<Review> generateReviews(int length) {
        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Review review = new Review();
            review.setContent("lorem ipsum");
            review.setId(null);
            review.setRating(ThreadLocalRandom.current().nextInt(1,11));

            reviews.add(review);
        }

        return reviews;
    }

    private List<Equipment> generateEquipment(int length) {
        List<Equipment> equipments = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Equipment eq = new Equipment();
            eq.setId(null);
            eq.setName("EQ" + i);

            equipments.add(eq);
        }

        return equipments;
    }

    private List<Facility> generateFacilities(int length) {
        List<Facility> facilities = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Facility f = new Facility();
            f.setId(null);
            f.setName("Facility " + i);
            f.setFacilityType(EnumUtils.randomValue(FacilityType.class));
            f.setHospitalType(EnumUtils.randomValue(HospitalType.class));
            f.setMaximumBeds(ThreadLocalRandom.current().nextInt(10, 101));

            facilities.add(f);
        }

        return facilities;
    }

    private List<ReportSurvey> generateSurveys(int length) {
        List<ReportSurvey> reportSurveys = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            ReportSurvey rp = new ReportSurvey();
            rp.setBloodType(EnumUtils.randomValue(BloodType.class));
            rp.setId(null);
            rp.setDate(LocalDate.now());
            rp.setDescription("Lorem ipsum");
            rp.setVictimBreathing(ThreadLocalRandom.current().nextBoolean());
            rp.setVictimConscious(ThreadLocalRandom.current().nextBoolean());
            rp.setFileUrl(List.of("/var/surveys/survey"+i+".csv"));

            reportSurveys.add(rp);
        }

        return reportSurveys;
    }

    private List<AccidentReport> generateReports(int length) {
        List<AccidentReport> accidentReports = new ArrayList<>();
        List<Ambulance> ambulances = ambulanceRepository.findAll();
        List<ReportSurvey> reportSurveys = reportSurveyRepository.findAll();
        List<Staff> staffList = staffRepository.findAll();
        List<User> users = userRepository.findAll();

        for (int i = 0; i < length; i++) {
            AccidentReport a = new AccidentReport();
            a.setId(null);
            a.setAmbulances(
                    ambulances.stream()
                            .limit(ThreadLocalRandom.current().nextInt(ambulances.size() - (entitiesToGenerate / 2)))
                            .collect(Collectors.toSet()));
            a.setDate(LocalDate.now());
            a.setClosed(ThreadLocalRandom.current().nextBoolean());
            a.setReportSurvey(reportSurveys.get(ThreadLocalRandom.current().nextInt(reportSurveys.size())));
            a.setStaff(staffList.get(ThreadLocalRandom.current().nextInt(staffList.size())));
            a.setDangerRating((short) ThreadLocalRandom.current().nextInt(128));
            a.setUser(users.get(ThreadLocalRandom.current().nextInt(users.size())));

            accidentReports.add(a);
        }

        return accidentReports;
    }

    private List<AdditionalServices> generateAdditionalServices(int length) {
        List<AdditionalServices> additionalServices = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            AdditionalServices as = new AdditionalServices();
            as.setId(null);
            as.setAdditionalServicesType(EnumUtils.randomValue(AdditionalServicesType.class));
            as.setJustification("lorem ipsum");
            as.setDate(LocalDate.now());

            additionalServices.add(as);
        }

        return additionalServices;
    }

    private List<AmbulanceAvailability> generateAmbulanceAvailability(int length) {
        List<AmbulanceAvailability> ambulanceAvailabilityList = new ArrayList<>();
        List<Ambulance> ambulances = ambulanceRepository.findAll();

        for (int i = 0; i < length; i++) {
            AmbulanceAvailability ambulanceAvailability = new AmbulanceAvailability();
            ambulanceAvailability.setAmbulance(ambulances.get(ThreadLocalRandom.current().nextInt(ambulances.size())));
            ambulanceAvailability.setId(null);
            ambulanceAvailability.setAvailabilityType(EnumUtils.randomValue(AvailabilityType.class));
            ambulanceAvailability.setDetails("lorem ipsum " + i);
            ambulanceAvailability.setDateStart(LocalDate.now());
            ambulanceAvailability.setDateEnd(LocalDate.now());

            ambulanceAvailabilityList.add(ambulanceAvailability);
        }

        return ambulanceAvailabilityList;
    }

    private List<DispositorDutyEntry> generateDispositorDuties(int length) {
        List<DispositorDutyEntry> dispositorDutyEntries = new ArrayList<>();
        List<Staff> staff = staffRepository.findAll();

        for (int i = 0; i < length; i++) {
            DispositorDutyEntry d = new DispositorDutyEntry();
            d.setId(null);
            d.setDutyStart((int) Instant.now().getEpochSecond());
            d.setDutyEnd((int) (Instant.now().getEpochSecond() + (60 * 60 * 8)));
            d.setComment("comment " + i);
            d.setStaff(staff.get(ThreadLocalRandom.current().nextInt(staff.size())));

            dispositorDutyEntries.add(d);
        }

        return dispositorDutyEntries;
    }

    private List<EquipmentLog> generateEquipmentLog(int length) {
        List<EquipmentLog> equipmentLogs = new ArrayList<>();
        List<Ambulance> ambulances = ambulanceRepository.findAll();
        List<Equipment> equipmentList = equipmentRepository.findAll();

        for (int i = 0; i < length; i++) {
            Double staring = ThreadLocalRandom.current().nextDouble(1,51);
            EquipmentLog e = new EquipmentLog();
            e.setId(null);
            e.setDateStart(LocalDate.now());
            e.setDateEnd(LocalDate.now());
            e.setStartingAmount(staring);
            e.setCurrentAmount(staring - ThreadLocalRandom.current().nextDouble(0, 51));
            e.setAmbulance(ambulances.get(ThreadLocalRandom.current().nextInt(ambulances.size())));
            e.setEquipment(equipmentList.get(ThreadLocalRandom.current().nextInt(equipmentList.size())));

            equipmentLogs.add(e);
        }

        return equipmentLogs;
    }

    private final List<String> chronicDiseases = List.of(
            "ASTMA",
            "CUKRZYCA",
            "NADCIŚNIENIE"
    );

    private final List<String> allergies = List.of(
            "ORZECHY",
            "PYŁKI",
            "TRUSKAWKI",
            "ROZTOCZA"
    );

    private final List<String> emails = List.of(
            "bockelboy@sbcglobal.net",
            "ubergeeb@att.net",
            "webteam@msn.com",
            "hermes@outlook.com",
            "themer@hotmail.com",
            "oster@yahoo.com",
            "lbecchi@msn.com",
            "koudas@mac.com",
            "mstrout@yahoo.ca",
            "liedra@yahoo.com",
            "email@example.com",
            "test@test.com",
            "abcdef@gmail.com"
    );

    private final List<String> names = List.of(
            "Pamela Rikki",
            "Wilburn Jolene",
            "Addie Peter",
            "Alex Geneva",
            "Odetta Leon",
            "Pamela Rikki",
            "Wilburn Jolene",
            "Addie Peter",
            "Odetta Leon",
            "Isabella Brielle",
            "Gardenia Matt",
            "Camilla Vince",
            "Ness Petronel",
            "Jacob Neely"
    );

    private final List<String> documentNames = List.of(
            "DOKUMENT1",
            "DOKUMENT2",
            "DOKUMENT3",
            "DOKUMENT4",
            "DOKUMENT5",
            "DOKUMENT6"
    );

    private final List<String> numberPlates = List.of(
            "KWA4862",
            "ZKA0443",
            "RST5405",
            "TKI8453",
            "BKL5392",
            "WPZ8939",
            "WSK4065",
            "TLW7107",
            "WM71020",
            "GA47053",
            "OOL6118",
            "TKN1306",
            "WLS8512"
    );

}
