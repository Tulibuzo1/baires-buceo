package com.bairesbuceo.exception;

public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String nombreProducto, int disponible, int pedido) {
        super("Stock insuficiente de '" + nombreProducto + "'. Disponible: "
                + disponible + " | Pedido: " + pedido);
    }
}
