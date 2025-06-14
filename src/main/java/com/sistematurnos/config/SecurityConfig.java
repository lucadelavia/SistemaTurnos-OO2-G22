package com.sistematurnos.config;

import com.sistematurnos.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/login.html",
                                "/registro.html",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/api/auth/**"
                        ).permitAll()

                        .requestMatchers("/api/clientes/**").hasRole("ADMIN")
                        .requestMatchers("/api/empleados/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                        .requestMatchers("/api/turnos/reservar", "/api/turnos/mis-turnos").hasRole("CLIENTE")
                        .requestMatchers("/api/turnos/**").hasAnyRole("ADMIN", "EMPLEADO")
                        .requestMatchers(
                                "/api/sucursales/**",
                                "/api/servicios/**",
                                "/api/especialidades/**",
                                "/api/establecimientos/**"
                        ).hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
