package com.computosas.modelo;

import java.util.ArrayList;
import java.util.List;

public class Venta {
    private String folio;
    private String fechaVenta; // CORREGIDO: Cambiado a String para sincronizar con la interfaz
    private String horaVenta;  // CORREGIDO: Cambiado a String para sincronizar con la interfaz
    private String estatusVenta; // "PENDIENTE" o "ENTREGADA"
    private List<Producto> productosComprados; // Colección exigida por los lineamientos
    private double subtotalSinIVA; 
    private double totalConIVA;
    private int idCliente;

    // CONSTRUCTOR VACÍO OBLIGATORIO: Corrige el fallo del 'new Venta()' en la vista
    public Venta() {
        this.productosComprados = new ArrayList<>();
        this.estatusVenta = "PENDIENTE";
    }

    // Constructor Completo adaptado
    public Venta(String folio, String fechaVenta, String horaVenta, String estatusVenta, double subtotalSinIVA, double totalConIVA, int idCliente) {
        this.folio = folio;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.estatusVenta = estatusVenta;
        this.subtotalSinIVA = subtotalSinIVA;
        this.totalConIVA = totalConIVA;
        this.idCliente = idCliente;
        this.productosComprados = new ArrayList<>(); 
    }

    // Método de lógica de negocio para agregar productos
    public void agregarProducto(Producto producto) {
        this.productosComprados.add(producto);
    }

    // Getters y Setters corregidos
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }

    public String getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(String fechaVenta) { this.fechaVenta = fechaVenta; }

    public String getHoraVenta() { return horaVenta; }
    public void setHoraVenta(String horaVenta) { this.horaVenta = horaVenta; }

    public String getEstatusVenta() { return estatusVenta; }
    public void setEstatusVenta(String estatusVenta) { this.estatusVenta = estatusVenta; }

    public List<Producto> getProductosComprados() { return productosComprados; }
    public void setProductosComprados(List<Producto> productosComprados) { this.productosComprados = productosComprados; }

    public double getSubtotalSinIVA() { return subtotalSinIVA; }
    public void setSubtotalSinIVA(double subtotalSinIVA) { this.subtotalSinIVA = subtotalSinIVA; }

    public double getTotalConIVA() { return totalConIVA; }
    public void setTotalConIVA(double totalConIVA) { this.totalConIVA = totalConIVA; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
}