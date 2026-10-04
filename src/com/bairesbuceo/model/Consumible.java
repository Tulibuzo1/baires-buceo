package com.bairesbuceo.model;

import com.bairesbuceo.util.Validador;

public class Consumible extends Producto {
    private String fechaVencimiento;

    public Consumible(String nombre, double precio, int stock, String fechaVencimiento) {
        super(nombre, precio, stock);
        Validador.validarTextoNoVacio("La fecha de vencimiento", fechaVencimiento);
        this.fechaVencimiento = fechaVencimiento.trim();
    }

    public String getFechaVencimiento() { return fechaVencimiento; }

    @Override
    public String getDetalle() {
        return " | Consumible - Vence: " + fechaVencimiento;
    }
}
