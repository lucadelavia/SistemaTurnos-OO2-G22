package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.TurnoRequest;
import com.sistematurnos.dtos.response.TurnoResponse;
import com.sistematurnos.entity.*;

public class TurnoMapper {

    public static TurnoResponse toResponse(Turno turno) {
        return new TurnoResponse(
                turno.getId(),
                turno.getFechaHora(),
                turno.isEstado(),
                turno.getCodigo(),
                turno.getCliente().getNombre() + " " + turno.getCliente().getApellido(),
                turno.getEmpleado().getNombre() + " " + turno.getEmpleado().getApellido(),
                turno.getServicio().getNombreServicio(),
                turno.getSucursal().getDireccion()
        );
    }

    public static Turno toEntity(TurnoRequest req, Cliente cliente, Empleado empleado,
                                 Sucursal sucursal, Servicio servicio) {
        return new Turno(
                req.fechaHora(),
                req.estado(),
                req.codigo(),
                servicio,
                cliente,
                sucursal,
                empleado
        );
    }
}
