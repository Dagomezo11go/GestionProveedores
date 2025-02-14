package ventanaGUI.producto;

import org.example.gestionproveedores.MenuPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaOpcionesProducto extends JFrame {
    private String usuarioConectado;
    public VentanaOpcionesProducto(String usuarioConectado) {
        this.usuarioConectado = usuarioConectado;

        setTitle("Gestión de Productos");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Botón: Agregar Producto
        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.setBounds(100, 50, 200, 30);
        add(btnAgregar);

        // Acción del botón Agregar Producto
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaGestionarProducto(usuarioConectado).setVisible(true);
                dispose(); // Cierra esta ventana
            }
        });

        // Botón: Modificar Producto
        JButton btnModificar = new JButton("Modificar Producto");
        btnModificar.setBounds(100, 100, 200, 30);
        add(btnModificar);

        // Acción del botón Modificar Producto
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí abrirás la ventana de modificar producto (debes implementarla)
                JOptionPane.showMessageDialog(null, "Modificar Producto: En desarrollo.");
            }
        });

        // Botón: Eliminar Producto
        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.setBounds(100, 150, 200, 30);
        add(btnEliminar);

        // Acción del botón Eliminar Producto
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí abrirás la ventana de eliminar producto (debes implementarla)
                JOptionPane.showMessageDialog(null, "Eliminar Producto: En desarrollo.");
            }
        });

        // Botón: Regresar
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(100, 200, 200, 30);
        add(btnRegresar);

        // Acción del botón Regresar
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Regresar al menú principal
                new MenuPrincipal(usuarioConectado).setVisible(true);
                dispose(); // Cierra esta ventana
            }
        });
    }
}
