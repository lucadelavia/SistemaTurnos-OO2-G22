package com.sistematurnos.controller;

import com.sistematurnos.dtos.mapper.EspecialidadMapper;
import com.sistematurnos.dtos.request.EspecialidadRequest;
import com.sistematurnos.dtos.response.EspecialidadResponse;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.service.EspecialidadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/especialidades")
@Tag(name = "Especialidades", description = "Gestión de especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    @Operation(summary = "Listar todas las especialidades")
    @GetMapping
    public List<EspecialidadResponse> listarEspecialidades() {
        return especialidadService.traerEspecialidades().stream()
                .map(EspecialidadMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener especialidad por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponse> obtenerPorId(@PathVariable int id) {
        try {
            Especialidad especialidad = especialidadService.obtenerEspecialidadPorId(id);
            return ResponseEntity.ok(EspecialidadMapper.toResponse(especialidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener especialidad por nombre")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EspecialidadResponse> obtenerPorNombre(@PathVariable String nombre) {
        try {
            Especialidad especialidad = especialidadService.obtenerEspecialidadPorNombre(nombre);
            return ResponseEntity.ok(EspecialidadMapper.toResponse(especialidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva especialidad")
    @PostMapping
    public ResponseEntity<EspecialidadResponse> crear(@RequestBody EspecialidadRequest request) {
        try {
            Especialidad nueva = especialidadService.altaEspecialidad(EspecialidadMapper.toEntity(request));
            return ResponseEntity.ok(EspecialidadMapper.toResponse(nueva));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Modificar una especialidad")
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadResponse> modificar(@PathVariable int id, @RequestBody EspecialidadRequest request) {
        try {
            Especialidad actualizada = especialidadService.modificarEspecialidad(
                    EspecialidadMapper.toEntity(id, request));
            return ResponseEntity.ok(EspecialidadMapper.toResponse(actualizada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar (baja lógica) una especialidad")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            especialidadService.bajaEspecialidad(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
