package com.sistematurnos.dtos.response;

import com.sistematurnos.entity.enums.Rol;

import java.time.LocalDateTime;

public record UsuarioResponse(
        int id,
        String nombre,
        String apellido,
        String email,
        String direccion,
        int dni,
        boolean estado,
        LocalDateTime fechaAlta,
        Rol rol
) {
}
