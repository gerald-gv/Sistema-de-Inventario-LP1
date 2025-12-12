package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import interfaz.CRUD;
import modelo.Libros;

public class LibrosDAO implements CRUD<Libros>{
	
	 Connection con;
	 PreparedStatement ps;
	 ResultSet rs;
	 
	@Override
	public List<Libros> listar() {
	    List<Libros> lista = new ArrayList<>();
	    String sql = "SELECT l.*, c.nombre AS categoria_nombre "
	            + "FROM libros l "
	            + "INNER JOIN categoria_libros c ON l.id_categoria = c.id_categoria";
	    try {
	        con = Conexion.Conectar();
	        ps = con.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Libros libro = new Libros();

	            libro.setIdLibro(rs.getInt("id_libro"));
	            libro.setIdCat(rs.getInt("id_categoria"));
	            libro.setTitulo(rs.getString("titulo"));
	            libro.setAutor(rs.getString("autor"));
	            libro.setDescripcion(rs.getString("descripcion"));
	            libro.setPrecioCompra(rs.getBigDecimal("precio_compra"));
	            libro.setPrecioVenta(rs.getBigDecimal("precio_venta"));
	            libro.setStock(rs.getInt("stock"));

	            lista.add(libro);
	        }

	    } catch (Exception e) {
	        System.out.println("Error al listar libros: " + e);
	    }

	    return lista;
	}

	@Override
	public Libros list(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Libros obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(Libros obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
