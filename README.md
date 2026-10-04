# Baires Buceo

Sistema de gestión de productos y pedidos por consola, en Java.

## Estructura

```
src/com/bairesbuceo/
├── Main.java                         (arranca el programa)
├── ui/
│   └── Menu.java                     (menú y lectura de datos)
├── model/
│   ├── Producto.java                 (clase base)
│   ├── Equipo.java                   (hereda de Producto: marca, talle)
│   ├── Consumible.java               (hereda de Producto: vencimiento)
│   ├── Pedido.java
│   └── LineaPedido.java
├── service/
│   ├── ProductoService.java          (agregar, listar, buscar, eliminar)
│   └── PedidoService.java            (valida stock, confirma pedidos)
├── exception/
│   ├── ProductoNoEncontradoException.java
│   ├── StockInsuficienteException.java
│   └── StockInvalidoException.java
└── util/
    └── Validador.java                (todas las validaciones)
```

