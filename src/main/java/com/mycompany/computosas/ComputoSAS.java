package com.mycompany.computosas;

import com.formdev.flatlaf.FlatDarkLaf;
import com.computosas.vista.LoginClienteView; // <-- ¡ESTE ES EL IMPORT CLAVE QUE FALTA!
import javax.swing.UIManager;

/**
 * Clase Principal de Arranque - Cómputo S.A.S.
 * @author ASUS
 */
public class ComputoSAS {
    public static void main(String[] args) {
        
        // 1. Configuración del tema visual oscuro
        try {
            FlatDarkLaf.setup();
            
            // Estilos de redondeado para los componentes
            UIManager.put("Button.arc", 999);         
            UIManager.put("Component.arc", 15);       
            UIManager.put("TextComponent.arc", 15);   
            
        } catch (Exception ex) {
            System.err.println("Fallo al inicializar el tema visual FlatDarkLaf.");
        }

        // 2. Lanzamos la interfaz de clientes (LoginClienteView)
        java.awt.EventQueue.invokeLater(() -> {
            try {
                LoginClienteView clienteLogin = new LoginClienteView(); // <-- Ya no saldrá en rojo
                clienteLogin.setLocationRelativeTo(null); // Centra la ventana
                clienteLogin.setVisible(true);            // Muestra el login de clientes
            } catch (Exception e) {
                System.err.println("Error al lanzar LoginClienteView: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}