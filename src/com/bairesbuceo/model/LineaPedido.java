package com.bairesbuceo.model;

public class LineaPedido {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }

    @Override
    public String toString() {
        return "  - " + producto.getNombre()
                + " x" + cantidad
                + " | $" + String.format("%.2f", precioUnitario) + " c/u"
                + " | Subtotal: $" + String.format("%.2f", getSubtotal());
    }
}
