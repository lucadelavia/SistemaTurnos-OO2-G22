package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.EspecialidadRequest;
import com.sistematurnos.dtos.response.EspecialidadResponse;
import com.sistematurnos.entity.Especialidad;

public class EspecialidadMapper {

    public static EspecialidadResponse toResponse(Especialidad especialidad) {
        return new EspecialidadResponse(
            especialidad.getId(),
            especialidad.getNombre()
        );
    }

    public static Especialidad toEntity(EspecialidadRequest request) {
        return new Especialidad(
            request.id(),
            request.nombre()
        );
    }

    public static Especialidad toEntity(int id, EspecialidadRequest request) {
        return new Especialidad(id, request.nombre());
    }
}