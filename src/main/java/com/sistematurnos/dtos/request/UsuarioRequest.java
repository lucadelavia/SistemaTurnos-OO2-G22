package com.sistematurnos.dtos.request;

import com.sistematurnos.entity.enums.Rol;

public record UsuarioRequest(
        String nombre,
        String apellido,
        String email,
        String password,
        String direccion,
        int dni,
        boolean estado,
        Rol rol
) {}