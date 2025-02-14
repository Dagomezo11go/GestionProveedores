package ventanasGUI.proveedor;

import ventanasGUI.proveedor.proveedorDP.Proveedor;
import ventanasGUI.proveedor.proveedorMD.ProveedorMD;
import ventanasGUI.proveedor.proveedorMD.VentanaOpcionesProveedor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaModificarProveedor extends JFrame {
    private ProveedorMD proveedorMD = new ProveedorMD();
    private JTextField txtBuscar, txtNombre, txtRUC, txtContacto;
    private JButton btnBuscar, btnModificar, btnRegresar;
    private Proveedor proveedorActual;

    public VentanaModificarProveedor(String usuarioConectado) {
        setTitle("Modificar Proveedor");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Campo de búsqueda
        JLabel lblBuscar = new JLabel("Buscar por RUC:");
        lblBuscar.setBounds(20, 20, 100, 20);
        add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(130, 20, 200, 20);
        add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(350, 20, 100, 20);
        add(btnBuscar);

        // Campos para editar
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 20);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 60, 300, 20);
        txtNombre.setEnabled(false); // Deshabilitado hasta que se busque un proveedor
        add(txtNombre);

        JLabel lblRUC = new JLabel("RUC:");
        lblRUC.setBounds(20, 100, 100, 20);
        add(lblRUC);

        txtRUC = new JTextField();
        txtRUC.setBounds(130, 100, 300, 20);
        txtRUC.setEnabled(false);
        add(txtRUC);

        JLabel lblContacto = new JLabel("Contacto:");
        lblContacto.setBounds(20, 140, 100, 20);
        add(lblContacto);

        txtContacto = new JTextField();
        txtContacto.setBounds(130, 140, 300, 20);
        txtContacto.setEnabled(false);
        add(txtContacto);

        // Botón para modificar
        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(200, 200, 100, 30);
        btnModificar.setEnabled(false); // Deshabilitado hasta que se busque un proveedor
        add(btnModificar);

        // Botón para regresar
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(200, 250, 100, 30);
        add(btnRegresar);

        // Acción del botón buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProveedor();
            }
        });

        // Acción del botón modificar
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProveedor();
            }
        });

        // Acción del botón regresar
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaOpcionesProveedor(usuarioConectado).setVisible(true);
                dispose(); // Cerrar esta ventana
            }
        });
    }

    // Método para buscar un proveedor
    private void buscarProveedor() {
        String ruc = txtBuscar.getText().trim();
        if (ruc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un RUC para buscar.");
            return;
        }

        Proveedor proveedor = proveedorMD.obtenerProveedorPorRUC(ruc);
        if (proveedor != null) {
            proveedorActual = proveedor; // Guardar el proveedor actual
            txtNombre.setText(proveedor.getNombre());
            txtRUC.setText(proveedor.getRuc());
            txtContacto.setText(proveedor.getContacto());

            // Habilitar campos y botón modificar
            txtNombre.setEnabled(true);
            txtRUC.setEnabled(true);
            txtContacto.setEnabled(true);
            btnModificar.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un proveedor con el RUC ingresado.");
        }
    }

    // Método para modificar un proveedor
    private void modificarProveedor() {
        if (proveedorActual == null) return;

        String nombre = txtNombre.getText().trim();
        String ruc = txtRUC.getText().trim();
        String contacto = txtContacto.getText().trim();

        // Validar si no se realizó ninguna modificación
        if (nombre.equals(proveedorActual.getNombre()) &&
                ruc.equals(proveedorActual.getRuc()) &&
                contacto.equals(proveedorActual.getContacto())) {
            JOptionPane.showMessageDialog(this, "No ha realizado ninguna modificación.", "Sin Cambios", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Validar campos vacíos
        if (nombre.isEmpty() || ruc.isEmpty() || contacto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Intentar modificar el proveedor en la base de datos
        if (proveedorMD.modificarProveedor(proveedorActual.getIdProveedor(), nombre, ruc, contacto)) {
            JOptionPane.showMessageDialog(this, "Proveedor modificado correctamente.");
            proveedorActual.setNombre(nombre); // Actualizar los valores locales
            proveedorActual.setRuc(ruc);
            proveedorActual.setContacto(contacto);
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
