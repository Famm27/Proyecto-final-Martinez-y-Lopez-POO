package com.computosas.dao;

// 1. IMPORTS DE LA API SQL DE JAVA
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 2. IMPORTS DE UTILIDADES
import java.util.ArrayList;
import java.util.List;

// 3. IMPORT DE TU MODELO
import com.computosas.modelo.Cliente;

/**
 * Clase Data Access Object (DAO) para gestionar las operaciones CRUD completas
 * de la tabla 'cliente' en la base de datos PostgreSQL de Neon Cloud.
 * Incluye soporte para el sistema de seguridad por contraseña.
 */
public class ClienteDao {
    
    // Consultas SQL actualizadas con el campo 'contrasena'
    private final String SQL_INSERT = "INSERT INTO cliente (id_cliente, nombre, direccion, telefono, correo_electronico, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQL_SELECT = "SELECT * FROM cliente";
    private final String SQL_UPDATE = "UPDATE cliente SET nombre = ?, direccion = ?, telefono = ?, correo_electronico = ?, contrasena = ? WHERE id_cliente = ?";
    private final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente = ?";

    /**
     * Constructor por defecto de la clase ClienteDao.
     */
    public ClienteDao() {
    }

    /**
     * C (Create): Registra un nuevo cliente de forma permanente en Neon Cloud.
     * @param c Objeto Cliente con los datos de la vista.
     * @return true si guardó con éxito, false en caso de error.
     */
    public boolean registrarCliente(Cliente c) {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            
            ps.setInt(1, c.getIdCliente()); 
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreoElectronico());
            ps.setString(6, c.getContrasena()); // Guardamos la contraseña en la BD
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar cliente en el DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * R (Read): Consulta todos los registros de la tabla cliente en la nube.
     * @return List de tipo Cliente.
     */
    public List<Cliente> listarClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo_electronico")
                );
                // Si tu constructor de Cliente no recibe contraseña, se la inyectamos por el setter:
                c.setContrasena(rs.getString("contrasena"));
                listaClientes.add(c);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar clientes en el DAO: " + e.getMessage());
        }
        
        return listaClientes;
    }

    /**
     * U (Update): Actualiza los datos de un cliente existente en la base de datos.
     * @param c Objeto Cliente con las modificaciones hechas en el formulario.
     * @return true si se actualizó correctamente, false en caso de error.
     */
    public boolean actualizarCliente(Cliente c) {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDireccion());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreoElectronico());
            ps.setString(5, c.getContrasena()); // Actualiza también la clave si cambia en gestión
            ps.setInt(6, c.getIdCliente()); 
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente en el DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * D (Delete): Elimina permanentemente un registro de cliente por su ID.
     * @param idCliente Identificador único del cliente a remover.
     * @return true si se borró con éxito, false en caso de error.
     */
    public boolean eliminarCliente(int idCliente) {
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            
            ps.setInt(1, idCliente); 
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente en el DAO: " + e.getMessage());
            return false;
        }
    }

    /**
     * BUSCAR POR ID: Busca un cliente específico por su ID único para extraer sus datos en la venta.
     * @param idCliente Identificador único del cliente.
     * @return Objeto Cliente poblado o null si no se encuentra.
     */
    public Cliente buscarClientePorId(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo_electronico")
                    );
                    c.setContrasena(rs.getString("contrasena"));
                    return c;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por ID en el DAO: " + e.getMessage());
        }
        return null;
    }

    /**
     * VERIFICAR EXISTENCIA: Busca si el ID existe y retorna el nombre del cliente.
     * @param idCliente Identificador único.
     * @return El nombre del cliente si existe, o null si no está registrado.
     */
    public String verificarCliente(int idCliente) {
        String sql = "SELECT nombre FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar cliente en el DAO: " + e.getMessage());
        }
        return null;
    }

    /**
     * VALIDAR LOGIN: Cruza el ID con la contraseña ingresada en la UI.
     * @param idCliente Identificador numérico del cliente (Cédula o NIT).
     * @param contrasena Clave escrita en el JPasswordField.
     * @return true si coinciden los datos en Neon Cloud, false en caso contrario.
     */
    public boolean validarLoginCliente(int idCliente, String contrasena) {
        String sql = "SELECT id_cliente FROM cliente WHERE id_cliente = ? AND contrasena = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            ps.setString(2, contrasena);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Retorna verdadero si hay coincidencia exacta
            }
        } catch (SQLException e) {
            System.err.println("Error al validar credenciales de cliente: " + e.getMessage());
            return false;
        }
    }
}