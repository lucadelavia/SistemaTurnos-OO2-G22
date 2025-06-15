package com.sistematurnos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

    @GetMapping("/inicio")
    public String inicio() {
        return "inicio";
    }

    @GetMapping("/clientes")
    public String clientes() {
        return "clientes";
    }

    @GetMapping("/empleados")
    public String empleados() {
        return "empleados";
    }

    @GetMapping("/especialidades")
    public String especialidades() {
        return "especialidades";
    }

    @GetMapping("/establecimientos")
    public String establecimientos() {
        return "establecimientos";
    }

    @GetMapping("/servicios")
    public String servicios() {
        return "servicios";
    }

    @GetMapping("/sucursales")
    public String sucursales() {
        return "sucursales";
    }

    @GetMapping("/turnos")
    public String turnos() {
        return "turnos";
    }

    // ⚠️ ELIMINADOS para evitar conflicto
    // @GetMapping("/login")
    // public String login() {
    //     return "login";
    // }

    // @GetMapping("/registro")
    // public String registro() {
    //     return "registro";
    // }
}
