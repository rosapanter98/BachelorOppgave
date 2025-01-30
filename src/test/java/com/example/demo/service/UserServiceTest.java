package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.model.user.UserAuthentication;
import com.example.demo.repository.user.UserAuthenticationRepository;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserAuthenticationRepository userAuthenticationRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void save_ShouldPersistUserAndAutoLogin() {
        // Arrange
        UserRegistrationDTO registrationDto = new UserRegistrationDTO();
        registrationDto.setEmail("user@example.com");
        registrationDto.setPassword("password123");

        UserAuthentication newUser = new UserAuthentication();
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setRole("USER");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userAuthenticationRepository.save(any(UserAuthentication.class))).thenReturn(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(registrationDto.getEmail(), registrationDto.getPassword());
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        // Act
        userService.save(registrationDto);

        // Assert
        verify(userAuthenticationRepository).save(any(UserAuthentication.class));
        verify(authenticationManager).authenticate(any(Authentication.class));
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(authentication);
    }

}
