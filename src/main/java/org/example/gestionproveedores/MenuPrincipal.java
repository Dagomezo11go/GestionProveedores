package org.example.gestionproveedores;

import ventanaGUI.producto.VentanaGestionarProducto;
import ventanaGUI.producto.VentanaOpcionesProducto;
import ventanasGUI.proveedor.proveedorMD.VentanaOpcionesProveedor;

import javax.swing.*;

public class MenuPrincipal extends JFrame {
    private String usuarioConectado;

    public MenuPrincipal(String usuarioConectado) {
        this.usuarioConectado = usuarioConectado;

        setTitle("Menú Principal");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Botón para gestionar proveedores
        JButton btnGestionarProveedores = new JButton("Gestionar Proveedores");
        btnGestionarProveedores.setBounds(100, 100, 200, 30);
        add(btnGestionarProveedores);

        // Acción del botón gestionar proveedores
        btnGestionarProveedores.addActionListener(e -> {
            new VentanaOpcionesProveedor(usuarioConectado).setVisible(true);
            dispose(); // Cerrar esta ventana al abrir la siguiente
        });

        // Botón para gestionar productos
        JButton btnGestionarProductos = new JButton("Gestionar Productos");
        btnGestionarProductos.setBounds(100, 150, 200, 30);
        add(btnGestionarProductos);

        // Acción del botón gestionar productos
        btnGestionarProductos.addActionListener(e -> {
            new VentanaOpcionesProducto(usuarioConectado).setVisible(true);
            dispose(); // Cerrar la ventana actual
        });
    }

    public static void main(String[] args) {
        // Simulación de un usuario conectado
        String usuarioConectado = "admin";

        // Abrir la ventana principal del sistema
        new MenuPrincipal(usuarioConectado).setVisible(true);
    }
}