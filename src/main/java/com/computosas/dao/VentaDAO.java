package com.computosas.dao;

import com.computosas.modelo.Venta;
import com.computosas.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // 1. REGISTRAR UNA VENTA (Corregido con id_producto)
    public boolean registrarVenta(Venta venta) {
        // Estructura idéntica a tu consola Neon:
        // 'ventas' usa 'folio'
        // 'detalle_ventas' usa 'folio_venta' e 'id_producto'
        String sqlVenta = "INSERT INTO ventas (folio, fecha_venta, hora_venta, estatus_venta, subtotal_sin_iva, total_con_iva, id_cliente) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (folio_venta, id_producto) VALUES (?, ?)";
        
        Connection con = null;
        PreparedStatement psVenta = null;
        PreparedStatement psDetalle = null;

        try {
            con = ConexionBD.obtenerConexion(); 
            if (con == null) {
                javax.swing.JOptionPane.showMessageDialog(null, "Error: ¡No hay conexión con Neon!", "Error de Conexión", javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            con.setAutoCommit(false); // Transacción segura

            // Insertar la cabecera de la venta
            psVenta = con.prepareStatement(sqlVenta);
            psVenta.setString(1, venta.getFolio());
            
            // Conversión de fecha
            try {
                psVenta.setDate(2, java.sql.Date.valueOf(venta.getFechaVenta()));
            } catch (Exception e) {
                try {
                    java.util.Date fechaParsed = new SimpleDateFormat("dd/MM/yyyy").parse(venta.getFechaVenta());
                    psVenta.setDate(2, new java.sql.Date(fechaParsed.getTime()));
                } catch (Exception ex) {
                    psVenta.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                }
            }
            
            // Conversión de hora
            try {
                psVenta.setTime(3, java.sql.Time.valueOf(venta.getHoraVenta()));
            } catch (Exception e) {
                psVenta.setTime(3, new java.sql.Time(System.currentTimeMillis()));
            }
            
            psVenta.setString(4, venta.getEstatusVenta());
            psVenta.setDouble(5, venta.getSubtotalSinIVA());
            psVenta.setDouble(6, venta.getTotalConIVA());
            psVenta.setInt(7, venta.getIdCliente());
            
            psVenta.executeUpdate();

            // Insertar los productos en detalle_ventas con la columna correcta 'id_producto'
            psDetalle = con.prepareStatement(sqlDetalle);
            for (Producto prod : venta.getProductosComprados()) {
                psDetalle.setString(1, venta.getFolio());
                psDetalle.setInt(2, prod.getIdProducto()); // Asegúrate de que este método devuelva el ID entero (ej. 2, 8, 3)
                psDetalle.executeUpdate();
            }

            con.commit(); // Guardar todo permanentemente en Neon
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Respuesta del Servidor Neon:\n" + e.getMessage(), 
                "Detalle del Error SQL", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (psVenta != null) psVenta.close();
                if (psDetalle != null) psDetalle.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 2. LISTAR TODAS LAS VENTAS
    public List<Venta> listarTodasLasVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT folio, fecha_venta, hora_venta, id_cliente, total_con_iva, subtotal_sin_iva, estatus_venta FROM ventas ORDER BY folio DESC";
        
        try (Connection con = ConexionBD.obtenerConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            
            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
            
            while (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("fecha_venta");
                java.sql.Time sqlTime = rs.getTime("hora_venta");
                
                String fechaString = (sqlDate != null) ? sdfFecha.format(sqlDate) : "";
                String horaString = (sqlTime != null) ? sdfHora.format(sqlTime) : "";
                
                Venta v = new Venta(
                    rs.getString("folio"),
                    fechaString,
                    horaString,
                    rs.getString("estatus_venta"),
                    rs.getDouble("subtotal_sin_iva"),
                    rs.getDouble("total_con_iva"),
                    rs.getInt("id_cliente")
                );
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error en listarTodasLasVentas: " + e.getMessage());
        }
        return lista;
    }

    public List<Venta> listarVentas() {
        return listarTodasLasVentas();
    }

    // 3. HISTORIAL POR CLIENTE
    public List<String[]> obtenerHistorialPorCliente(int idCliente) {
        List<String[]> historial = new ArrayList<>();
        String sql = "SELECT folio, fecha_venta, estatus_venta, total_con_iva FROM ventas WHERE id_cliente = ? ORDER BY folio DESC";
        SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
        
        try (Connection con = ConexionBD.obtenerConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idCliente);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String[] fila = new String[4];
                    fila[0] = rs.getString("folio");
                    java.sql.Date sqlDate = rs.getDate("fecha_venta");
                    fila[1] = (sqlDate != null) ? sdfFecha.format(sqlDate) : "";
                    fila[2] = rs.getString("estatus_venta");
                    fila[3] = "$" + rs.getDouble("total_con_iva");
                    historial.add(fila);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerHistorialPorCliente: " + e.getMessage());
        }
        return historial;
    }

    // 4. METRICAS GERENCIALES
    public java.util.Map<String, Object> obtenerMetricasGerenciales() {
        java.util.Map<String, Object> metricas = new java.util.HashMap<>();
        metricas.put("total_ingresos", 0.0);
        metricas.put("total_ventas", 0);
        metricas.put("ventas_completadas", 0);
        metricas.put("ventas_canceladas", 0);

        String sql = "SELECT " +
                     "  COALESCE(SUM(total_con_iva), 0) AS ingresos_totales, " +
                     "  COUNT(*) AS cantidad_ventas, " +
                     "  COUNT(CASE WHEN UPPER(estatus_venta) IN ('ENTREGADA', 'COMPLETADA') THEN 1 END) AS completadas, " +
                     "  COUNT(CASE WHEN UPPER(estatus_venta) = 'CANCELADA' THEN 1 END) AS canceladas " +
                     "FROM ventas";

        try (Connection con = ConexionBD.obtenerConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                metricas.put("total_ingresos", rs.getDouble("ingresos_totales"));
                metricas.put("total_ventas", rs.getInt("cantidad_ventas"));
                metricas.put("ventas_completadas", rs.getInt("completadas"));
                metricas.put("ventas_canceladas", rs.getInt("canceladas"));
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerMetricasGerenciales: " + e.getMessage());
        }
        return metricas;
    }

    // 5. ACTUALIZAR ESTADO DE VENTA
    public boolean actualizarEstadoVenta(String folio, String nuevoEstado) {
        String sql = "UPDATE ventas SET estatus_venta = ? WHERE folio = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setString(2, folio);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en actualizarEstadoVenta: " + e.getMessage());
            return false;
        }
    }
}