package com.sistematurnos.controller;

import com.sistematurnos.entity.Usuario;
import com.sistematurnos.repository.IUsuarioRepository;
import com.sistematurnos.entity.enums.Rol;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@Controller
public class AuthController {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Vista de login
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas");
        }
        if (logout != null) {
            model.addAttribute("mensaje", "Sesión cerrada correctamente");
        }
        return "login";
    }

    // Vista de formulario de registro
    @GetMapping("/registro")
    public String showRegistroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // Procesamiento de registro
    @PostMapping("/registro")
    public String registrar(@ModelAttribute Usuario usuario,
                            @RequestParam("confirmPassword") String confirmPassword,
                            Model model) {

        if (!usuario.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "registro";
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            model.addAttribute("error", "El email ya está registrado");
            return "registro";
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.CLIENTE); // Rol por defecto

        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    // Retorna el rol del usuario autenticado
    @ResponseBody
    @GetMapping("/auth/rol")
    public ResponseEntity<String> obtenerRol(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("");
        }

        return usuarioRepository.findByEmail(principal.getName())
                .map(usuario -> ResponseEntity.ok(usuario.getRol().name()))
                .orElseGet(() -> ResponseEntity.status(404).body(""));
    }

    // Retorna nombre y rol del usuario autenticado
    @GetMapping("/auth/usuario")
    @ResponseBody
    public ResponseEntity<Map<String, String>> obtenerDatosUsuario(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of());
        }

        return usuarioRepository.findByEmail(principal.getName())
                .map(u -> ResponseEntity.ok(Map.of(
                        "nombre", u.getNombre(),
                        "rol", u.getRol().name()
                )))
                .orElse(ResponseEntity.status(404).body(Map.of()));
    }
}
