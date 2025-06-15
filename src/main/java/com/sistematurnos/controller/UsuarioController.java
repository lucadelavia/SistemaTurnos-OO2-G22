package com.sistematurnos.controller;

import com.sistematurnos.dtos.response.UsuarioResponse;
import com.sistematurnos.dtos.mapper.UsuarioMapper;
import com.sistematurnos.entity.Usuario;
import com.sistematurnos.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
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
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener datos del usuario autenticado")
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> getMe(Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email);
        UsuarioResponse response = UsuarioMapper.toResponse(usuario);
        return ResponseEntity.ok(response);
    }
}
