package com.example.io_backend.repository;

import com.example.io_backend.model.MedicalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalInfoRepository extends JpaRepository<MedicalInfo, Integer> {
}