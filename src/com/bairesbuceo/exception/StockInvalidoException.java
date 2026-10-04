package com.bairesbuceo.exception;

public class StockInvalidoException extends IllegalArgumentException {
    public StockInvalidoException(int stock) {
        super("El stock no puede ser negativo (se ingresó: " + stock + ").");
    }
}
