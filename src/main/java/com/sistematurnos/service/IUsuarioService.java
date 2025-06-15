package com.sistematurnos.service;

import com.sistematurnos.entity.RolUsuario;
import com.sistematurnos.entity.Usuario;

import java.time.LocalDate;
import java.util.List;

public interface IUsuarioService {
    public List<Usuario> obtenerUsuarios();

    public Usuario obtenerUsuarioPorId(int id);

    public Usuario obtenerUsuarioPorDni(int dni);

	public Usuario obtenerUsuarioPorEmail(String email);

    public List<Usuario> obtenerUsuariosPorFecha(LocalDate fecha, boolean estado);

    public List<Usuario> obtenerUsuariosPorRangoFechas(LocalDate desde, LocalDate hasta);

    public Usuario altaUsuario(Usuario usuario);

    public Usuario modificarUsuario(Usuario usuario);

    public Usuario altaUsuario(String nombre, String apellido, String email, String direccion, int dni, String password, RolUsuario rol);

    public void bajaUsuario(int dni);

}
