package com.sistematurnos.service;

import com.sistematurnos.entity.Usuario;
import com.sistematurnos.entity.enums.Rol;

import java.time.LocalDate;
import java.util.List;

public interface IUsuarioService {
    public Usuario altaUsuario(Usuario u);

    public Usuario altaUsuario(String nombre, String apellido, String email, String password,
                               String direccion, int dni, Rol rol);

    public Usuario buscarPorEmail(String email);
    
    public void bajaUsuario(int id);

    public Usuario modificarUsuario(Usuario u);

    public Usuario obtenerUsuarioPorId(int id);

    public Usuario obtenerUsuarioPorDni(int dni);

    public Usuario obtenerUsuarioPorEmail(String email);

    public List<Usuario> obtenerUsuariosPorFecha(LocalDate fecha, boolean estado);

    public List<Usuario> obtenerUsuariosPorRangoFechas(LocalDate desde, LocalDate hasta);
}
