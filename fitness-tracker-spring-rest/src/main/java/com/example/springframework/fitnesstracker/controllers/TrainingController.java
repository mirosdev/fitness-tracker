package com.example.springframework.fitnesstracker.controllers;

import com.example.springframework.fitnesstracker.domain.PastExercise;
import com.example.springframework.fitnesstracker.services.interfaces.AvailableExerciseService;
import com.example.springframework.fitnesstracker.services.interfaces.PastExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    private final AvailableExerciseService availableExerciseService;
    private final PastExerciseService pastExerciseService;

    @Autowired
    public TrainingController(AvailableExerciseService availableExerciseService,
                              PastExerciseService pastExerciseService) {
        this.availableExerciseService = availableExerciseService;
        this.pastExerciseService = pastExerciseService;
    }

    @GetMapping("/available")
    public ResponseEntity<?> findAllExercises(){
        return new ResponseEntity<>(availableExerciseService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePastExercise(@RequestBody PastExercise pastExercise, Principal principal){
        return new ResponseEntity<>(pastExerciseService.save(pastExercise, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/past")
    public ResponseEntity<?> findAllPastExercises(Principal principal){
        return new ResponseEntity<>(pastExerciseService.findAllByUserEmail(principal.getName()), HttpStatus.OK);
    }
}
