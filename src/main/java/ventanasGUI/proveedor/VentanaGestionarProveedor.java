package ventanasGUI.proveedor;

import ventanasGUI.proveedor.proveedorDP.Proveedor;
import ventanasGUI.proveedor.proveedorMD.ProveedorMD;
import ventanasGUI.proveedor.proveedorMD.VentanaOpcionesProveedor;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class VentanaGestionarProveedor extends JFrame {
    private ProveedorMD proveedorMD = new ProveedorMD();
    private JTextField txtNombre, txtRUC, txtContacto;
    private JTextArea textArea;
    private JButton btnAgregar, btnRegresar;

    public VentanaGestionarProveedor(String usuarioConectado) {
        setTitle("Gestionar Proveedores");
        setSize(600, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Título
        JLabel lblTitulo = new JLabel("Agregar Proveedor");
        lblTitulo.setBounds(20, 20, 300, 20);
        add(lblTitulo);

        // Campo: Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 20);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 60, 300, 20);
        add(txtNombre);
        txtNombre.addKeyListener(new ValidarCampoListener(txtNombre, "nombre"));

        // Campo: RUC
        JLabel lblRUC = new JLabel("RUC:");
        lblRUC.setBounds(20, 100, 100, 20);
        add(lblRUC);

        txtRUC = new JTextField();
        txtRUC.setBounds(120, 100, 300, 20);
        add(txtRUC);
        txtRUC.addKeyListener(new ValidarCampoListener(txtRUC, "ruc"));

        // Campo: Contacto
        JLabel lblContacto = new JLabel("Contacto:");
        lblContacto.setBounds(20, 140, 100, 20);
        add(lblContacto);

        txtContacto = new JTextField();
        txtContacto.setBounds(120, 140, 300, 20);
        add(txtContacto);
        txtContacto.addKeyListener(new ValidarCampoListener(txtContacto, "contacto"));

        // Botón para agregar
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(200, 180, 100, 30);
        add(btnAgregar);

        // Acción del botón agregar
        btnAgregar.addActionListener(e -> agregarProveedor(usuarioConectado));

        // Área de texto para mostrar proveedores
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 230, 550, 250);
        add(scrollPane);

        // Botón para regresar
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(200, 500, 100, 30);
        add(btnRegresar);

        // Acción del botón regresar
        btnRegresar.addActionListener(e -> {
            new VentanaOpcionesProveedor(usuarioConectado).setVisible(true);
            dispose();
        });

        // Mostrar proveedores registrados al iniciar
        mostrarProveedores();
    }

    // Método para agregar un proveedor
    private void agregarProveedor(String usuarioConectado) {
        try {
            String nombre = txtNombre.getText().trim();
            String ruc = txtRUC.getText().trim();
            String contacto = txtContacto.getText().trim();

            // Validaciones
            if (nombre.isEmpty() || ruc.isEmpty() || contacto.isEmpty()) {
                throw new Exception("Todos los campos son obligatorios.");
            }
            if (!ruc.matches("\\d+")) {
                throw new Exception("El RUC solo debe contener números.");
            }
            if (!contacto.matches("\\d+")) {
                throw new Exception("El Contacto solo debe contener números.");
            }

            // Crear proveedor e insertar
            Proveedor proveedor = new Proveedor(0, nombre, ruc, contacto, null, usuarioConectado);
            if (proveedorMD.insertarProveedor(proveedor, usuarioConectado)) {
                JOptionPane.showMessageDialog(this, "Proveedor agregado correctamente.");
                mostrarProveedores();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar proveedor.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar los proveedores en el área de texto
    private void mostrarProveedores() {
        List<Proveedor> proveedores = proveedorMD.obtenerProveedores();
        StringBuilder texto = new StringBuilder();
        for (Proveedor p : proveedores) {
            texto.append("ID: ").append(p.getIdProveedor())
                    .append(", Nombre: ").append(p.getNombre())
                    .append(", RUC: ").append(p.getRuc())
                    .append(", Contacto: ").append(p.getContacto())
                    .append(", Registrado por: ").append(p.getRegistradoPor())
                    .append("\n");
        }
        textArea.setText(texto.toString());
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        txtNombre.setText("");
        txtRUC.setText("");
        txtContacto.setText("");
    }

    // Clase interna para validación en tiempo real
    private class ValidarCampoListener extends KeyAdapter {
        private JTextField campo;
        private String tipo;

        public ValidarCampoListener(JTextField campo, String tipo) {
            this.campo = campo;
            this.tipo = tipo;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    String texto = campo.getText().trim();
                    if (tipo.equals("nombre") && !texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        throw new Exception("El Nombre solo debe contener letras y espacios.");
                    } else if (tipo.equals("ruc") && (!texto.matches("\\d+") || texto.isEmpty())) {
                        throw new Exception("El campo RUC debe contener solo números.");
                    } else if (tipo.equals("contacto") && (!texto.matches("\\d+") || texto.isEmpty())) {
                        throw new Exception("El campo Contacto debe contener solo números.");
                    }
                    campo.transferFocus(); // Pasar al siguiente campo
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VentanaGestionarProveedor.this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    campo.setText("");
                }
            }
        }
    }
}