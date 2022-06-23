package com.example.io_backend.service;

import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.EquipmentLog;
import com.example.io_backend.repository.EquipmentLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentLogService {

    private final EquipmentLogRepository equipmentLogRepository;

    public List<EquipmentLog> getEquipmentLogs() {
        return equipmentLogRepository.findAll();
    }

    public EquipmentLog getEquipmentLogById(Integer id) {
        return equipmentLogRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that Id"));
    }

    public EquipmentLog addEquipmentLog(EquipmentLog equipmentLog){
        return equipmentLogRepository.save(equipmentLog);
    }

    public void updateEquipmentLog(EquipmentLog equipmentLog, Integer id){
        var e = equipmentLogRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that Id"));
        e.setId(equipmentLog.getId());
        e.setEquipment(equipmentLog.getEquipment());
        e.setAmbulance(equipmentLog.getAmbulance());
        e.setCurrentAmount(equipmentLog.getCurrentAmount());
        e.setDateStart(equipmentLog.getDateStart());
        e.setDateEnd(equipmentLog.getDateEnd());
        e.setStartingAmount(equipmentLog.getStartingAmount());
        e.setMeasurement(equipmentLog.getMeasurement());

        equipmentLogRepository.save(e);
    }

    public void deleteEquipmentLog(Integer id){
        var f = equipmentLogRepository.findById(id).orElseThrow(() -> new NotFoundException("No record with that Id"));
        equipmentLogRepository.delete(f);
    }
}
