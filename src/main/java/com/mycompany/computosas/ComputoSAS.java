/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.computosas;

import com.formdev.flatlaf.FlatDarkLaf;
import com.computosas.vista.LoginView; // Importamos el Login para poder lanzarlo
import javax.swing.UIManager;

/**
 * Clase Principal de Arranque - Cómputo S.A.S.
 * @author ASUS
 */
public class ComputoSAS {
    public static void main(String[] args) {
        try {
            // Activamos el tema oscuro moderno
            FlatDarkLaf.setup();
            
            // Opcional: Personalizar el redondeo de los botones y componentes
            UIManager.put("Button.arc", 999);
            UIManager.put("Component.arc", 15);
            
        } catch (Exception ex) {
            System.err.println("Fallo al inicializar el tema visual.");
        }

        // Lanzamos la interfaz en el hilo de eventos de Swing apuntando al Login
        java.awt.EventQueue.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setLocationRelativeTo(null); // Centra la ventana en medio de la pantalla
            login.setVisible(true); // Muestra la ventana de login
        });
    }
}