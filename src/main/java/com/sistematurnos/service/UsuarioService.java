package com.sistematurnos.service;

import com.sistematurnos.entity.Usuario;
import com.sistematurnos.repository.IUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public Usuario altaUsuario(String nombre, String apellido, String email, String direccion, int dni) {
    	Usuario u = new Usuario(nombre, apellido, email, direccion, dni);
    	
    	if (usuarioRepository.findByDni(dni).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con ese DNI");
        }

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con ese EMAIL");
        }

        u.setFechaAlta(LocalDateTime.now());
        u.setEstado(true);

        return usuarioRepository.save(u);
    }
    
    public Usuario altaUsuario(Usuario u) {
        if (usuarioRepository.findByDni(u.getDni()).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con ese DNI");
        }

        if (usuarioRepository.findByEmail(u.getEmail()).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un usuario con ese EMAIL");
        }

        return usuarioRepository.save(u);
    }

    public void bajaUsuario(int id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe un usuario con ID: " + id));

        usuarioRepository.delete(usuario);
    }

    public Usuario modificarUsuario(Usuario u) {
        Usuario actual = usuarioRepository.findById(u.getId())
        	.orElseThrow(() -> new IllegalArgumentException("ERROR: No existe un usuario con ID: " + id));

        actual.setNombre(u.getNombre());
        actual.setApellido(u.getApellido());
        actual.setEmail(u.getEmail());
        actual.setDireccion(u.getDireccion());

        return usuarioRepository.save(actual);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe un usuario con ese ID"));
    }

    public Usuario obtenerUsuarioPorDNI(int dni) {
        return usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe un usuario con ese DNI"));
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe un usuario con EMAIL"));
    }
}