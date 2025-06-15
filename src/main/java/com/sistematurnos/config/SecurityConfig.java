package com.sistematurnos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        // Recursos públicos
                        .requestMatchers("/", "/index", "/login", "/registro", "/css/**", "/js/**", "/img/**").permitAll()

                        // Swagger (documentación)
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Página principal
                        .requestMatchers("/inicio").authenticated()

                        // 🔒 Obtener datos del usuario autenticado
                        .requestMatchers("/api/usuario/me").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")

                        // Clientes: lectura para admin y cliente
                        .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/clientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasRole("ADMIN")

                        // Empleados: lectura para admin y empleado
                        .requestMatchers(HttpMethod.GET, "/api/empleados/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/api/empleados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/empleados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/empleados/**").hasRole("ADMIN")

                        // Establecimientos
                        .requestMatchers("/establecimientos").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/establecimientos/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                        .requestMatchers("/api/establecimientos/**").hasRole("ADMIN")

                        // Turnos
                        .requestMatchers("/turnos", "/api/turnos/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")

                        // Sucursales, servicios, especialidades
                        .requestMatchers("/sucursales", "/api/sucursales/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                        .requestMatchers("/servicios", "/api/servicios/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")
                        .requestMatchers("/especialidades", "/api/especialidades/**").hasAnyRole("ADMIN", "EMPLEADO", "CLIENTE")

                        // Cualquier otra ruta necesita autenticación
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
