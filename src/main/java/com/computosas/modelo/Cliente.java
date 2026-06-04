package com.computosas.modelo;

/**
 * Clase Modelo que representa la entidad Cliente.
 */
public class Cliente {

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    // Atributos obligatorios según la guía
    private int idCliente;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String contrasena;

    // Constructor Vacío
    public Cliente() {
    }

    // Constructor Completo
    public Cliente(int idCliente, String nombre, String direccion, String telefono, String correoElectronico) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    // ==========================================
    // GETTERS Y SETTERS (Mantén los tuyos tal cual)
    // ==========================================
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
}