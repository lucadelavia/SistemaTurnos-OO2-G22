package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.DiasDeAtencionRequest;
import com.sistematurnos.dtos.response.DiasDeAtencionResponse;
import com.sistematurnos.entity.DiasDeAtencion;

public class DiasDeAtencionMapper {

    public static DiasDeAtencion toEntity(DiasDeAtencionRequest dto) {
        return new DiasDeAtencion(dto.nombre());
    }

    public static DiasDeAtencionResponse toResponse(DiasDeAtencion dia) {
        return new DiasDeAtencionResponse(dia.getId(), dia.getNombre());
    }
}
