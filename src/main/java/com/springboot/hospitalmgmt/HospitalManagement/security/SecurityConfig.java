package com.springboot.hospitalmgmt.HospitalManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig class defines how authentication and authorization
 * are handled across the entire Hospital Management System application.
 *
 * It sets up the authentication manager, defines which endpoints are
 * public or secured, and configures login and logout behavior.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Service that loads user-specific data for authentication.
     * (Custom implementation of UserDetailsService)
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Bean that handles password encoding and verification.
     * Usually configured in PasswordConfig.java
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * AuthenticationManager bean — required for authenticating users manually
     * (e.g., in JWT-based login endpoints or form login).
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Main security configuration for the HTTP layer.
     * Defines which endpoints are open or secured,
     * and configures CSRF, login, and logout.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for simplicity (enable it for form-based apps)
                .csrf(csrf -> csrf.disable())

                // Configure endpoint access control
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints that don't need authentication
                        .requestMatchers("/public/**", "/login", "/register").permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated())

                // Configure form-based login
                .formLogin(login -> login
                        // The endpoint Spring Security will process the login request from
                        .loginProcessingUrl("/login")
                        // Custom login page (optional)
                        .loginPage("/login").permitAll()
                        // Redirect after successful login
                        .defaultSuccessUrl("/dashboard", true)
                        // Redirect after failed login
                        .failureUrl("/login?error=true"))

                // Configure logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll());

        return http.build();
    }
}
