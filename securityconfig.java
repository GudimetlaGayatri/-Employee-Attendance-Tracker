// SecurityConfig.java
package com.example.attendance.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.withUsername("employee").password(encoder.encode("pass")).roles("EMPLOYEE").build();
        UserDetails admin = User.withUsername("manager").password(encoder.encode("admin")).roles("MANAGER").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/attendance/**").hasAnyRole("EMPLOYEE", "MANAGER")
            .requestMatchers("/api/attendance/department-summary").hasRole("MANAGER")
            .anyRequest().authenticated()
            .and().httpBasic();
        return http.build();
    }
}

