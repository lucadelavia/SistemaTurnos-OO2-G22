package com.sistematurnos.security;

import com.sistematurnos.entity.Cliente;
import com.sistematurnos.entity.RolUsuario;
import com.sistematurnos.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Cliente registrarCliente(Cliente cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        cliente.setRol(RolUsuario.CLIENTE);
        cliente.setEstado(true);
        cliente.setFechaAlta(LocalDateTime.now());
        return usuarioRepository.save(cliente);
    }
}
