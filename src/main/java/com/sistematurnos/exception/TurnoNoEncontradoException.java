package com.sistematurnos.exception;

public class TurnoNoEncontradoException extends RuntimeException {

    public TurnoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}