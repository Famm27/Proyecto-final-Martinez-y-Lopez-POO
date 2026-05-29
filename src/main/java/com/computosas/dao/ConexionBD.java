/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.computosas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    // URL Corregida: Incluye el '-pooler.c-7' obligatorio por tener el Pooling activo en Neon
    private static final String URL = "jdbc:postgresql://ep-plain-poetry-ap5th9uv-pooler.c-7.us-east-1.aws.neon.tech/computosas?sslmode=require";
    
    // Credenciales de acceso oficiales sacadas de tu consola
    private static final String USUARIO = "neondb_owner";
    private static final String CONTRASENA = "npg_vKj8LQmzo6Zd";

    public static Connection obtenerConexion() throws SQLException {
        try {
            // Registrar explícitamente el driver de PostgreSQL compatible con Java
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error crítico: No se encontró el Driver JDBC en las librerías.", e);
        } catch (SQLException e) {
            throw new SQLException("Error de conexión: No se pudo conectar a la base de datos de Neon. Verifique credenciales o red.", e);
        }
    }
}