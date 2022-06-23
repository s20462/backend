package com.example.io_backend.repository;

import com.example.io_backend.model.EquipmentLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentLogRepository extends JpaRepository<EquipmentLog, Integer> {
}