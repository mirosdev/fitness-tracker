package com.example.springframework.fitnesstracker.repositories;

import com.example.springframework.fitnesstracker.domain.PastExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PastExercisesRepository extends JpaRepository<PastExercise, Long> {
    Optional<List<PastExercise>> findAllByUserEmail(String email);
}
