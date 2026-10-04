package com.bairesbuceo.model;

import com.bairesbuceo.util.Validador;

public class Equipo extends Producto {
    private String marca;
    private String talle;

    public Equipo(String nombre, double precio, int stock, String marca, String talle) {
        super(nombre, precio, stock);
        Validador.validarTextoNoVacio("La marca", marca);
        Validador.validarTextoNoVacio("El talle", talle);
        this.marca = marca.trim();
        this.talle = talle.trim();
    }

    public String getMarca() { return marca; }
    public String getTalle() { return talle; }

    @Override
    public String getDetalle() {
        return " | Equipo - Marca: " + marca + " | Talle: " + talle;
    }
}
