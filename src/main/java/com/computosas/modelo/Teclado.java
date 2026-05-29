/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.computosas.modelo;

public class Teclado extends Producto {
    private String tipoSwitch;

    public Teclado(int idProducto, String nombre, String numeroSerie, String descripcion, 
                    double precioVenta, int cantidadActual, int cantidadMinimaPermitida, 
                    boolean esBajoPedido, String rutaImagen, String paisProcedencia, 
                    String fabricante, double pesoGramos, String medidas, 
                    int duracionGarantiaDias, int idProveedor, String tipoProducto,
                    String tipoSwitch) {
        super(idProducto, nombre, numeroSerie, descripcion, precioVenta, cantidadActual, 
              cantidadMinimaPermitida, esBajoPedido, rutaImagen, paisProcedencia, 
              fabricante, pesoGramos, medidas, duracionGarantiaDias, idProveedor, tipoProducto);
        this.tipoSwitch = tipoSwitch;
    }

    @Override
    public int calcularGarantia() {
        // Ejemplo: Los teclados tienen 180 días extra por desgaste mecánico
        return getDuracionGarantiaDias() + 180;
    }
}