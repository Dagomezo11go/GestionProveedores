package ventanasGUI.proveedor.proveedorMD;

import org.example.gestionproveedores.MenuPrincipal;
import ventanasGUI.proveedor.VentanaEliminarProveedor;
import ventanasGUI.proveedor.VentanaGestionarProveedor;
import ventanasGUI.proveedor.VentanaModificarProveedor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaOpcionesProveedor extends JFrame {

    public VentanaOpcionesProveedor(String usuarioConectado) {
        setTitle("Opciones de Gestión de Proveedores");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Botón: Registrar Proveedor
        JButton btnRegistrar = new JButton("Registrar Proveedor");
        btnRegistrar.setBounds(100, 50, 200, 30);
        add(btnRegistrar);

        // Acción del botón registrar
        btnRegistrar.addActionListener(e -> {
            new VentanaGestionarProveedor(usuarioConectado).setVisible(true);
            dispose(); // Cerrar esta ventana
        });

        // Botón: Modificar Proveedor
        JButton btnModificar = new JButton("Modificar Proveedor");
        btnModificar.setBounds(100, 100, 200, 30);
        add(btnModificar);

        // Acción del botón modificar
        btnModificar.addActionListener(e -> {
            new VentanaModificarProveedor(usuarioConectado).setVisible(true); // Abrir ventana de modificar proveedor
            dispose(); // Cerrar esta ventana
        });

        // Botón: Eliminar Proveedor
        JButton btnEliminar = new JButton("Eliminar Proveedor");
        btnEliminar.setBounds(100, 150, 200, 30);
        add(btnEliminar);

        // Acción del botón eliminar
        btnEliminar.addActionListener(e -> {
            new VentanaEliminarProveedor(usuarioConectado).setVisible(true);
            dispose(); // Cerrar esta ventana
        });

        // Botón: Regresar al Menú Principal
        JButton btnRegresar = new JButton("Regresar al Menú Principal");
        btnRegresar.setBounds(100, 200, 200, 30);
        add(btnRegresar);

        // Acción del botón regresar
        btnRegresar.addActionListener(e -> {
            new MenuPrincipal(usuarioConectado).setVisible(true);
            dispose(); // Cerrar esta ventana
        });
    }
}
