package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.EstablecimientoRequest;
import com.sistematurnos.dtos.response.EstablecimientoResponse;
import com.sistematurnos.entity.Establecimiento;

public class EstablecimientoMapper {

    public static EstablecimientoResponse toResponse(Establecimiento est) {
        EstablecimientoResponse dto = new EstablecimientoResponse();
        dto.setId(est.getId());
        dto.setNombre(est.getNombre());
        dto.setCuit(est.getCuit());
        dto.setDireccion(est.getDireccion());
        dto.setDescripcion(est.getDescripcion());
        return dto;
    }

    public static Establecimiento toEntity(EstablecimientoRequest request) {
        Establecimiento est = new Establecimiento();
        est.setId(request.getId());
        est.setNombre(request.getNombre());
        est.setCuit(request.getCuit());
        est.setDireccion(request.getDireccion());
        est.setDescripcion(request.getDescripcion());
        return est;
    }

    public static Establecimiento toEntity(int id, EstablecimientoRequest request) {
        Establecimiento est = toEntity(request);
        est.setId(id); 
        return est;
    }
}