package ventanaGUI.producto;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaGestionarProducto extends JFrame {
    private JTextField txtNombreProducto, txtCodigoProducto, txtPrecioProducto, txtStockProducto;
    private JTextArea textAreaProductos;
    private JButton btnRegistrar, btnRegresar;

    public VentanaGestionarProducto(String usuarioConectado) {
        setTitle("Gestionar Productos");
        setSize(600, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Título
        JLabel lblTitulo = new JLabel("Gestión de Productos");
        lblTitulo.setBounds(20, 20, 300, 20);
        add(lblTitulo);

        // Campo: Nombre del Producto
        JLabel lblNombreProducto = new JLabel("Nombre:");
        lblNombreProducto.setBounds(20, 60, 100, 20);
        add(lblNombreProducto);

        txtNombreProducto = new JTextField();
        txtNombreProducto.setBounds(120, 60, 300, 20);
        add(txtNombreProducto);
        txtNombreProducto.addKeyListener(new ValidarCampo(txtNombreProducto, "nombre", txtCodigoProducto));

        // Campo: Código del Producto
        JLabel lblCodigoProducto = new JLabel("Código:");
        lblCodigoProducto.setBounds(20, 100, 100, 20);
        add(lblCodigoProducto);

        txtCodigoProducto = new JTextField();
        txtCodigoProducto.setBounds(120, 100, 300, 20);
        add(txtCodigoProducto);
        txtCodigoProducto.addKeyListener(new ValidarCampo(txtCodigoProducto, "codigo", txtPrecioProducto));

        // Campo: Precio del Producto
        JLabel lblPrecioProducto = new JLabel("Precio:");
        lblPrecioProducto.setBounds(20, 140, 100, 20);
        add(lblPrecioProducto);

        txtPrecioProducto = new JTextField();
        txtPrecioProducto.setBounds(120, 140, 300, 20);
        add(txtPrecioProducto);
        txtPrecioProducto.addKeyListener(new ValidarCampo(txtPrecioProducto, "precio", txtStockProducto));

        // Campo: Stock del Producto
        JLabel lblStockProducto = new JLabel("Stock:");
        lblStockProducto.setBounds(20, 180, 100, 20);
        add(lblStockProducto);

        txtStockProducto = new JTextField();
        txtStockProducto.setBounds(120, 180, 300, 20);
        add(txtStockProducto);
        txtStockProducto.addKeyListener(new ValidarCampo(txtStockProducto, "stock", btnRegistrar));

        // Botón Registrar
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(120, 220, 100, 30);
        add(btnRegistrar);

        // Acción del botón registrar
        btnRegistrar.addActionListener(e -> registrarProducto());

        // Área de texto para mostrar productos
        textAreaProductos = new JTextArea();
        textAreaProductos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaProductos);
        scrollPane.setBounds(20, 270, 550, 250);
        add(scrollPane);

        // Botón Regresar
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBounds(230, 530, 100, 30);
        add(btnRegresar);

        // Acción del botón regresar
        btnRegresar.addActionListener(e -> dispose());

        // Mostrar productos registrados
        mostrarProductos();
    }

    // Método para registrar un producto
    private void registrarProducto() {
        try {
            String nombre = txtNombreProducto.getText().trim();
            String codigo = txtCodigoProducto.getText().trim();
            String precio = txtPrecioProducto.getText().trim();
            String stock = txtStockProducto.getText().trim();

            // Validaciones antes de registrar
            if (nombre.isEmpty() || codigo.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
                throw new Exception("Todos los campos son obligatorios.");
            }

            // Aquí puedes añadir la lógica para guardar el producto en la base de datos
            JOptionPane.showMessageDialog(this, "Producto registrado correctamente.");
            mostrarProductos();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar productos
    private void mostrarProductos() {
        textAreaProductos.setText("Listado de productos registrados.\n");
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        txtNombreProducto.setText("");
        txtCodigoProducto.setText("");
        txtPrecioProducto.setText("");
        txtStockProducto.setText("");
    }

    // Clase interna para validación en tiempo real
    private class ValidarCampo extends KeyAdapter {
        private JTextField campoActual;
        private String tipo;
        private JComponent siguienteCampo;

        public ValidarCampo(JTextField campoActual, String tipo, JComponent siguienteCampo) {
            this.campoActual = campoActual;
            this.tipo = tipo;
            this.siguienteCampo = siguienteCampo;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    String texto = campoActual.getText().trim();
                    switch (tipo) {
                        case "nombre":
                            if (texto.isEmpty() || !texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))
                                throw new Exception("El nombre debe contener solo letras.");
                            break;
                        case "codigo":
                            if (!texto.matches("\\d+"))
                                throw new Exception("El código debe contener solo números.");
                            break;
                        case "precio":
                            if (!texto.matches("\\d+(\\.\\d{1,2})?"))
                                throw new Exception("El precio debe ser un número válido.");
                            break;
                        case "stock":
                            if (!texto.matches("\\d+"))
                                throw new Exception("El stock debe ser un número válido.");
                            break;
                    }
                    // Si no hay errores, enfoca el siguiente campo
                    if (siguienteCampo instanceof JTextField) {
                        ((JTextField) siguienteCampo).requestFocus();
                    } else if (siguienteCampo instanceof JButton) {
                        ((JButton) siguienteCampo).requestFocus();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VentanaGestionarProducto.this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    campoActual.setText("");
                    campoActual.requestFocus();
                }
            }
        }
    }
}
