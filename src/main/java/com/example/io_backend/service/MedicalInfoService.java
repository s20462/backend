package com.example.io_backend.service;

import com.example.io_backend.model.dto.MedicalInfoDto;
import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.MedicalInfo;
import com.example.io_backend.model.enums.BloodType;
import com.example.io_backend.repository.MedicalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalInfoService {

    private final MedicalInfoRepository medicalInfoRepository;
    private final ModelMapper modelMapper;

    public List<MedicalInfo> getMedicalInfo() {
        return medicalInfoRepository.findAll();
    }

    public MedicalInfo getMedicalInfoById(Long id) {
        return medicalInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that id "));
    }

    public MedicalInfo addMedicalInfo(MedicalInfo medicalInfo) {
        return medicalInfoRepository.save(medicalInfo);
    }

    public void updateMedicalInfo(MedicalInfo medicalInfo, Long id){
        var m = medicalInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("Medical Info not found"));
        m.setId(medicalInfo.getId());
        m.setAllergies(medicalInfo.getAllergies());
        m.setBloodType(medicalInfo.getBloodType());
        m.setChronicDiseases(medicalInfo.getChronicDiseases());

        medicalInfoRepository.save(m);
    }

    public void deleteMedicalInfo(Long id) {
        var m = medicalInfoRepository.findById(id).orElseThrow(() -> new NotFoundException("Medical Info not found "));

        medicalInfoRepository.delete(m);
    }

    public MedicalInfoDto updateBloodType(Long medicalInfoId, String bloodType) {
        MedicalInfo medicalInfo = this.getMedicalInfoById(medicalInfoId);
        medicalInfo.setBloodType(BloodType.valueOf(bloodType.toUpperCase()));
        medicalInfoRepository.save(medicalInfo);
        return modelMapper.map(medicalInfo,MedicalInfoDto.class);
    }

    public MedicalInfoDto updateChronicDiseases(Long medicalInfoId, String chronicDiseases) {
        MedicalInfo medicalInfo = this.getMedicalInfoById(medicalInfoId);
        medicalInfo.setChronicDiseases(chronicDiseases);
        medicalInfoRepository.save(medicalInfo);
        return modelMapper.map(medicalInfo,MedicalInfoDto.class);
    }

    public MedicalInfoDto updateAllergies(Long medicalInfoId, String allergies) {
        MedicalInfo medicalInfo = this.getMedicalInfoById(medicalInfoId);
        medicalInfo.setAllergies(allergies);
        medicalInfoRepository.save(medicalInfo);
        return modelMapper.map(medicalInfo,MedicalInfoDto.class);
    }

}
