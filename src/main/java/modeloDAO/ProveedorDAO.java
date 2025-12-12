package modeloDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import interfaz.CRUD;
import modelo.Proveedor;

public class ProveedorDAO implements CRUD<Proveedor>{
	
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Proveedor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(Proveedor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
