package com.example.springframework.fitnesstracker.services.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MapValidationErrorService {
    ResponseEntity<?> mapValidationService(BindingResult result);
}
