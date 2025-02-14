package ventanasGUI.proveedor;

import ventanasGUI.proveedor.proveedorDP.Proveedor;
import ventanasGUI.proveedor.proveedorMD.ProveedorMD;
import ventanasGUI.proveedor.proveedorMD.VentanaOpcionesProveedor;

import javax.swing.*;
import java.util.List;

public class VentanaEliminarProveedor extends JFrame {
    private ProveedorMD proveedorMD = new ProveedorMD();
    private JTextField txtBuscar;
    private JButton btnBuscar, btnEliminar, btnRegresar;
    private JTextArea txtListadoProveedores;

    public VentanaEliminarProveedor(String usuarioConectado) {
        setTitle("Eliminar Proveedor");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Campo de búsqueda
        JLabel lblBuscar = new JLabel("Ingrese el RUC:");
        lblBuscar.setBounds(20, 20, 100, 20);
        add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(130, 20, 200, 20);
        add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(350, 20, 100, 30);
        add(btnBuscar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 60, 100, 30);
        btnEliminar.setEnabled(false); // Deshabilitado hasta buscar un proveedor
        add(btnEliminar);

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(350, 100, 100, 30);
        add(btnRegresar);

        // Listado de proveedores
        JLabel lblListado = new JLabel("Listado de Proveedores:");
        lblListado.setBounds(20, 60, 200, 20);
        add(lblListado);

        txtListadoProveedores = new JTextArea();
        txtListadoProveedores.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtListadoProveedores);
        scrollPane.setBounds(20, 80, 300, 250);
        add(scrollPane);

        // Acción del botón Buscar
        btnBuscar.addActionListener(e -> buscarProveedor());

        // Acción del botón Eliminar
        btnEliminar.addActionListener(e -> eliminarProveedor());

        // Acción del botón Regresar
        btnRegresar.addActionListener(e -> {
            new VentanaOpcionesProveedor(usuarioConectado).setVisible(true);
            dispose();
        });

        // Mostrar listado de proveedores al iniciar
        mostrarProveedores();
    }

    // Mostrar todos los proveedores
    private void mostrarProveedores() {
        List<Proveedor> proveedores = proveedorMD.obtenerProveedores();
        txtListadoProveedores.setText(""); // Limpiar el área de texto
        for (Proveedor proveedor : proveedores) {
            txtListadoProveedores.append("RUC: " + proveedor.getRuc() + ", Nombre: " + proveedor.getNombre() + "\n");
        }
    }

    // Buscar un proveedor por RUC
    private void buscarProveedor() {
        String ruc = txtBuscar.getText().trim();
        if (ruc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un RUC para buscar.");
            return;
        }

        Proveedor proveedor = proveedorMD.obtenerProveedorPorRUC(ruc);
        if (proveedor != null) {
            JOptionPane.showMessageDialog(this, "Proveedor encontrado: " + proveedor.getNombre());
            btnEliminar.setEnabled(true); // Habilitar botón eliminar
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un proveedor con el RUC ingresado.");
            btnEliminar.setEnabled(false);
        }
    }

    // Eliminar proveedor
    private void eliminarProveedor() {
        String ruc = txtBuscar.getText().trim();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar el proveedor con RUC: " + ruc + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (proveedorMD.eliminarProveedorPorRUC(ruc)) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
                btnEliminar.setEnabled(false); // Deshabilitar el botón eliminar
                mostrarProveedores(); // Actualizar el listado
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor.");
            }
        }
    }
}