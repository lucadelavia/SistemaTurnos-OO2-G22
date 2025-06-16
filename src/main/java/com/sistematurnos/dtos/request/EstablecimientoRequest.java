package com.sistematurnos.dtos.request;

public record EstablecimientoRequest(
    int id,
    String nombre,
    String cuit,
    String direccion,
    String descripcion
) {}