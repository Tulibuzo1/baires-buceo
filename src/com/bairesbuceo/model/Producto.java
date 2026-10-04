package com.bairesbuceo.model;

import com.bairesbuceo.util.Validador;

public class Producto {
    private static int contadorId = 1;

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        Validador.validarNombre(nombre);
        Validador.validarPrecio(precio);
        Validador.validarStock(stock);

        this.id = contadorId;
        contadorId++;
        this.nombre = nombre.trim();
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setNombre(String nombre) {
        Validador.validarNombre(nombre);
        this.nombre = nombre.trim();
    }

    public void setPrecio(double precio) {
        Validador.validarPrecio(precio);
        this.precio = precio;
    }

    public void setStock(int stock) {
        Validador.validarStock(stock);
        this.stock = stock;
    }

    public String getDetalle() {
        return "";
    }

    @Override
    public String toString() {
        return "[ID: " + id + "] " + nombre
                + " | Precio: $" + String.format("%.2f", precio)
                + " | Stock: " + stock
                + getDetalle();
    }
}
