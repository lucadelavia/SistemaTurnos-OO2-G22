package com.sistematurnos.service;

import com.sistematurnos.entity.Empleado;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.repository.IEmpleadoRepository;
import com.sistematurnos.repository.IEspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    public Empleado altaEmpleado(String nombre, String apellido, String email, String direccion,
                                 int dni, boolean estado, LocalDateTime fechaAlta, long cuil, String matricula) {

        if (empleadoRepository.findByDni(dni).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un empleado con ese DNI");
        }

        if (empleadoRepository.findByCuil(cuil).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un empleado con ese CUIL");
        }

        Empleado e = new Empleado(nombre, apellido, email, direccion, dni, estado, fechaAlta, cuil, matricula);
        return empleadoRepository.save(e);
    }

    public Empleado altaEmpleado(Empleado empleado) {
        if (empleadoRepository.findByDni(empleado.getDni()).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un empleado con ese DNI");
        }

        if (empleadoRepository.findByCuil(empleado.getCuil()).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe un empleado con ese CUIL");
        }

        return empleadoRepository.save(empleado);
    }

    public Empleado obtenerEmpleadoPorId(int id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe el empleado solicitado"));
    }

    public Empleado obtenerEmpleadoPorCuil(long cuil) {
        return empleadoRepository.findByCuil(cuil)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe el empleado solicitado"));
    }

    public Empleado obtenerEmpleadoPorMatricula(String matricula) {
        return empleadoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new IllegalArgumentException("No existe un empleado con esa matrícula"));
    }

    public Empleado modificarEmpleado(Empleado e) {
        Empleado actual = obtenerEmpleadoPorId(e.getId());
        actual.setNombre(e.getNombre());
        actual.setApellido(e.getApellido());
        actual.setEmail(e.getEmail());
        actual.setDireccion(e.getDireccion());
        actual.setCuil(e.getCuil());
        actual.setMatricula(e.getMatricula());
        return empleadoRepository.save(actual);
    }

    public void bajaEmpleado(int id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
        empleado.setEstado(false);
        empleadoRepository.save(empleado);
    }

    public void asignarEspecialidad(int idEmpleado, Especialidad esp) {
        Empleado e = obtenerEmpleadoPorId(idEmpleado);
        Especialidad especialidad = especialidadRepository.findById(esp.getId())
                .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada"));

        if (!e.getLstEspecialidades().contains(especialidad)) {
            e.getLstEspecialidades().add(especialidad);
            empleadoRepository.save(e);
        }
    }

    public void removerEspecialidad(int idEmpleado, Especialidad esp) {
        Empleado e = obtenerEmpleadoPorId(idEmpleado);
        Especialidad especialidad = especialidadRepository.findById(esp.getId())
                .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada"));

        if (e.getLstEspecialidades().contains(especialidad)) {
            e.getLstEspecialidades().remove(especialidad);
            empleadoRepository.save(e);
        }
    }

    public List<Empleado> traerEmpleados() {
        return empleadoRepository.findByEstadoTrue();
    }
}
