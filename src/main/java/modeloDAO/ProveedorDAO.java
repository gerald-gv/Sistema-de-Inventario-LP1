package modeloDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import interfaz.CRUD;
import interfaz.CountMetrics;
import modelo.Proveedor;

public class ProveedorDAO implements CRUD<Proveedor>, CountMetrics{
	
	
	Connection cnx;
	PreparedStatement ps;
	ResultSet rs;
	
	@Override
	public List<Proveedor> listar() {
		
		List<Proveedor> lista = new ArrayList<Proveedor>();
		
		String sql = "SELECT * FROM proveedores";
		
		try {
			
			cnx = Conexion.Conectar();
			ps = cnx.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Proveedor proveedor = new Proveedor();
				
				proveedor.setId(rs.getInt("id_proveedor"));
				proveedor.setNombre(rs.getString("company_name"));
				proveedor.setRuc(rs.getString("ruc"));
				proveedor.setTelefono(rs.getString("telefono"));
				proveedor.setDireccion(rs.getString("direccion"));
				proveedor.setEmail(rs.getString("email"));
				
				lista.add(proveedor);
				
			}
		} catch (Exception e) {
			System.out.println("Error al listar los clientes: " + e);
		}
		
		
		return lista;
	}

	@Override
	public Proveedor list(int id) {
        Proveedor p = new Proveedor();
        /*
        String sql = "SELECT u.id_usuario, u.username, u.user_email, u.password, "
                   + "r.id_rol, r.nombre AS nombreRol "
                   + "FROM usuarios u "
                   + "JOIN roles r ON u.id_rol = r.id_rol "
                   + "WHERE u.id_usuario=?";
	*/
        String sql = """
        			SELECT * FROM proveedores
        			WHERE id_proveedor=?
        		""";
        
        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("id_usuario"));
                p.setNombre(rs.getString("company_name"));
				p.setRuc(rs.getString("ruc"));
				p.setTelefono(rs.getString("telefono"));
				p.setDireccion(rs.getString("direccion"));
				p.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener usuario: " + e);
        }

        return p;
	}

	@Override
	public boolean add(Proveedor p) {
        String sql = "INSERT INTO proveedores(company_name, ruc, telefono, direccion, email) VALUES(?,?,?,?,?)";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getRuc());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getEmail());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar usuario: " + e);
        }

        return false;
	}

	@Override
	public boolean edit(Proveedor p) {
        String sql = "UPDATE proveedores SET company_name=?, ruc=?, telefono=?, direccion=?, email=? WHERE id_proveedor=?";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getRuc());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getDireccion());
            ps.setString(5, p.getEmail());
            ps.setInt(6, p.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al editar usuario: " + e);
        }

        return false;
	}

	@Override
	public boolean eliminar(int id) {
        String sql = "DELETE FROM proveedores WHERE id_proveedor=?";

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
		String sql = "SELECT COUNT(*) FROM proveedores";
		
		try {
			Connection con = Conexion.Conectar();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("Sucedio un error al obtener la cantidad de la entidad proveedores: "+ e);
		}
		
		
		return total;
	}

}
