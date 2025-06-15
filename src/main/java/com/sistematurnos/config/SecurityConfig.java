package com.sistematurnos.config;

<<<<<<< HEAD
import com.sistematurnos.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
=======
>>>>>>> 99f4d3c (Version Funcional Spring Security)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
<<<<<<< HEAD
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
=======
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
<<<<<<< HEAD
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
=======

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
>>>>>>> 99f4d3c (Version Funcional Spring Security)
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
<<<<<<< HEAD
=======

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
>>>>>>> 99f4d3c (Version Funcional Spring Security)
}
