package com.sistematurnos.controller;

import com.sistematurnos.entity.Cliente;
import com.sistematurnos.entity.Usuario;
import com.sistematurnos.security.AuthService;
import com.sistematurnos.security.CustomUserDetails;
import com.sistematurnos.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Usuario usuario = userDetails.getUsuario();

        String jwt = jwtService.generateToken(usuario.getEmail(), usuario.getRol().name());

        return ResponseEntity.ok(Map.of(
                "token", jwt,
                "nombre", usuario.getNombre(),
                "rol", usuario.getRol().name()
        ));
    }

    @PostMapping("/registro")
    public ResponseEntity<?> register(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = authService.registrarCliente(cliente);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Cliente registrado con éxito",
                "id", nuevoCliente.getId()
        ));
    }
}
