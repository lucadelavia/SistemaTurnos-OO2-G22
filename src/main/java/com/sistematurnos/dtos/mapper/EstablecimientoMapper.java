package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.EstablecimientoRequest;
import com.sistematurnos.dtos.response.EstablecimientoResponse;
import com.sistematurnos.entity.Establecimiento;

public class EstablecimientoMapper {

    public static Establecimiento toEntity(EstablecimientoRequest request) {
        return new Establecimiento(
                request.nombre(),
                request.cuit(),
                request.direccion(),
                request.descripcion()
        );
    }

    public static Establecimiento toEntity(int id, EstablecimientoRequest request) {
        Establecimiento establecimiento = toEntity(request);
        establecimiento.setId(id);
        return establecimiento;
    }

    public static EstablecimientoResponse toResponse(Establecimiento est) {
        return new EstablecimientoResponse(
                est.getId(),
                est.getNombre(),
                est.getCuit(),
                est.getDireccion(),
                est.getDescripcion()
        );
    }
}
