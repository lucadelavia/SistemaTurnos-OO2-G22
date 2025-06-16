package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.EstablecimientoRequest;
import com.sistematurnos.dtos.response.EstablecimientoResponse;
import com.sistematurnos.entity.Establecimiento;

public class EstablecimientoMapper {

    public static EstablecimientoResponse toResponse(Establecimiento est) {
        return new EstablecimientoResponse(
            est.getId(),
            est.getNombre(),
            est.getCuit(),
            est.getDireccion(),
            est.getDescripcion()
        );
    }

    public static Establecimiento toEntity(EstablecimientoRequest request) {
        Establecimiento est = new Establecimiento();
        est.setId(request.id());
        est.setNombre(request.nombre());
        est.setCuit(request.cuit());
        est.setDireccion(request.direccion());
        est.setDescripcion(request.descripcion());
        return est;
    }

    public static Establecimiento toEntity(int id, EstablecimientoRequest request) {
        Establecimiento est = toEntity(request);
        est.setId(id);
        return est;
    }
}