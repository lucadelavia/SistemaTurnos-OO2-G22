package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.EspecialidadRequest;
import com.sistematurnos.dtos.response.EspecialidadResponse;
import com.sistematurnos.entity.Especialidad;

public class EspecialidadMapper {

    public static EspecialidadResponse toResponse(Especialidad especialidad) {
        EspecialidadResponse response = new EspecialidadResponse();
        response.setId(especialidad.getId());
        response.setNombre(especialidad.getNombre());
        return response;
    }

    public static Especialidad toEntity(EspecialidadRequest request) {
        Especialidad especialidad = new Especialidad();
        especialidad.setId(request.getId());
        especialidad.setNombre(request.getNombre());
        return especialidad;
    }
}