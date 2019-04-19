package com.example.springframework.fitnesstracker.services.interfaces;

import com.example.springframework.fitnesstracker.domain.User;

public interface UserService {
    User saveUser(User user);
    User findByEmail(String email);
}
