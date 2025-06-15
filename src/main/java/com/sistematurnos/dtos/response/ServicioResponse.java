package com.sistematurnos.dtos.response;

public record ServicioResponse(
        int id,
        String nombreServicio,
        int duracion,
        String nombreEspecialidad
) {}
