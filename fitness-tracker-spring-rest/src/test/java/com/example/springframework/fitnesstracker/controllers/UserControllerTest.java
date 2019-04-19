package com.example.springframework.fitnesstracker.controllers;

import com.example.springframework.fitnesstracker.domain.User;
import com.example.springframework.fitnesstracker.security.JwtTokenProvider;
import com.example.springframework.fitnesstracker.services.interfaces.MapValidationErrorService;
import com.example.springframework.fitnesstracker.services.interfaces.UserService;
import com.example.springframework.fitnesstracker.validator.UserValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = UserController.class, secure = false)
class UserControllerTest {

    @MockBean
    UserService userService;
    @MockBean
    MapValidationErrorService mapValidationErrorService;
    @MockBean
    UserValidator userValidator;
    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    MockMvc mockMvc;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("example@example.com");
    }

    @AfterEach
    void tearDown() {
        reset(userService);
        reset(mapValidationErrorService);
        reset(userValidator);
        reset(jwtTokenProvider);
        reset(authenticationManager);
    }

    @Test
    void authenticateUser() throws Exception {
        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(authenticationManager.authenticate(any())).willReturn(null);
        given(jwtTokenProvider.generateToken(any())).willReturn("example");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"example@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void registerUser() throws Exception {
        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(userService.saveUser(any())).willReturn(user);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"example@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.email", is("example@example.com")));
    }
}