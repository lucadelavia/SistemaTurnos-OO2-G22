package com.sistematurnos.dtos.request;

public record EstablecimientoRequest(
        String nombre,
        String cuit,
        String direccion,
        String descripcion
) {}
