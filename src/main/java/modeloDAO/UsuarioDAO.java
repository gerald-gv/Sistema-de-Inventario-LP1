package modeloDAO;

import modelo.Usuario;
import modelo.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import interfaz.CRUD;
import interfaz.CountMetrics;

public class UsuarioDAO implements CRUD<Usuario>, CountMetrics {

    // VALIDAR LOGIN
    public Usuario validar(String email, String password) {
        Usuario usuario = new Usuario();

        String sql = "SELECT u.id_usuario, u.user_email, u.username, u.password, " +
                     "r.id_rol, r.nombre AS nombreRol " +
                     "FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.user_email=? AND u.password=?";

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
            }
        } catch (SQLException e) {
            System.err.println("Error en validar usuario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        return usuario;
    }

    // CRUD
    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT u.id_usuario, u.username, u.user_email, u.password, " +
                     "r.id_rol, r.nombre AS nombreRol " +
                     "FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setUserEmail(rs.getString("user_email"));
                u.setPassword(rs.getString("password"));

                Rol r = new Rol();
                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombreRol"));
                u.setRol(r);

                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e);
        }
        return lista;
    }

    @Override
    public Usuario list(int id) {
        Usuario u = new Usuario();

        String sql = "SELECT u.id_usuario, u.username, u.user_email, u.password, " +
                     "r.id_rol, r.nombre AS nombreRol " +
                     "FROM usuarios u JOIN roles r ON u.id_rol = r.id_rol " +
                     "WHERE u.id_usuario=?";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u.setId(rs.getInt("id_usuario"));
                u.setUsername(rs.getString("username"));
                u.setUserEmail(rs.getString("user_email"));
                u.setPassword(rs.getString("password"));

                Rol r = new Rol();
                r.setIdRol(rs.getInt("id_rol"));
                r.setNombre(rs.getString("nombreRol"));
                u.setRol(r);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario: " + e);
        }
        return u;
    }

    @Override
    public boolean add(Usuario u) {
        String sql = "INSERT INTO usuarios(username, user_email, password, id_rol) VALUES(?,?,?,?)";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getUserEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getRol().getIdRol());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e);
        }
        return false;
    }

    @Override
    public boolean edit(Usuario u) {
        String sql = "UPDATE usuarios SET username=?, user_email=?, password=?, id_rol=? WHERE id_usuario=?";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getUserEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getRol().getIdRol());
            ps.setInt(5, u.getId());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al editar usuario: " + e);
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e);
        }
        return false;
    }

	@Override
	public int count() {
		int total = 0;
		String sql = "SELECT COUNT(*) FROM usuarios";
		
		try {
			Connection con = Conexion.Conectar();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("Sucedio un error al obtener la cantidad de la entidad Uusario: "+ e);
		}
		
		return total;
	}
}