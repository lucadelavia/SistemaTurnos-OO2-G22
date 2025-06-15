package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.response.UsuarioResponse;
import com.sistematurnos.entity.Usuario;

public class UsuarioMapper {

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
