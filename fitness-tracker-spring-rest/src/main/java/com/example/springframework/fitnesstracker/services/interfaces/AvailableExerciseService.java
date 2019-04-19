package com.example.springframework.fitnesstracker.services.interfaces;

import com.example.springframework.fitnesstracker.domain.AvailableExercise;

import java.util.List;

public interface AvailableExerciseService {
    AvailableExercise save(AvailableExercise availableExercise);
    List<AvailableExercise> findAll();
}
