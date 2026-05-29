/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.computosas.modelo;

public class Monitor extends Producto {
    private String resolucion;
    private int tasaRefresco;

    public Monitor(int idProducto, String nombre, String numeroSerie, String descripcion, 
                   double precioVenta, int cantidadActual, int cantidadMinimaPermitida, 
                   boolean esBajoPedido, String rutaImagen, String paisProcedencia, 
                   String fabricante, double pesoGramos, String medidas, 
                   int duracionGarantiaDias, int idProveedor, String tipoProducto,
                   String resolucion, int tasaRefresco) {
        // Enviar datos a la superclase Producto
        super(idProducto, nombre, numeroSerie, descripcion, precioVenta, cantidadActual, 
              cantidadMinimaPermitida, esBajoPedido, rutaImagen, paisProcedencia, 
              fabricante, pesoGramos, medidas, duracionGarantiaDias, idProveedor, tipoProducto);
        this.resolucion = resolucion;
        this.tasaRefresco = tasaRefresco;
    }

    // Implementación de polimorfismo para garantía
    @Override
    public int calcularGarantia() {
        // Ejemplo: Los monitores tienen 365 días extra por ser hardware sensible
        return getDuracionGarantiaDias() + 365;
    }
}

