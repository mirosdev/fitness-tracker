package com.example.springframework.fitnesstracker.repositories;

import com.example.springframework.fitnesstracker.domain.AvailableExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableExercisesRepository extends JpaRepository<AvailableExercise, Long> {
}
