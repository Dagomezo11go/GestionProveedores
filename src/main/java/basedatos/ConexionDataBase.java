package basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDataBase {
    // Configuración de la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/GestionProveedores";;// Cambia por el nombre de tu base de datos
    private static final String USER = "admin"; // Usuario configurado en tu base de datos
    private static final String PASSWORD = "admin123"; // Contraseña correspondiente

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null; // Esto causa el NullPointerException si no se maneja
        }
    }
}
