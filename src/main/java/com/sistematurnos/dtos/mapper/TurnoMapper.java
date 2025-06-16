package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.TurnoRequest;
import com.sistematurnos.dtos.response.TurnoResponse;
import com.sistematurnos.entity.*;

public class TurnoMapper {

    public static Turno toEntity(TurnoRequest dto) {
        Turno turno = new Turno();
        turno.setFechaHora(dto.fechaHora());
        turno.setEstado(dto.estado());
        turno.setCodigo(dto.codigo());
        return turno;
    }

    public static TurnoResponse toResponse(Turno turno) {
        return new TurnoResponse(
                turno.getId(),
                turno.getFechaHora(),
                turno.isEstado(),
                turno.getCodigo(),
                turno.getCliente() != null ? turno.getCliente().getId() : null,
                turno.getEmpleado() != null ? turno.getEmpleado().getId() : null,
                turno.getSucursal() != null ? turno.getSucursal().getId() : null,
                turno.getServicio() != null ? turno.getServicio().getId() : null
        );
    }
}