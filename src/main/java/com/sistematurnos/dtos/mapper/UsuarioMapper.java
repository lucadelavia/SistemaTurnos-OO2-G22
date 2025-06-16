package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.UsuarioRequest;
import com.sistematurnos.dtos.response.UsuarioResponse;
import com.sistematurnos.entity.Usuario;
import com.sistematurnos.entity.enums.Rol;
import java.time.LocalDateTime;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setApellido(dto.apellido());
        usuario.setEmail(dto.email());
        usuario.setPassword(dto.password());
        usuario.setDireccion(dto.direccion());
        usuario.setDni(dto.dni());
        usuario.setRol(dto.rol());
        usuario.setEstado(true); // Valor por defecto
        usuario.setFechaAlta(LocalDateTime.now()); // Fecha actual automática
        return usuario;
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getDireccion(),
                usuario.getDni(),
                usuario.isEstado(),
                usuario.getFechaAlta(),
                usuario.getRol()
        );
    }
}