package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.EspecialidadRequest;
import com.sistematurnos.dtos.response.EspecialidadResponse;
import com.sistematurnos.entity.Especialidad;

public class EspecialidadMapper {

    public static Especialidad toEntity(EspecialidadRequest request) {
        return new Especialidad(request.nombre());
    }

    public static Especialidad toEntity(int id, EspecialidadRequest request) {
        Especialidad especialidad = new Especialidad(request.nombre());
        especialidad.setId(id);
        return especialidad;
    }

    public static EspecialidadResponse toResponse(Especialidad especialidad) {
        return new EspecialidadResponse(
                especialidad.getId(),
                especialidad.getNombre()
        );
    }
}
