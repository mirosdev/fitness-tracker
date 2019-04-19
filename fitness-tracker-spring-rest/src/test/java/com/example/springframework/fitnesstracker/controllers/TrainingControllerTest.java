package com.example.springframework.fitnesstracker.controllers;

import com.example.springframework.fitnesstracker.domain.AvailableExercise;
import com.example.springframework.fitnesstracker.domain.PastExercise;
import com.example.springframework.fitnesstracker.domain.User;
import com.example.springframework.fitnesstracker.services.interfaces.AvailableExerciseService;
import com.example.springframework.fitnesstracker.services.interfaces.PastExerciseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = TrainingController.class, secure = false)
class TrainingControllerTest {

    @MockBean
    AvailableExerciseService availableExerciseService;

    @MockBean
    PastExerciseService pastExerciseService;

    @Autowired
    MockMvc mockMvc;

    List<AvailableExercise> availableExercises = new ArrayList<>();

    Principal principal;

    AvailableExercise availableExercise = new AvailableExercise();

    PastExercise pastExercise = new PastExercise();

    List<PastExercise> pastExercises = new ArrayList<>();

    @BeforeEach
    void setUp() {
        pastExercise.setId(1L);
        pastExercise.setName("pExerciseName");

        availableExercise.setId(1L);
        availableExercise.setName("aExerciseName");

        availableExercises.add(availableExercise);

        pastExercises.add(pastExercise);

        principal = () -> "nameStr";
    }

    @AfterEach
    void tearDown() {
        reset(availableExerciseService);
        reset(pastExerciseService);
    }

    @Test
    void findAllExercises() throws Exception {
        given(availableExerciseService.findAll()).willReturn(availableExercises);

        mockMvc.perform(get("/api/training/available"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void savePastExercise() throws Exception {
        given(pastExerciseService.save(any(PastExercise.class), anyString())).willReturn(pastExercise);

        mockMvc.perform(post("/api/training/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"id\":1,\"name\":\"pExerciseName\"}")
                .principal(principal))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void findAllPastExercises() throws Exception {
        given(pastExerciseService.findAllByUserEmail(anyString())).willReturn(pastExercises);

        mockMvc.perform(get("/api/training/past").principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }
}