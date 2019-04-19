package com.example.springframework.fitnesstracker.services.interfaces;

import com.example.springframework.fitnesstracker.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    User loadUserById(Long id);
}
