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
	    Cliente cliente = new Cliente();
	    String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

	    try {
	        con = Conexion.Conectar();
	        ps = con.prepareStatement(sql);
	        ps.setInt(1, id);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            cliente = new Cliente();
	            cliente.setId(rs.getInt("id_cliente"));
	            cliente.setNombreNegocio(rs.getString("nombre_negocio"));
	            cliente.setEmail(rs.getString("customer_email"));
	            cliente.setContacto(rs.getString("customer_contact"));
	            cliente.setDireccion(rs.getString("direccion"));
	            cliente.setTipoCliente(rs.getString("tipo_cliente"));
	        }

	    } catch (Exception e) {
	        System.out.println("Error al obtener el cliente: " + e);
	    }

	    return cliente;
	}

	@Override
	public boolean add(Cliente obj) {
	    
	    String sql = "INSERT INTO clientes(nombre_negocio, customer_email, customer_contact, direccion, tipo_cliente) VALUES(?, ?, ?, ?, ?)";

	    try {
	        con = Conexion.Conectar();
	        ps = con.prepareStatement(sql);
	        
	        ps.setString(1, obj.getNombreNegocio());
	        ps.setString(2, obj.getEmail());
	        ps.setString(3, obj.getContacto());
	        ps.setString(4, obj.getDireccion());
	        ps.setString(5, obj.getTipoCliente());
	        
	        ps.executeUpdate();
	        return true;

	    } catch (Exception e) {
	        System.out.println("Error al agregar cliente: " + e);
	    }

	    return false;
	}

	@Override
	public boolean edit(Cliente obj) {
	    String sql = "UPDATE clientes SET nombre_negocio=?, customer_email=?, customer_contact=?, direccion=?, tipo_cliente=? WHERE id_cliente=?";

	    try {
	        con = Conexion.Conectar();
	        ps = con.prepareStatement(sql);
	        ps.setString(1, obj.getNombreNegocio());
	        ps.setString(2, obj.getEmail());
	        ps.setString(3, obj.getContacto());
	        ps.setString(4, obj.getDireccion());
	        ps.setString(5, obj.getTipoCliente());
	        ps.setInt(6, obj.getId());

	        ps.executeUpdate();
	        return true;

	    } catch (Exception e) {
	        System.out.println("Error al editar cliente: " + e);
	    }

	    return false;
	}

	@Override
	public boolean eliminar(int id) {
	    String sql = "DELETE FROM clientes WHERE id_cliente=?";

	    try {
	        con = Conexion.Conectar();
	        ps = con.prepareStatement(sql);
	        ps.setInt(1, id);

	        ps.executeUpdate();
	        return true;

	    } catch (Exception e) {
	        System.out.println("Error al eliminar cliente: " + e);
	    }

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
