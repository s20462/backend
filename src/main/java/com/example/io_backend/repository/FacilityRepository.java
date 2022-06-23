package com.example.io_backend.repository;

import com.example.io_backend.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {
}