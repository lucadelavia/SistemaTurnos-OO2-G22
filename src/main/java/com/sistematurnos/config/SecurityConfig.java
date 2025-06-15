package com.sistematurnos.config;

import com.sistematurnos.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Públicos
                        .requestMatchers("/", "/index", "/login", "/registro", "/css/**", "/js/**", "/img/**").permitAll()

                        // Acceso general
                        .requestMatchers("/inicio").authenticated()

                        // ADMIN
                        .requestMatchers("/clientes", "/api/clientes/**").hasRole("ADMIN")
                        .requestMatchers("/empleados", "/api/empleados/**").hasRole("ADMIN")
                        .requestMatchers("/establecimientos", "/api/establecimientos/**").hasRole("ADMIN")

                        // EMPLEADO: solo puede ver turnos asignados y consultar módulos
                        .requestMatchers("/turnos", "/api/turnos/**").hasAnyRole("EMPLEADO", "CLIENTE", "ADMIN")
                        .requestMatchers("/sucursales", "/api/sucursales/**").hasAnyRole("EMPLEADO", "CLIENTE", "ADMIN")
                        .requestMatchers("/servicios", "/api/servicios/**").hasAnyRole("EMPLEADO", "CLIENTE", "ADMIN")
                        .requestMatchers("/especialidades", "/api/especialidades/**").hasAnyRole("EMPLEADO", "CLIENTE", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/inicio", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
