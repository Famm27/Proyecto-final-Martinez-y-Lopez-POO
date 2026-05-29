/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.computosas.dao;

// 1. IMPORTS CLAVE DE JAVA SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 2. IMPORTS DE UTILIDADES
import java.util.ArrayList;
import java.util.List;

// 3. IMPORTS DE TU PROPIO PROYECTO (Modelo)
import com.computosas.modelo.Producto;
import com.computosas.modelo.Monitor;

public class ProductoDAO {

    /**
     * Inserta un nuevo producto en la base de datos de Neon Cloud con los 15 campos completos.
     * @param p Objeto Producto con la información recolectada de la interfaz.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean registrarProducto(Producto p) {
        // Sentencia SQL con los 15 campos exactos de la base de datos
        String sql = "INSERT INTO producto (nombre, numero_serie, descripcion, precio_venta, "
                + "cantidad_actual, cantidad_minima_permitida, es_bajo_pedido, ruta_imagen, "
                + "pais_procedencia, fabricante, peso_gramos, medidas, duracion_garantiadias, "
                + "id_proveedor, tipo_producto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Mapeo de parámetros desde el objeto de negocio hacia los comodines '?'
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getNumeroSerie());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecioVenta());
            ps.setInt(5, p.getCantidadActual());
            ps.setInt(6, p.getCantidadMinimaPermitida());
            ps.setBoolean(7, p.isEsBajoPedido());
            ps.setString(8, p.getRutaImagen());
            ps.setString(9, p.getPaisProcedencia());
            ps.setString(10, p.getFabricante());
            ps.setDouble(11, p.getPesoGramos());
            ps.setString(12, p.getMedidas());
            ps.setInt(13, p.getDuracionGarantiaDias());
            
            // Manejo de llave foránea para el proveedor asociado
            if (p.getIdProveedor() > 0) {
                ps.setInt(14, p.getIdProveedor());
            } else {
                ps.setNull(14, java.sql.Types.INTEGER);
            }
            
            ps.setString(15, p.getTipoProducto());
            
            // Ejecuta la inserción y retorna verdadero si se afectó la fila
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar en el DAO: " + e.getMessage());
            return false;
        }
    }
        
    /**
     * Consulta la tabla completa de productos y los parsea a objetos de Java.
     * @return Lista con todos los productos registrados.
     */
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Instanciamos Monitor pasando los datos de la BD de Neon
                Producto p = new Monitor(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("numero_serie"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio_venta"),
                    rs.getInt("cantidad_actual"),
                    rs.getInt("cantidad_minima_permitida"),
                    rs.getBoolean("es_bajo_pedido"),
                    rs.getString("ruta_imagen"),
                    rs.getString("pais_procedencia"),
                    rs.getString("fabricante"),
                    rs.getDouble("peso_gramos"),
                    rs.getString("medidas"),
                    rs.getInt("duracion_garantiadias"),
                    rs.getInt("id_proveedor"),
                    rs.getString("tipo_producto"),
                    "1080p", 144 // Atributos específicos de Monitor (Dummy para la prueba)
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar desde el DAO: " + e.getMessage());
        }
        return lista;
    }
}