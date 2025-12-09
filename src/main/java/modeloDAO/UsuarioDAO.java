package modeloDAO;

import modelo.Usuario;
import modelo.Rol;
import java.sql.*;
import config.Conexion;

public class UsuarioDAO {
	public Usuario validar(String email, String password) {
        Usuario usuario = new Usuario();
        
        String sql = "SELECT u.id_usuario, u.user_email, u.username, u.password, r.id_rol, r.nombre AS nombreRol "
                   + "FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol "
                   + "WHERE u.user_email=? AND u.password=?";
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = Conexion.Conectar();
            
            ps = con.prepareStatement(sql);
            ps.setString(1, email); 
            ps.setString(2, password);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario.setId(rs.getInt("id_usuario")); 
                usuario.setUserEmail(rs.getString("user_email"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol")); 
                rol.setNombre(rs.getString("nombreRol")); 
                usuario.setRol(rol);
            } else {
                System.out.println("Consulta sql ejecutada, pero no se encontraron credenciales validas");
            }
            
        } catch (SQLException e) {
            System.err.println("Error en la validación de credenciales de sql: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return usuario; 
    }
}