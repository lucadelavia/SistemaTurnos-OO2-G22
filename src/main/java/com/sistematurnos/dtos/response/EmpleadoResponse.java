package com.sistematurnos.dtos.response;

import java.util.Set;

public record EmpleadoResponse(
        int id,
        String nombre,
        String apellido,
        String email,
        String direccion,
        int dni,
        long cuil,
        String matricula,
        Set<String> especialidades
) {}
