/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.computosas.modelo;

public abstract class Producto {
    // Atributos privados (Encapsulamiento)
    private int idProducto;
    private String nombre;
    private String numeroSerie;
    private String descripcion;
    private double precioVenta;
    private int cantidadActual;
    private int cantidadMinimaPermitida;
    private boolean esBajoPedido;
    private String rutaImagen;
    private String paisProcedencia;
    private String fabricante;
    private double pesoGramos;
    private String medidas;
    private int duracionGarantiaDias;
    private int idProveedor;
    private String tipoProducto;

   
    public Producto(int idProducto, String nombre, String numeroSerie, String descripcion, 
                    double precioVenta, int cantidadActual, int cantidadMinimaPermitida, 
                    boolean esBajoPedido, String rutaImagen, String paisProcedencia, 
                    String fabricante, double pesoGramos, String medidas, 
                    int duracionGarantiaDias, int idProveedor, String tipoProducto) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.numeroSerie = numeroSerie;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.cantidadActual = cantidadActual;
        this.cantidadMinimaPermitida = cantidadMinimaPermitida;
        this.esBajoPedido = esBajoPedido;
        this.rutaImagen = rutaImagen;
        this.paisProcedencia = paisProcedencia;
        this.fabricante = fabricante;
        this.pesoGramos = pesoGramos;
        this.medidas = medidas;
        this.duracionGarantiaDias = duracionGarantiaDias;
        this.idProveedor = idProveedor;
        this.tipoProducto = tipoProducto;
    }

    // Método abstracto 
    public abstract int calcularGarantia();

    // Métodos Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(int cantidadActual) { this.cantidadActual = cantidadActual; }

    public int getCantidadMinimaPermitida() { return cantidadMinimaPermitida; }
    public void setCantidadMinimaPermitida(int cantidadMinimaPermitida) { this.cantidadMinimaPermitida = cantidadMinimaPermitida; }

    public boolean isEsBajoPedido() { return esBajoPedido; }
    public void setEsBajoPedido(boolean esBajoPedido) { this.esBajoPedido = esBajoPedido; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public String getPaisProcedencia() { return paisProcedencia; }
    public void setPaisProcedencia(String paisProcedencia) { this.paisProcedencia = paisProcedencia; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public double getPesoGramos() { return pesoGramos; }
    public void setPesoGramos(double pesoGramos) { this.pesoGramos = pesoGramos; }

    public String getMedidas() { return medidas; }
    public void setMedidas(String medidas) { this.medidas = medidas; }

    public int getDuracionGarantiaDias() { return duracionGarantiaDias; }
    public void setDuracionGarantiaDias(int duracionGarantiaDias) { this.duracionGarantiaDias = duracionGarantiaDias; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public String getTipoProducto() { return tipoProducto; }
    public void setTipoProducto(String tipoProducto) { this.tipoProducto = tipoProducto; }
}