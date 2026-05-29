/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.computosas.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private String folio;
    private LocalDate fechaVenta;
    private LocalTime horaVenta;
    private String estatusVenta; // "PENDIENTE" o "ENTREGADA"
    private List<Producto> productosComprados; // Colección exigida por los lineamientos [cite: 73]
    private double subtotalSinIVA; // Corregido: Ya no tiene errores de escritura
    private double totalConIVA;
    private int idCliente;

    // Constructor Completo
    public Venta(String folio, LocalDate fechaVenta, LocalTime horaVenta, String estatusVenta, double subtotalSinIVA, double totalConIVA, int idCliente) {
        this.folio = folio;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.estatusVenta = estatusVenta;
        this.subtotalSinIVA = subtotalSinIVA;
        this.totalConIVA = totalConIVA;
        this.idCliente = idCliente;
        this.productosComprados = new ArrayList<>(); // Inicialización obligatoria de la lista
    }

    // Método de lógica de negocio para agregar productos al carrito de la venta
    public void agregarProducto(Producto producto) {
        this.productosComprados.add(producto);
    }

    // Getters y Setters
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(LocalTime horaVenta) {
        this.horaVenta = horaVenta;
    }

    public String getEstatusVenta() {
        return estatusVenta;
    }

    public void setEstatusVenta(String estatusVenta) {
        this.estatusVenta = estatusVenta;
    }

    public List<Producto> getProductosComprados() {
        return productosComprados;
    }

    public void setProductosComprados(List<Producto> productosComprados) {
        this.productosComprados = productosComprados;
    }

    public double getSubtotalSinIVA() {
        return subtotalSinIVA;
    }

    public void setSubtotalSinIVA(double subtotalSinIVA) {
        this.subtotalSinIVA = subtotalSinIVA;
    }

    public double getTotalConIVA() {
        return totalConIVA;
    }

    public void setTotalConIVA(double totalConIVA) {
        this.totalConIVA = totalConIVA;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}