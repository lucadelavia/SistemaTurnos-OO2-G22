package com.sistematurnos.service;

import com.sistematurnos.dto.LoginRequest;
import com.sistematurnos.dto.RegisterRequest;
import com.sistematurnos.entity.Usuario;
import com.sistematurnos.repository.IUsuarioRepository;
import com.sistematurnos.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    public String register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario usuario = new Usuario(
                request.getNombre(),
                request.getApellido(),
                request.getEmail(),
                encoder.encode(request.getPassword()),
                request.getDireccion(),
                request.getDni(),
                true,
                LocalDateTime.now(),
                request.getRol()
        );

        usuarioRepository.save(usuario);
        return "Usuario registrado correctamente con rol: " + request.getRol();
    }

    public String login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        System.out.println("🔐 Contraseña ingresada: " + request.getPassword());
        System.out.println("🗝️  Hash en la DB: " + usuario.getPassword());

        boolean match = encoder.matches(request.getPassword(), usuario.getPassword());
        System.out.println("✅ Coinciden?: " + match);

        if (!match) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());
    }
}
