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

/**
 * Clase Data Access Object (DAO) para gestionar las operaciones CRUD 
 * de la tabla 'producto' en la base de datos Neon Cloud.
 */
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
     * Consulta la tabla completa de productos y los convierte a objetos de Java.
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

    /**
     * Elimina un producto físico de la base de datos de Neon Cloud usando su ID único.
     * @param id El identificador del producto (llave primaria).
     * @return true si la eliminación afectó filas reales, false si falló.
     */
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id); // Pasamos el identificador al marcador '?'
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar en el DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * ACTUALIZAR / MODIFICAR: Reemplaza los valores de un producto en Neon Cloud.
     * @param p Objeto Producto (o hijo Monitor) con los nuevos datos ingresados en el formulario.
     * @return true si la consulta modificó exitosamente la fila en PostgreSQL.
     */
    public boolean actualizarProducto(Producto p) {
        String sql = "UPDATE producto SET nombre=?, numero_serie=?, descripcion=?, precio_venta=?, "
                + "cantidad_actual=?, cantidad_minima_permitida=?, es_bajo_pedido=?, ruta_imagen=?, "
                + "pais_procedencia=?, fabricante=?, peso_gramos=?, medidas=?, duracion_garantiadias=?, "
                + "id_proveedor=?, tipo_producto=? WHERE id_producto=?";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
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
            
            if (p.getIdProveedor() > 0) {
                ps.setInt(14, p.getIdProveedor());
            } else {
                ps.setNull(14, java.sql.Types.INTEGER);
            }
            
            ps.setString(15, p.getTipoProducto());
            ps.setInt(16, p.getIdProducto()); // Cláusula WHERE id_producto = ?
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar en el DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * BUSCAR POR ID: Recupera el 100% de la ficha técnica de un producto desde Neon Cloud
     * para rellenar de golpe los TextFields vacíos en la interfaz gráfica.
     * @param idProducto Llave primaria del producto seleccionado en la tabla.
     * @return Objeto Monitor (Cast a Producto) listo con todos sus atributos poblados.
     */
    public Producto buscarProductoPorId(int idProducto) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idProducto);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Instanciamos el objeto con la estructura completa de tu BD
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
                        "1080p", 144 // Valores dummy por herencia de Monitor
                    );
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto por ID en el DAO: " + e.getMessage());
        }
        return null;
    }

    /**
     * DESCONTAR INVENTARIO: Disminuye la cantidad disponible de un producto tras una venta.
     * @param idProducto Identificador del producto comprado.
     * @param cantidad Cantidad de unidades a restar de la bodega.
     * @return true si el inventario se actualizó correctamente.
     */
    public boolean restarStock(int idProducto, int cantidad) {
        String sql = "UPDATE producto SET cantidad_actual = cantidad_actual - ? WHERE id_producto = ?";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al restar stock en el DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * CONSULTAR STOCK ACTUAL: Devuelve las existencias reales directamente de Neon Cloud
     * para alimentar el motor de alarmas y validaciones en la interfaz de venta.
     * @param idProducto Identificador único del producto.
     * @return Cantidad de piezas disponibles en bodega (cantidad_actual).
     */
    public int obtenerStockActual(int idProducto) {
        String sql = "SELECT cantidad_actual FROM producto WHERE id_producto = ?";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idProducto);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad_actual");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el stock actual en el DAO: " + e.getMessage());
        }
        return 0; // Retorna 0 si hay una excepción o el producto no existe
    }
}