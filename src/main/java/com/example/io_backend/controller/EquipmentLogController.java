package com.example.io_backend.controller;

import com.example.io_backend.model.EquipmentLog;
import com.example.io_backend.service.EquipmentLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/equipmentLog")
@RequiredArgsConstructor
public class EquipmentLogController {

    private final EquipmentLogService equipmentLogService;

    @GetMapping("")
    public List<EquipmentLog> getAll() {
        return equipmentLogService.getEquipmentLogs();
    }

    @GetMapping("/{id}")
    public EquipmentLog getById(@PathVariable Integer id) {
        return equipmentLogService.getEquipmentLogById(id);
    }

    @PostMapping("")
    public EquipmentLog add(@RequestBody EquipmentLog equipmentLog) {
        return equipmentLogService.addEquipmentLog(equipmentLog);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody EquipmentLog equipmentLog, @PathVariable Integer id) {
        equipmentLogService.updateEquipmentLog(equipmentLog,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        equipmentLogService.deleteEquipmentLog(id);
    }
}
