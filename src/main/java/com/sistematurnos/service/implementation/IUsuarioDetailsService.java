package com.sistematurnos.service.implementation;

import com.sistematurnos.entity.Usuario;
import com.sistematurnos.repository.IUsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class IUsuarioDetailsService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;

    public IUsuarioDetailsService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities("ROLE_" + usuario.getRol().name()) // ADMIN, CLIENTE, etc.
                .build();
    }
}
