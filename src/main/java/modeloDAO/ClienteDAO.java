package modeloDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import interfaz.CRUD;
import interfaz.CountMetrics;
import modelo.Cliente;

public class ClienteDAO implements CRUD<Cliente>, CountMetrics {
	
	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	@Override
	public List<Cliente> listar() {
		
		List<Cliente> lista = new ArrayList<Cliente>();
		
		String sql = "SELECT * FROM clientes";
		
		try {
			
			con = Conexion.Conectar();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Cliente cliente = new Cliente();
				
				cliente.setId(rs.getInt("id_cliente"));
				cliente.setNombreNegocio(rs.getString("nombre_negocio"));
				cliente.setEmail(rs.getString("customer_email"));
				cliente.setContacto(rs.getString("customer_contact"));
				cliente.setDireccion(rs.getString("direccion"));
				cliente.setTipoCliente(rs.getString("tipo_cliente"));
				
				lista.add(cliente);
				
			}
		} catch (Exception e) {
			System.out.println("Error al listar los clientes: " + e);
		}
		
		
		return lista;
	}

	@Override
	public Cliente list(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Cliente obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(Cliente obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	// Contar la cantidad de Clientes
	@Override
	public int count() {
		
		int total = 0;
		String sql = "SELECT COUNT(*) FROM clientes";
		
		try {
			con = Conexion.Conectar();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("Sucedio un error al obtener la cantidad de la entidad Clientes: "+ e);
		}
		
		return total;
	}

}
