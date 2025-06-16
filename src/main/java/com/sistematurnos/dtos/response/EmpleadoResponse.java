package com.sistematurnos.dtos.response;

import com.sistematurnos.entity.enums.Rol;
import java.time.LocalDateTime;
import java.util.Set;

public record EmpleadoResponse(
        int id,
        String nombre,
        String apellido,
        String email,
        String direccion,
        int dni,
        boolean estado,
        LocalDateTime fechaAlta,
        Rol rol,
        long cuil,
        String matricula,
        Set<String> especialidades
) {}
