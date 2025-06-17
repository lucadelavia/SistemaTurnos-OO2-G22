package com.sistematurnos.dtos.request;

public record ServicioRequest(
        String nombreServicio,
        int duracion,
        int idEspecialidad,
        boolean estado
) {}