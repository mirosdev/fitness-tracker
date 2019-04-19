package com.example.springframework.fitnesstracker.controllers;

import com.example.springframework.fitnesstracker.domain.User;
import com.example.springframework.fitnesstracker.payload.JWTLoginSuccessResponse;
import com.example.springframework.fitnesstracker.payload.LoginRequest;
import com.example.springframework.fitnesstracker.security.JwtTokenProvider;
import com.example.springframework.fitnesstracker.services.interfaces.MapValidationErrorService;
import com.example.springframework.fitnesstracker.services.interfaces.UserService;
import com.example.springframework.fitnesstracker.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import static com.example.springframework.fitnesstracker.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MapValidationErrorService mapValidationErrorService;
    private final UserValidator userValidator;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService,
                          MapValidationErrorService mapValidationErrorService,
                          UserValidator userValidator,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.mapValidationErrorService = mapValidationErrorService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
		
		return new ResponseEntity<>(new JWTLoginSuccessResponse(true, jwt), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);

    }
}
