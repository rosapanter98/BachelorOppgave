package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.model.user.UserAuthentication;
import com.example.demo.repository.user.UserAuthenticationRepository;


/**
 * Service class for managing user-related operations.
 * This class provides methods for user registration and authentication, including encoding passwords and automatically logging in users after registration.
 */
@Service
public class UserService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Registers a new user with the provided registration data.
     * This method creates a new {@link UserAuthentication} entity, encodes the password, sets the user's role,
     * saves the user, and attempts to automatically log in the new user.
     * 
     * @param registrationDto the data transfer object containing registration details such as email and password
     */
    public void save(UserRegistrationDTO registrationDto) {
        UserAuthentication newUser = new UserAuthentication();
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setRole("USER");  // Assuming the default role
        userAuthenticationRepository.save(newUser);

        autoLogin(newUser.getEmail(), registrationDto.getPassword());
    }

    /**
     * Automatically logs in the user with the given email and raw password.
     * This method creates an authentication token, attempts to authenticate it using the {@link AuthenticationManager},
     * and sets the authentication in the {@link SecurityContextHolder}.
     * 
     * @param email the user's email address
     * @param rawPassword the user's raw (unencoded) password
     */
    private void autoLogin(String email, String rawPassword) {
        Authentication auth = new UsernamePasswordAuthenticationToken(email, rawPassword);
        Authentication authenticated = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
    }
}
