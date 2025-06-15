package com.sistematurnos.service;

import com.sistematurnos.entity.Empleado;
import com.sistematurnos.entity.Especialidad;

import java.time.LocalDateTime;
import java.util.List;

public interface IEmpleadoService {
    public List<Empleado> traerEmpleados();

    public Empleado altaEmpleado(String nombre, String apellido, String email, String direccion,
                                int dni, boolean estado, LocalDateTime fechaAlta, long cuil, String matricula, String password);
    
    public Empleado altaEmpleado(Empleado empleado);

    public Empleado altaEmpleadoConEspecialidades(Empleado empleado);

    public Empleado obtenerEmpleadoPorId(int id);

    public Empleado obtenerEmpleadoPorCuil(long cuil);

    public Empleado obtenerEmpleadoPorMatricula(String matricula);

    public Empleado modificarEmpleado(Empleado e);

    public void bajaEmpleado(int id);

    public void asignarEspecialidad(int idEmpleado, Especialidad esp);

    public void removerEspecialidad(int idEmpleado, Especialidad esp);
}
