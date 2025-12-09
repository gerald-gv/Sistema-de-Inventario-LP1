package modeloDAO;

import modelo.Usuario;
import modelo.Rol;
import java.sql.*;
import config.Conexion;

public class UsuarioDAO {
	public Usuario validar(String email, String password) {
        Usuario usuario = new Usuario();
        
        String sql = "SELECT u.*, r.nombre AS nombreRol FROM usuarios u JOIN rol r ON u.id_rol = r.id_rol WHERE u.user_email=? AND u.password=?";
        
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
                usuario.setId(rs.getInt("id"));
                usuario.setUserEmail(rs.getString("user_email"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol")); 
                rol.setNombre(rs.getString("nombreRol")); 
                usuario.setRol(rol);
            }
            
        } catch (SQLException e) {
            System.out.println("Error en la validación de credenciales: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return usuario; 
    }
}