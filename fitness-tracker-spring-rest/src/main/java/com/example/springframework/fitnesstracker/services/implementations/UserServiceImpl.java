package com.example.springframework.fitnesstracker.services.implementations;

import com.example.springframework.fitnesstracker.domain.User;
import com.example.springframework.fitnesstracker.exceptions.EmailAlreadyExistsException;
import com.example.springframework.fitnesstracker.repositories.UserRepository;
import com.example.springframework.fitnesstracker.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public User saveUser(User user) {

        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e){
            throw new EmailAlreadyExistsException("Email '" + user.getEmail() + "' already exists.");
        }
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }


}
