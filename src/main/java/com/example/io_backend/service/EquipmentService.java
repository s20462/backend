package com.example.io_backend.service;

import com.example.io_backend.exception.NotFoundException;
import com.example.io_backend.model.Equipment;
import com.example.io_backend.model.dto.EquipmentDto;
import com.example.io_backend.model.dto.response.EquipmentResponse;
import com.example.io_backend.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public List<EquipmentResponse> getEquipment() {
        return equipmentRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public EquipmentResponse getEquipmentById(Integer id) {
        return equipmentRepository.findById(id).map(this::mapToResponse).orElseThrow(NotFoundException::new);
    }
    public List<EquipmentResponse> getEquipmentByName(String name) {
        return equipmentRepository.getEquipmentByName(name).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<EquipmentResponse> addEquipment(List<EquipmentDto> equipmentRequest) {
        List<Equipment> equipment = new ArrayList<>();
        for (var e : equipmentRequest) {
            Equipment eq = new Equipment(null, e.getName(), null);
            eq = equipmentRepository.save(eq);
            equipment.add(eq);
        }

        return equipment.stream().map(this::mapToResponse).toList();
    }

    public EquipmentResponse updateEquipment(EquipmentDto equipment, Integer id){
        Equipment eq = equipmentRepository.findById(id).orElseThrow(NotFoundException::new);
        eq.setName(equipment.getName());

        eq = equipmentRepository.save(eq);
        return mapToResponse(eq);
    }

    public void deleteEquipment(Integer id) {
        Equipment eq = equipmentRepository.findById(id).orElseThrow(NotFoundException::new);
        equipmentRepository.delete(eq);
    }

    private EquipmentResponse mapToResponse(Equipment equipment) {
        EquipmentResponse eq = new EquipmentResponse();
        eq.setId(equipment.getId());
        eq.setName(equipment.getName());

        return eq;
    }
}
