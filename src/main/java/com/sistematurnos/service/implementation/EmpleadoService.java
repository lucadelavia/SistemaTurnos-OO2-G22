package com.sistematurnos.service.implementation;

import com.sistematurnos.entity.Empleado;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.Servicio;
import com.sistematurnos.entity.enums.Rol;
import com.sistematurnos.exception.EmpleadoNoEncontradoException;
import com.sistematurnos.repository.IEmpleadoRepository;
import com.sistematurnos.repository.IEspecialidadRepository;
import com.sistematurnos.service.IEmpleadoService;
import com.sistematurnos.service.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmpleadoService implements IEmpleadoService {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    @Autowired
    private IServicioService servicioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Empleado altaEmpleado(String nombre, String apellido, String email, String direccion,
                                 int dni, boolean estado, LocalDateTime fechaAlta, long cuil, String matricula, String password) {

        if (empleadoRepository.findByDni(dni).isPresent()) {
            throw new EmpleadoNoEncontradoException("ERROR: Ya existe un empleado con ese DNI");
        }

        if (empleadoRepository.findByCuil(cuil).isPresent()) {
            throw new EmpleadoNoEncontradoException("ERROR: Ya existe un empleado con ese CUIL");
        }

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setEmail(email);
        empleado.setDireccion(direccion);
        empleado.setDni(dni);
        empleado.setEstado(estado);
        empleado.setFechaAlta(fechaAlta);
        empleado.setCuil(cuil);
        empleado.setMatricula(matricula);
        empleado.setPassword(passwordEncoder.encode(password));
        empleado.setRol(Rol.EMPLEADO);

        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado altaEmpleado(Empleado empleado) {
        empleado.setRol(Rol.EMPLEADO);
        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
        empleado.setFechaAlta(LocalDateTime.now());
        empleado.setEstado(true);
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado altaEmpleadoConEspecialidades(Empleado empleado) {
        if (empleado.getPassword() == null || empleado.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria para crear un empleado.");
        }

        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
        empleado.setEstado(true);
        empleado.setFechaAlta(LocalDateTime.now());
        empleado.setRol(Rol.EMPLEADO);

        Set<Especialidad> especialidades = null;

        if (empleado.getLstEspecialidades() != null && !empleado.getLstEspecialidades().isEmpty()) {
            especialidades = empleado.getLstEspecialidades().stream()
                    .map(e -> especialidadRepository.findById(e.getId())
                            .orElseThrow(() -> new EmpleadoNoEncontradoException("Especialidad no encontrada: " + e.getId())))
                    .collect(Collectors.toSet());
        }

        empleado.setLstEspecialidades(null);
        Empleado guardado = empleadoRepository.save(empleado);

        if (especialidades != null) {
            guardado.setLstEspecialidades(especialidades);
            guardado = empleadoRepository.save(guardado);
        }

        return guardado;
    }

    @Override
    public Empleado modificarEmpleado(Empleado e) {
        Empleado actual = obtenerEmpleadoPorId(e.getId());

        actual.setNombre(e.getNombre());
        actual.setApellido(e.getApellido());
        actual.setEmail(e.getEmail());
        actual.setDireccion(e.getDireccion());
        actual.setCuil(e.getCuil());
        actual.setMatricula(e.getMatricula());

        if (e.getPassword() != null && !e.getPassword().isBlank()) {
            actual.setPassword(passwordEncoder.encode(e.getPassword()));
        }

        actual.setRol(Rol.EMPLEADO);

        if (e.getLstEspecialidades() != null && !e.getLstEspecialidades().isEmpty()) {
            actual.getLstEspecialidades().clear();
            empleadoRepository.save(actual);

            Set<Especialidad> especialidadesActualizadas = e.getLstEspecialidades().stream()
                    .map(es -> especialidadRepository.findById(es.getId())
                            .orElseThrow(() -> new EmpleadoNoEncontradoException("Especialidad no encontrada: " + es.getId())))
                    .collect(Collectors.toSet());

            actual.getLstEspecialidades().addAll(especialidadesActualizadas);
        } else {
            actual.getLstEspecialidades().clear();
        }

        return empleadoRepository.save(actual);
    }

    @Override
    public Empleado obtenerEmpleadoPorId(int id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("ERROR: No existe el empleado con ID " + id));
    }

    @Override
    public Empleado obtenerEmpleadoPorCuil(long cuil) {
        return empleadoRepository.findByCuil(cuil)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("ERROR: No existe el empleado con CUIL " + cuil));
    }

    @Override
    public Empleado obtenerEmpleadoPorMatricula(String matricula) {
        return empleadoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("No existe un empleado con la matrícula: " + matricula));
    }

    @Override
    public List<Empleado> buscarPorServicio(int idServicio) {
        Servicio servicio = servicioService.obtenerServicioPorId(idServicio);
        Especialidad especialidad = servicio.getEspecialidad();

        if (especialidad == null) {
            throw new IllegalArgumentException("ERROR: El servicio no tiene especialidad asignada.");
        }

        return empleadoRepository.findByLstEspecialidadesContaining(especialidad);
    }

    @Override
    public List<Empleado> buscarPorEspecialidad(int idEspecialidad) {
        Especialidad especialidad = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada con ID " + idEspecialidad));

        return empleadoRepository.findByLstEspecialidadesContaining(especialidad);
    }

    @Override
    public void bajaEmpleado(int id) {
        Empleado empleado = obtenerEmpleadoPorId(id);

        empleado.getLstEspecialidades().clear();
        empleadoRepository.save(empleado);

        empleadoRepository.delete(empleado);
    }


    @Override
    public void asignarEspecialidad(int idEmpleado, Especialidad esp) {
        Empleado e = obtenerEmpleadoPorId(idEmpleado);
        Especialidad especialidad = especialidadRepository.findById(esp.getId())
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Especialidad no encontrada"));

        if (!e.getLstEspecialidades().contains(especialidad)) {
            e.getLstEspecialidades().add(especialidad);
            empleadoRepository.save(e);
        }
    }

    @Override
    public void removerEspecialidad(int idEmpleado, Especialidad esp) {
        Empleado e = obtenerEmpleadoPorId(idEmpleado);
        Especialidad especialidad = especialidadRepository.findById(esp.getId())
                .orElseThrow(() -> new EmpleadoNoEncontradoException("Especialidad no encontrada"));

        if (e.getLstEspecialidades().contains(especialidad)) {
            e.getLstEspecialidades().remove(especialidad);
            empleadoRepository.save(e);
        }
    }

    @Override
    public List<Empleado> traerEmpleados() {
        return empleadoRepository.findByEstadoTrue();
    }
}
