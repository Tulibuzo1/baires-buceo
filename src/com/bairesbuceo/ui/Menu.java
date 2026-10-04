package com.bairesbuceo.ui;

import com.bairesbuceo.exception.ProductoNoEncontradoException;
import com.bairesbuceo.exception.StockInsuficienteException;
import com.bairesbuceo.model.Consumible;
import com.bairesbuceo.model.Equipo;
import com.bairesbuceo.model.Pedido;
import com.bairesbuceo.model.Producto;
import com.bairesbuceo.service.PedidoService;
import com.bairesbuceo.service.ProductoService;
import com.bairesbuceo.util.Validador;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private ProductoService productoService = new ProductoService();
    private PedidoService pedidoService = new PedidoService();

    public void iniciar() {
        sc.useLocale(Locale.US);

        int opcion = 0;
        while (opcion != 7) {
            mostrarMenu();
            opcion = pedirOpcion("Elija una opción: ", 1, 7);

            if (opcion == 1) {
                agregarProducto();
            } else if (opcion == 2) {
                productoService.listar();
            } else if (opcion == 3) {
                buscarYActualizarProducto();
            } else if (opcion == 4) {
                eliminarProducto();
            } else if (opcion == 5) {
                crearPedido();
            } else if (opcion == 6) {
                pedidoService.listar();
            } else {
                System.out.println("¡Hasta luego, buen buceo!");
            }
        }
        sc.close();
    }

    private void mostrarMenu() {
        System.out.println("\n=================== SISTEMA DE GESTIÓN - BAIRES BUCEO ===================");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
        System.out.println();
    }

    private void agregarProducto() {
        System.out.println("\nTipo de producto:");
        System.out.println("1) Producto general");
        System.out.println("2) Equipo (regulador, traje, aletas...)");
        System.out.println("3) Consumible (antiempañante, cal sodada...)");
        int tipo = pedirOpcion("Tipo: ", 1, 3);

        String nombre = pedirNombre();
        double precio = pedirPrecio();
        int stock = pedirStock();

        Producto nuevo;
        if (tipo == 1) {
            nuevo = new Producto(nombre, precio, stock);
        } else if (tipo == 2) {
            String marca = pedirTexto("Marca");
            String talle = pedirTexto("Talle");
            nuevo = new Equipo(nombre, precio, stock, marca, talle);
        } else {
            String vencimiento = pedirTexto("Fecha de vencimiento (ej: 12/2027)");
            nuevo = new Consumible(nombre, precio, stock, vencimiento);
        }

        productoService.agregar(nuevo);
        System.out.println("Producto agregado: " + nuevo);
    }

    private void buscarYActualizarProducto() {
        System.out.println("\nBuscar por:");
        System.out.println("1) ID");
        System.out.println("2) Nombre");
        int modo = pedirOpcion("Opción: ", 1, 2);

        Producto encontrado = null;

        if (modo == 1) {
            int id = Validador.leerEntero(sc, "ID a buscar: ");
            try {
                encontrado = productoService.buscarPorId(id);
            } catch (ProductoNoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            System.out.print("Nombre (o parte del nombre): ");
            String texto = sc.nextLine();
            ArrayList<Producto> resultados = productoService.buscarPorNombre(texto);

            if (resultados.isEmpty()) {
                System.out.println("No se encontraron productos con ese nombre.");
                return;
            }
            if (resultados.size() == 1) {
                encontrado = resultados.get(0);
            } else {
                System.out.println("Se encontraron varios productos:");
                for (Producto p : resultados) {
                    System.out.println(p);
                }
                int id = Validador.leerEntero(sc, "Elegí el ID del producto: ");
                try {
                    encontrado = productoService.buscarPorId(id);
                } catch (ProductoNoEncontradoException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
        }

        System.out.println("\nEncontrado: " + encontrado);

        System.out.println("\n¿Qué querés actualizar?");
        System.out.println("1) Nombre");
        System.out.println("2) Precio");
        System.out.println("3) Stock");
        System.out.println("0) Nada, volver al menú");
        int que = pedirOpcion("Opción: ", 0, 3);

        if (que == 1) {
            encontrado.setNombre(pedirNombre());
            System.out.println("Nombre actualizado: " + encontrado);
        } else if (que == 2) {
            encontrado.setPrecio(pedirPrecio());
            System.out.println("Precio actualizado: " + encontrado);
        } else if (que == 3) {
            encontrado.setStock(pedirStock());
            System.out.println("Stock actualizado: " + encontrado);
        }
    }

    private void eliminarProducto() {
        int id = Validador.leerEntero(sc, "ID del producto a eliminar: ");
        try {
            Producto p = productoService.buscarPorId(id);
            System.out.println("Vas a eliminar: " + p);
            System.out.print("¿Confirmás? (s/n): ");
            String respuesta = sc.nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                productoService.eliminar(id);
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void crearPedido() {
        if (productoService.estaVacio()) {
            System.out.println("No hay productos cargados, no se puede crear un pedido.");
            return;
        }

        int cantidadProductos = Validador.leerEntero(sc, "¿Cuántos productos distintos va a agregar? ");
        try {
            Validador.validarCantidad(cantidadProductos);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        Pedido pedido = new Pedido();
        int agregados = 0;
        productoService.listar();

        while (agregados < cantidadProductos) {
            int id = Validador.leerEntero(sc, "\nID del producto (0 para terminar): ");
            if (id == 0) {
                break;
            }
            int cantidad = Validador.leerEntero(sc, "Cantidad: ");

            try {
                Producto producto = productoService.buscarPorId(id);
                pedidoService.agregarLinea(pedido, producto, cantidad);
                agregados++;
                System.out.println("Agregado: " + producto.getNombre() + " x" + cantidad);
            } catch (ProductoNoEncontradoException e) {
                System.out.println(e.getMessage());
            } catch (StockInsuficienteException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        if (pedido.getLineas().isEmpty()) {
            System.out.println("Pedido vacío, se cancela.");
            return;
        }

        System.out.println("\n--- Resumen del pedido ---");
        System.out.println(pedido);
        System.out.print("¿Confirmar pedido? (s/n): ");
        String respuesta = sc.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            pedidoService.confirmar(pedido);
            System.out.println("Pedido #" + pedido.getId() + " confirmado. Stock actualizado.");
        } else {
            System.out.println("Pedido cancelado. El stock no se modificó.");
        }
    }

    private String pedirNombre() {
        while (true) {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            try {
                Validador.validarNombre(nombre);
                return nombre;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private double pedirPrecio() {
        while (true) {
            double precio = Validador.leerDouble(sc, "Precio: ");
            try {
                Validador.validarPrecio(precio);
                return precio;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int pedirStock() {
        while (true) {
            int stock = Validador.leerEntero(sc, "Stock: ");
            try {
                Validador.validarStock(stock);
                return stock;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String pedirTexto(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String texto = sc.nextLine();
            try {
                Validador.validarTextoNoVacio(campo, texto);
                return texto;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int pedirOpcion(String mensaje, int min, int max) {
        while (true) {
            int opcion = Validador.leerEntero(sc, mensaje);
            try {
                Validador.validarOpcion(opcion, min, max);
                return opcion;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
