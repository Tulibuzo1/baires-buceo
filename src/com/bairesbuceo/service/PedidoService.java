package com.bairesbuceo.service;

import com.bairesbuceo.exception.StockInsuficienteException;
import com.bairesbuceo.model.LineaPedido;
import com.bairesbuceo.model.Pedido;
import com.bairesbuceo.model.Producto;
import com.bairesbuceo.util.Validador;
import java.util.ArrayList;

public class PedidoService {
    private ArrayList<Pedido> pedidos = new ArrayList<>();
    private int contadorId = 1;

    public void agregarLinea(Pedido pedido, Producto producto, int cantidad)
            throws StockInsuficienteException {
        Validador.validarCantidad(cantidad);

        int yaPedida = pedido.cantidadPedidaDe(producto);
        int disponible = producto.getStock() - yaPedida;

        if (cantidad > disponible) {
            throw new StockInsuficienteException(producto.getNombre(), disponible, cantidad);
        }
        pedido.agregarLinea(producto, cantidad);
    }

    public void confirmar(Pedido pedido) {
        for (LineaPedido linea : pedido.getLineas()) {
            Producto p = linea.getProducto();
            p.setStock(p.getStock() - linea.getCantidad());
        }
        pedido.setId(contadorId);
        contadorId++;
        pedidos.add(pedido);
    }

    public void listar() {
        if (pedidos.isEmpty()) {
            System.out.println("Todavía no se realizó ningún pedido.");
            return;
        }
        System.out.println("\n--- Pedidos realizados ---");
        for (Pedido p : pedidos) {
            System.out.println(p);
            System.out.println();
        }
    }
}
