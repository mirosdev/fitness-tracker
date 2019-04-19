package com.example.springframework.fitnesstracker.services.implementations;

import com.example.springframework.fitnesstracker.domain.User;
import com.example.springframework.fitnesstracker.repositories.UserRepository;
import com.example.springframework.fitnesstracker.services.interfaces.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public User loadUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
