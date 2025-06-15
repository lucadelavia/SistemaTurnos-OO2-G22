package com.sistematurnos.exception;

public class ClienteDuplicadoException extends RuntimeException {
    public ClienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
