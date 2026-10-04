package com.bairesbuceo.model;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private ArrayList<LineaPedido> lineas = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public ArrayList<LineaPedido> getLineas() { return lineas; }

    public void agregarLinea(Producto producto, int cantidad) {
        for (LineaPedido linea : lineas) {
            if (linea.getProducto().getId() == producto.getId()) {
                linea.setCantidad(linea.getCantidad() + cantidad);
                return;
            }
        }
        lineas.add(new LineaPedido(producto, cantidad));
    }

    public int cantidadPedidaDe(Producto producto) {
        for (LineaPedido linea : lineas) {
            if (linea.getProducto().getId() == producto.getId()) {
                return linea.getCantidad();
            }
        }
        return 0;
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        String texto;
        if (id == 0) {
            texto = "Pedido (sin confirmar)\n";
        } else {
            texto = "Pedido #" + id + "\n";
        }
        for (LineaPedido linea : lineas) {
            texto += linea + "\n";
        }
        texto += "  TOTAL: $" + String.format("%.2f", calcularTotal());
        return texto;
    }
}
