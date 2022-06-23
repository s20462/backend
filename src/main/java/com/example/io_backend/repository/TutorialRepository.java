package com.example.io_backend.repository;

import com.example.io_backend.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {
}