package basedatos.usuers;

import basedatos.ConexionDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioMD {
    // Método para obtener el rol del usuario conectado
    public String obtenerRolUsuario(String nombreUsuario) {
        String sql = "SELECT r.nombreRol FROM usuarios u JOIN roles r ON u.idRol = r.idRol WHERE u.nombreUsuario = ?";
        try (Connection conn = ConexionDataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nombreRol"); // Retorna el nombre del rol
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el rol del usuario: " + e.getMessage());
        }
        return null; // Retorna null si no se encontró el usuario o ocurrió un error
    }
}
