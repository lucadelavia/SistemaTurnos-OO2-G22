package com.sistematurnos.service;

import com.sistematurnos.entity.Empleado;
import com.sistematurnos.entity.enums.Rol;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.exception.EmpleadoNoEncontradoException;
import com.sistematurnos.repository.IEmpleadoRepository;
import com.sistematurnos.repository.IEspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Empleado altaEmpleado(Empleado empleado) {
        if (empleadoRepository.findByDni(empleado.getDni()).isPresent()) {
            throw new EmpleadoNoEncontradoException("ERROR: Ya existe un empleado con ese DNI");
        }

        if (empleadoRepository.findByCuil(empleado.getCuil()).isPresent()) {
            throw new EmpleadoNoEncontradoException("ERROR: Ya existe un empleado con ese CUIL");
        }

        empleado.setPassword(encoder.encode(empleado.getPassword()));
        empleado.setRol(Rol.EMPLEADO);
        empleado.setEstado(true);
        empleado.setFechaAlta(LocalDateTime.now());

        return empleadoRepository.save(empleado);
    }

    public Empleado altaEmpleadoConEspecialidades(Empleado empleado) {
        empleado.setPassword(encoder.encode(empleado.getPassword()));
        empleado.setRol(Rol.EMPLEADO);
        empleado.setEstado(true);
        empleado.setFechaAlta(LocalDateTime.now());

        if (empleado.getLstEspecialidades() != null && !empleado.getLstEspecialidades().isEmpty()) {
            Set<Especialidad> especialidadesExistentes = empleado.getLstEspecialidades().stream()
                    .map(espec -> especialidadRepository.findById(espec.getId())
                            .orElseThrow(() -> new EmpleadoNoEncontradoException("Especialidad no encontrada: " + espec.getId())))
                    .collect(Collectors.toSet());

            empleado.setLstEspecialidades(especialidadesExistentes);
        }

        return empleadoRepository.save(empleado);
    }

    public Empleado obtenerEmpleadoPorId(int id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("ERROR: No existe el empleado solicitado con ID " + id));
    }

    public Empleado obtenerEmpleadoPorCuil(long cuil) {
        return empleadoRepository.findByCuil(cuil)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("ERROR: No existe el empleado solicitado con CUIL " + cuil));
    }

    public Empleado obtenerEmpleadoPorMatricula(String matricula) {
        return empleadoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("No existe un empleado con la matrícula: " + matricula));
    }

    public Empleado modificarEmpleado(Empleado e) {
        Empleado actual = obtenerEmpleadoPorId(e.getId());

        actual.setNombre(e.getNombre());
        actual.setApellido(e.getApellido());
        actual.setEmail(e.getEmail());
        actual.setDireccion(e.getDireccion());
        actual.setCuil(e.getCuil());
        actual.setMatricula(e.getMatricula());

        if (e.getLstEspecialidades() != null) {
            Set<Especialidad> especialidadesActualizadas = e.getLstEspecialidades().stream()
                    .map(es -> especialidadRepository.findById(es.getId())
                            .orElseThrow(() -> new EmpleadoNoEncontradoException("Especialidad no encontrada: " + es.getId())))
                    .collect(Collectors.toSet());
            actual.setLstEspecialidades(especialidadesActualizadas);
        }

        return empleadoRepository.save(actual);
    }

    public void bajaEmpleado(int id) {
        Empleado empleado = obtenerEmpleadoPorId(id);
        empleado.setEstado(false);
        empleadoRepository.save(empleado);
    }

    public void asignarEspecialidad(int idEmpleado, Especialidad esp) {
        Empleado e = obtenerEmpleadoPorId(idEmpleado);
        Especialidad especialidad = especialidadRepository.findById(esp.getId())
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Especialidad no encontrada"));

        if (!e.getLstEspecialidades().contains(especialidad)) {
            e.getLstEspecialidades().add(especialidad);
            empleadoRepository.save(e);
        }
    }

    public void removerEspecialidad(int idEmpleado, Especialidad esp) {
        Empleado e = obtenerEmpleadoPorId(idEmpleado);
        Especialidad especialidad = especialidadRepository.findById(esp.getId())
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Especialidad no encontrada"));

        if (e.getLstEspecialidades().contains(especialidad)) {
            e.getLstEspecialidades().remove(especialidad);
            empleadoRepository.save(e);
        }
    }

    public List<Empleado> traerEmpleados() {
        return empleadoRepository.findByEstadoTrue();
    }
}
