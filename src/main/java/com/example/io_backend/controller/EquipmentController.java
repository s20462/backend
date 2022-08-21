package com.example.io_backend.controller;

import com.example.io_backend.model.dto.EquipmentDto;
import com.example.io_backend.model.dto.response.EquipmentResponse;
import com.example.io_backend.service.EquipmentLogService;
import com.example.io_backend.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public EquipmentResponse getById(@PathVariable Long id){
        return equipmentService.getEquipmentById(id);
    }

    @PostMapping
    public List<EquipmentResponse> add(@RequestBody List<EquipmentDto> equipment) {
        return equipmentService.addEquipment(equipment);
    }

    @PutMapping("/{id}")
    public EquipmentResponse update(@Valid @RequestBody EquipmentDto equipment, @PathVariable Long id){
        return equipmentService.updateEquipment(equipment,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
    }
}
