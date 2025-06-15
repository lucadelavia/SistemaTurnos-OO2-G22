package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.EmpleadoRequest;
import com.sistematurnos.dtos.response.EmpleadoResponse;
import com.sistematurnos.entity.Empleado;
import com.sistematurnos.entity.enums.Rol;
import com.sistematurnos.entity.Especialidad;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class EmpleadoMapper {

    public static Empleado toEntity(EmpleadoRequest request, Set<Especialidad> especialidades) {
        Empleado empleado = new Empleado();
        empleado.setNombre(request.nombre());
        empleado.setApellido(request.apellido());
        empleado.setEmail(request.email());
        empleado.setPassword(request.password());
        empleado.setDireccion(request.direccion());
        empleado.setDni(request.dni());
        empleado.setCuil(request.cuil());
        empleado.setMatricula(request.matricula());
        empleado.setEstado(true);
        empleado.setFechaAlta(LocalDateTime.now());
        empleado.setRol(Rol.EMPLEADO);
        empleado.setLstEspecialidades(especialidades);
        return empleado;
    }

    public static EmpleadoResponse toResponse(Empleado empleado) {
        Set<String> nombresEsp = empleado.getLstEspecialidades().stream()
                .map(Especialidad::getNombre)
                .collect(Collectors.toSet());

        return new EmpleadoResponse(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getEmail(),
                empleado.getDireccion(),
                empleado.getDni(),
                empleado.getCuil(),
                empleado.getMatricula(),
                nombresEsp
        );
    }
}
