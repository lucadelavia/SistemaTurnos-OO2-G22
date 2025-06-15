package com.sistematurnos.dtos.response;

public record EstablecimientoResponse(
        int id,
        String nombre,
        String cuit,
        String direccion,
        String descripcion
) {}
