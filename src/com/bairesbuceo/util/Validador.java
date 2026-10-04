package com.bairesbuceo.util;

import com.bairesbuceo.exception.StockInvalidoException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validador {
    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    public static void validarPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    public static void validarStock(int stock) {
        if (stock < 0) {
            throw new StockInvalidoException(stock);
        }
    }

    public static void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
    }

    public static void validarOpcion(int opcion, int min, int max) {
        if (opcion < min || opcion > max) {
            throw new IllegalArgumentException("Opción no válida. Elegí entre " + min + " y " + max + ".");
        }
    }

    public static void validarTextoNoVacio(String campo, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
    }

    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                int valor = sc.nextInt();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número entero. Intente nuevamente.");
                sc.nextLine();
            }
        }
    }

    public static double leerDouble(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                double valor = sc.nextDouble();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número válido (ej: 1500.50). Intente nuevamente.");
                sc.nextLine();
            }
        }
    }
}
