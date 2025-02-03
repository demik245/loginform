package com.demik.LoginForm.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;

    // Constructor injection for userDetailsService only
    public SecurityConfig(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Bean for PasswordEncoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Secure password encoding
    }

    // Security filter chain configuration for HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/addUser", "/login", "/loginUser").permitAll()  // Permit access to these URLs
                        .anyRequest().authenticated()  // Require authentication for any other requests
                )
                .formLogin(login -> login
                        .loginPage("/login")  // Custom login page
                        .loginProcessingUrl("/loginUser")  // URL to process the login
                        .defaultSuccessUrl("/hello", true)  // Redirect on success
                        .failureUrl("/login?error=true")  // Redirect on failure
                        .permitAll()  // Allow anyone to access the login page
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL to trigger logout
                        .logoutSuccessUrl("/login")  // Redirect after logout
                        .permitAll()  // Allow anyone to log out
                )
                .userDetailsService(userDetailsService);  // Use custom UserDetailsService for authentication

        return http.build();
    }

    // Optionally, configure the AuthenticationManager
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());  // Use UserDetailsService and password encoder
        return authenticationManagerBuilder.build();  // Return the configured AuthenticationManager
    }
}
