package com.example.springframework.fitnesstracker.bootstrap;

import com.example.springframework.fitnesstracker.domain.AvailableExercise;
import com.example.springframework.fitnesstracker.domain.PastExercise;
import com.example.springframework.fitnesstracker.domain.User;
import com.example.springframework.fitnesstracker.services.interfaces.AvailableExerciseService;
import com.example.springframework.fitnesstracker.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserService userService;
    private final AvailableExerciseService availableExerciseService;

    @Autowired
    public BootstrapData(UserService userService, AvailableExerciseService availableExerciseService) {
        this.userService = userService;
        this.availableExerciseService = availableExerciseService;
    }

    @Override
    public void run(String... args) {

        User user = new User();
        user.setPassword("password");
        user.setBirthDate(new GregorianCalendar(1994, Calendar.FEBRUARY, 11).getTime());
        user.setEmail("test@test.com");
        List<PastExercise> pastExerciseList = new ArrayList<>();
        user.setPastExercises(pastExerciseList);
        userService.saveUser(user);

        AvailableExercise crunches = new AvailableExercise();
        crunches.setName("Crunches");
        crunches.setCalories(8);
        crunches.setDuration(30);

        AvailableExercise touchToes = new AvailableExercise();
        touchToes.setName("Touch Toes");
        touchToes.setCalories(15);
        touchToes.setDuration(180);

        AvailableExercise sideLunges = new AvailableExercise();
        sideLunges.setName("Side Lunges");
        sideLunges.setCalories(18);
        sideLunges.setDuration(120);

        AvailableExercise burpees = new AvailableExercise();
        burpees.setName("Burpees");
        burpees.setCalories(8);
        burpees.setDuration(60);

        availableExerciseService.save(crunches);
        availableExerciseService.save(touchToes);
        availableExerciseService.save(sideLunges);
        availableExerciseService.save(burpees);

    }
}
