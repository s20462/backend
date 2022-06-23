package com.example.io_backend.controller;

import com.example.io_backend.model.dto.EquipmentDto;
import com.example.io_backend.model.dto.response.EquipmentResponse;
import com.example.io_backend.service.EquipmentLogService;
import com.example.io_backend.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;
    private final EquipmentLogService equipmentLogService;

    @GetMapping
    public List<EquipmentResponse> getAll() {
        return equipmentService.getEquipment();
    }

    @GetMapping("/{id}")
    public EquipmentResponse getById(@PathVariable Integer id){
        return equipmentService.getEquipmentById(id);
    }

    @PostMapping
    public List<EquipmentResponse> add(@RequestBody List<EquipmentDto> equipment) {
        return equipmentService.addEquipment(equipment);
    }

    @PutMapping("/{id}")
    public EquipmentResponse update(@RequestBody EquipmentDto equipment, @PathVariable Integer id){
        return equipmentService.updateEquipment(equipment,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        equipmentService.deleteEquipment(id);
    }
}
