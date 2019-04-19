package com.example.springframework.fitnesstracker.services.implementations;

import com.example.springframework.fitnesstracker.domain.PastExercise;
import com.example.springframework.fitnesstracker.domain.User;
import com.example.springframework.fitnesstracker.repositories.PastExercisesRepository;
import com.example.springframework.fitnesstracker.repositories.UserRepository;
import com.example.springframework.fitnesstracker.services.interfaces.PastExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PastExerciseServiceImpl implements PastExerciseService {

    private final PastExercisesRepository pastExercisesRepository;
    private final UserRepository userRepository;

    @Autowired
    public PastExerciseServiceImpl(PastExercisesRepository pastExercisesRepository,
                                   UserRepository userRepository) {
        this.pastExercisesRepository = pastExercisesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PastExercise save(PastExercise pastExercise, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.getPastExercises().size() > 0){
                List<PastExercise> pastExerciseList = user.getPastExercises();
                pastExerciseList.add(pastExercise);
                user.setPastExercises(pastExerciseList);
                userRepository.save(user);
            } else {
                List<PastExercise> pastExerciseList = new ArrayList<>();
                pastExerciseList.add(pastExercise);
                user.setPastExercises(pastExerciseList);
                userRepository.save(user);
            }

            pastExercise.setUser(user);
            return pastExercisesRepository.save(pastExercise);

        } else {
            return null;
        }
    }

    @Override
    public List<PastExercise> findAllByUserEmail(String email) {
        Optional<List<PastExercise>> optionalPastExercises = pastExercisesRepository.findAllByUserEmail(email);
        return optionalPastExercises.orElse(null);
    }


}
