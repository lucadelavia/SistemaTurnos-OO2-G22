package com.sistematurnos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TurnoNoEncontradoException.class)
    public ResponseEntity<?> manejarTurnoNoEncontrado(TurnoNoEncontradoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<?> manejarClienteNoEncontrado(ClienteNoEncontradoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<?> manejarEmpleadoNoEncontrado(EmpleadoNoEncontradoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteDuplicadoException.class)
    public ResponseEntity<?> manejarClienteDuplicado(ClienteDuplicadoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejarExcepcionGeneral(Exception ex) {
        return buildErrorResponse("Ocurrió un error inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(String mensaje, HttpStatus status) {
        Map<String, String> body = new HashMap<>();
        body.put("error", mensaje);
        return new ResponseEntity<>(body, status);
    }
}
