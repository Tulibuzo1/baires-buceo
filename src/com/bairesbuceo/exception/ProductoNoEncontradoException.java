package com.bairesbuceo.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(int id) {
        super("No se encontró ningún producto con ID: " + id);
    }
}
