package com.example.springframework.fitnesstracker.services.implementations;

import com.example.springframework.fitnesstracker.domain.AvailableExercise;
import com.example.springframework.fitnesstracker.repositories.AvailableExercisesRepository;
import com.example.springframework.fitnesstracker.services.interfaces.AvailableExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableExerciseServiceImpl implements AvailableExerciseService {

    private final AvailableExercisesRepository availableExercisesRepository;

    @Autowired
    public AvailableExerciseServiceImpl(AvailableExercisesRepository availableExercisesRepository) {
        this.availableExercisesRepository = availableExercisesRepository;
    }

    @Override
    public AvailableExercise save(AvailableExercise availableExercise) {
        return availableExercisesRepository.save(availableExercise);
    }

    @Override
    public List<AvailableExercise> findAll() {
        return availableExercisesRepository.findAll();
    }
}
