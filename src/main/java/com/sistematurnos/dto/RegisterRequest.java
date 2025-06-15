package com.sistematurnos.dto;

import com.sistematurnos.entity.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private int dni;
    private String direccion;
    private Rol rol;
}
