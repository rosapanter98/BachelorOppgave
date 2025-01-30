package com.example.demo.config;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.model.user.UserAuthentication;
import com.example.demo.repository.user.UserAuthenticationRepository;

/**
 * Configuration class for Spring Security.
 * This class provides the necessary configuration for securing the web application.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    /**
     * Creates and configures the security filter chain for the application.
     * This method is responsible for setting up various security configurations such as CSRF protection,
     * CORS configuration, frame options, request authorization, form login, and logout.
     *
     * @param http the HttpSecurity object used to configure the security filter chain
     * @return the configured SecurityFilterChain object
     * @throws Exception if an error occurs during configuration
     */
	@SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF 
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS configuration
            .headers(headers -> headers.frameOptions().sameOrigin()) // Allow framing from the same origin
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/","/error","/register", "/login","/veileder/pdf-options", 
                "/veileder","/about","/contact", "/index","/home", "/css/**", "/js/**", "/images/**", 
                "/WEB-INF/jsp/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Only allow users with ROLE_ADMIN to access /admin
                // Explicitly permit these
                .anyRequest().authenticated() // Require authentication for all other requests
                )
                
                .formLogin(form -> form
                    .loginPage("/home")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/home", true)
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutSuccessUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                );
    
            return http.build();
    }
    
    /**
     * Creates and returns an instance of the AuthenticationManager.
     *
     * @param userDetailsService The UserDetailsService implementation to use for authentication.
     * @param passwordEncoder The PasswordEncoder implementation to use for encoding passwords.
     * @return An instance of the AuthenticationManager.
     */
	@Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }


    /**
     * Returns a UserDetailsService implementation that retrieves user details from the UserAuthenticationRepository.
     * 
     * @param userAuthenticationRepository the repository used to retrieve user authentication information
     * @param passwordEncoder the password encoder used to encode user passwords
     * @return a UserDetailsService implementation
     */
	@Bean
    public UserDetailsService userDetailsService(UserAuthenticationRepository userAuthenticationRepository, PasswordEncoder passwordEncoder) {
        return email -> {
            UserAuthentication user = userAuthenticationRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with email: " + email);
            }

            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
        };
    }

    /**
     * Returns a new instance of the PasswordEncoder interface that uses the BCrypt hashing algorithm.
     *
     * @return a PasswordEncoder implementation using BCrypt
     */
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * A source for CORS (Cross-Origin Resource Sharing) configuration.
     * Implementations of this interface provide the CORS configuration that is used by the web application.
     * The CORS configuration specifies the allowed origins, methods, headers, and whether credentials are allowed.
     * 
     * @return The CORS configuration for the web application.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080")); // Adjust to match your frontend
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "X-CSRF-TOKEN")); // Ensure CSRF header is allowed
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}