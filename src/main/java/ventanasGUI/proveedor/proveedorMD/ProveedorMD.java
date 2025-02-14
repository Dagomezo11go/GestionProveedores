package ventanasGUI.proveedor.proveedorMD;

import ventanasGUI.proveedor.proveedorDP.Proveedor;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorMD {
    private Connection conn;

    public ProveedorMD() {
        try {
            String url = "jdbc:postgresql://localhost:5432/GestionProveedores";
            String user = "admin";
            String password = "admin123";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            conn = null;
        }
    }

    // Obtener el rol del usuario
    public String obtenerRolUsuario(String username) {
        if (conn == null) return null;

        String query = "SELECT r.nombrerol FROM usuarios u JOIN roles r ON u.idrol = r.idrol WHERE u.nombreusuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombrerol");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el rol del usuario: " + e.getMessage());
        }
        return null;
    }

    // Obtener lista de proveedores
    public List<Proveedor> obtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        if (conn == null) return proveedores;

        String query = "SELECT p.idproveedor, p.nombre, p.ruc, p.contacto, p.fecharegistro, u.nombreusuario AS registrado_por " +
                "FROM proveedores p JOIN usuarios u ON p.registradopor = u.idusuario";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                        rs.getInt("idproveedor"),
                        rs.getString("nombre"),
                        rs.getString("ruc"),
                        rs.getString("contacto"),
                        rs.getString("fecharegistro"),
                        rs.getString("registrado_por")
                );
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
        }
        return proveedores;
    }

    public boolean insertarProveedor(Proveedor proveedor, String usuarioConectado) {
        if (conn == null) {
            System.err.println("Conexión a la base de datos no establecida.");
            return false;
        }

        int idUsuario = obtenerIDUsuario(usuarioConectado);
        if (idUsuario == -1) {
            JOptionPane.showMessageDialog(null, "Usuario no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (existeProveedor(proveedor.getNombre(), proveedor.getRuc())) {
            JOptionPane.showMessageDialog(null, "El proveedor ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String query = "INSERT INTO proveedores (nombre, ruc, contacto, fecharegistro, registradopor) VALUES (?, ?, ?, CURRENT_DATE, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getRuc());
            stmt.setString(3, proveedor.getContacto());
            stmt.setInt(4, idUsuario);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Filas insertadas: " + rowsAffected);

            // Reasignar IDs después de la inserción
            reasignarIDs();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar proveedor: " + e.getMessage());
            return false;
        }
    }

    private boolean existeProveedor(String nombre, String ruc) {
        if (conn == null) {
            System.err.println("Conexión a la base de datos no establecida.");
            return false;
        }

        String query = "SELECT COUNT(*) FROM proveedores WHERE nombre = ? OR ruc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, ruc);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si existe un proveedor con el mismo nombre o RUC
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia del proveedor: " + e.getMessage());
        }
        return false;
    }

    public boolean modificarProveedor(int idProveedor, String nombre, String ruc, String contacto) {
        if (conn == null) {
            System.err.println("Conexión a la base de datos no establecida.");
            return false;
        }

        String query = "UPDATE proveedores SET nombre = ?, ruc = ?, contacto = ? WHERE idproveedor = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, ruc);
            stmt.setString(3, contacto);
            stmt.setInt(4, idProveedor);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar proveedor: " + e.getMessage());
            return false;
        }
    }

    public Proveedor obtenerProveedorPorRUC(String ruc) {
        if (conn == null) {
            System.err.println("Conexión a la base de datos no establecida.");
            return null;
        }

        String query = "SELECT p.idproveedor, p.nombre, p.ruc, p.contacto, p.fecharegistro, u.nombreusuario AS registrado_por " +
                "FROM proveedores p JOIN usuarios u ON p.registradopor = u.idusuario WHERE p.ruc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ruc);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Proveedor(
                        rs.getInt("idproveedor"),
                        rs.getString("nombre"),
                        rs.getString("ruc"),
                        rs.getString("contacto"),
                        rs.getString("fecharegistro"),
                        rs.getString("registrado_por")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedor por RUC: " + e.getMessage());
        }
        return null;
    }

    public boolean eliminarProveedorPorRUC(String ruc) {
        if (conn == null) {
            System.err.println("Conexión a la base de datos no establecida.");
            return false;
        }

        String query = "DELETE FROM proveedores WHERE ruc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ruc);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                reasignarIDs();
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }

    public int obtenerIDUsuario(String nombreUsuario) {
        String query = "SELECT idusuario FROM usuarios WHERE nombreusuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idusuario");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID del usuario: " + e.getMessage());
        }
        return -1;
    }

    public boolean reasignarIDs() {
        if (conn == null) {
            System.err.println("Conexión a la base de datos no establecida.");
            return false;
        }

        String query = "WITH cte AS ( " +
                "    SELECT idproveedor, ROW_NUMBER() OVER (ORDER BY idproveedor) AS nuevo_id " +
                "    FROM proveedores " +
                ") " +
                "UPDATE proveedores " +
                "SET idproveedor = nuevo_id " +
                "FROM cte " +
                "WHERE proveedores.idproveedor = cte.idproveedor";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            int rowsAffected = stmt.executeUpdate();
            System.out.println("IDs reasignados. Filas afectadas: " + rowsAffected);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al reasignar IDs: " + e.getMessage());
            return false;
        }
    }
}




