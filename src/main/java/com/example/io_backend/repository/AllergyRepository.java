package com.example.io_backend.repository;

import com.example.io_backend.model.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<Allergy,Long> {
}
