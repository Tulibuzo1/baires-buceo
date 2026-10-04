package com.bairesbuceo.service;

import com.bairesbuceo.exception.ProductoNoEncontradoException;
import com.bairesbuceo.model.Producto;
import java.util.ArrayList;

public class ProductoService {
    private ArrayList<Producto> productos = new ArrayList<>();

    public void agregar(Producto producto) {
        productos.add(producto);
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }

    public void listar() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        System.out.println("\n--- Listado de productos ---");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ProductoNoEncontradoException(id);
    }

    public ArrayList<Producto> buscarPorNombre(String texto) {
        ArrayList<Producto> encontrados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    public void eliminar(int id) {
        Producto p = buscarPorId(id);
        productos.remove(p);
        System.out.println("Producto eliminado: " + p.getNombre());
    }
}
