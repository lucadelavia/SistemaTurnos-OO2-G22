package com.sistematurnos.controller;

import com.sistematurnos.entity.Usuario;
import com.sistematurnos.service.IUsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuario", description = "Operaciones relacionadas con el usuario autenticado")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<Usuario> getMe(Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

}
