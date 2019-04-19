package com.example.springframework.fitnesstracker.services.interfaces;

import com.example.springframework.fitnesstracker.domain.PastExercise;

import java.util.List;

public interface PastExerciseService {
    PastExercise save(PastExercise pastExercise, String email);
    List<PastExercise> findAllByUserEmail(String email);
}
