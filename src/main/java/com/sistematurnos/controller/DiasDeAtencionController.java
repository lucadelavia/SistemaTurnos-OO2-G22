package com.sistematurnos.controller;

import com.sistematurnos.dtos.request.DiasDeAtencionRequest;
import com.sistematurnos.dtos.response.DiasDeAtencionResponse;
import com.sistematurnos.dtos.mapper.DiasDeAtencionMapper;
import com.sistematurnos.entity.DiasDeAtencion;
import com.sistematurnos.service.IDiasDeAtencionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dias-atencion")
@Tag(name = "Días de Atención", description = "Gestión de días disponibles de atención")
public class DiasDeAtencionController {

    @Autowired
    private IDiasDeAtencionService diasDeAtencionService;

    @Operation(summary = "Listar todos los días de atención")
    @GetMapping
    public List<DiasDeAtencionResponse> listarDias() {
        return diasDeAtencionService.traerTodos().stream()
                .map(DiasDeAtencionMapper::toResponse)
                .toList(); // reemplazo moderno de collect(Collectors.toList())
    }

    @Operation(summary = "Obtener un día de atención por ID")
    @GetMapping("/{id}")
    public ResponseEntity<DiasDeAtencionResponse> obtenerPorId(@PathVariable int id) {
        try {
            DiasDeAtencion dia = diasDeAtencionService.traer(id);
            return ResponseEntity.ok(DiasDeAtencionMapper.toResponse(dia));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo día de atención")
    @PostMapping
    public ResponseEntity<DiasDeAtencionResponse> crear(@RequestBody DiasDeAtencionRequest request) {
        try {
            DiasDeAtencion nuevo = diasDeAtencionService.altaDiaDeAtencion(
                    DiasDeAtencionMapper.toEntity(request));
            return ResponseEntity.ok(DiasDeAtencionMapper.toResponse(nuevo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar un día de atención existente")
    @PutMapping("/{id}")
    public ResponseEntity<DiasDeAtencionResponse> actualizar(@PathVariable int id,
                                                             @RequestBody DiasDeAtencionRequest request) {
        try {
            DiasDeAtencion dia = DiasDeAtencionMapper.toEntity(request);
            dia.setId(id);
            DiasDeAtencion actualizado = diasDeAtencionService.modificarDiaDeAtencion(dia);
            return ResponseEntity.ok(DiasDeAtencionMapper.toResponse(actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un día de atención (baja lógica)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            diasDeAtencionService.bajaDiaDeAtencion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
