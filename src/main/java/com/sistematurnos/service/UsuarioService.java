package com.sistematurnos.service;

import com.sistematurnos.entity.Usuario;
import com.sistematurnos.entity.enums.Rol;
import com.sistematurnos.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Usuario altaUsuario(Usuario u) {
        if (usuarioRepository.findByDni(u.getDni()).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con ese DNI");
        }
        if (usuarioRepository.findByEmail(u.getEmail()).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con ese EMAIL");
        }

        u.setPassword(encoder.encode(u.getPassword()));
        u.setFechaAlta(LocalDateTime.now());
        u.setEstado(true);

        return usuarioRepository.save(u);
    }

    public Usuario altaUsuario(String nombre, String apellido, String email, String password,
                               String direccion, int dni, Rol rol) {
        if (usuarioRepository.findByDni(dni).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con ese DNI");
        }
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con ese EMAIL");
        }

        Usuario u = new Usuario(
                nombre,
                apellido,
                email,
                encoder.encode(password),
                direccion,
                dni,
                true,
                LocalDateTime.now(),
                rol
        );

        return usuarioRepository.save(u);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    public void bajaUsuario(int id) {
        Usuario u = obtenerUsuarioPorId(id);
        usuarioRepository.delete(u);
    }

    public Usuario modificarUsuario(Usuario u) {
        Usuario actual = obtenerUsuarioPorId(u.getId());
        actual.setNombre(u.getNombre());
        actual.setApellido(u.getApellido());
        actual.setEmail(u.getEmail());
        actual.setDireccion(u.getDireccion());

        if (u.getPassword() != null && !u.getPassword().isBlank()) {
            actual.setPassword(encoder.encode(u.getPassword()));
        }

        return usuarioRepository.save(actual);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe un usuario con ese ID"));
    }

    public Usuario obtenerUsuarioPorDni(int dni) {
        return usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe un usuario con ese DNI"));
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe un usuario con ese EMAIL"));
    }

    public List<Usuario> obtenerUsuariosPorFecha(LocalDate fecha, boolean estado) {
        List<Usuario> usuarios = usuarioRepository.findByFechaAltaBetweenAndEstado(
                fecha.atStartOfDay(),
                fecha.plusDays(1).atStartOfDay(),
                estado
        );
        if (usuarios.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No hay usuarios creados en esta fecha: " + fecha);
        }
        return usuarios;
    }

    public List<Usuario> obtenerUsuariosPorRangoFechas(LocalDate desde, LocalDate hasta) {
        List<Usuario> usuarios = usuarioRepository.findByFechaAltaBetween(
                desde.atStartOfDay(),
                hasta.plusDays(1).atStartOfDay()
        );
        if (usuarios.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No hay usuarios creados en este rango de fechas");
        }
        return usuarios;
    }
}
