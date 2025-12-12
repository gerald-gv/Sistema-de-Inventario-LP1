package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import interfaz.CRUD;
import modelo.Categoria;

public class CategoriaDAO implements CRUD<Categoria>{
	
	 Connection con;
	 PreparedStatement ps;
	 ResultSet rs;
	    
	@Override
	public List<Categoria> listar() {
		 List<Categoria> lista = new ArrayList<>();
	        String sql = "SELECT * FROM categoria_libros"; 

	        try {
	            con = Conexion.Conectar();
	            ps = con.prepareStatement(sql);
	            rs = ps.executeQuery();

	            while (rs.next()) {
	                Categoria cat = new Categoria();

	                cat.setIdCat(rs.getInt("id_categoria"));       
	                cat.setNombre(rs.getString("nombre"));    

	                lista.add(cat);
	            }

	        } catch (Exception e) {
	            System.out.println("Error al listar categorías: " + e);
	        }

	        return lista;
	    }

	@Override
	public Categoria list(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Categoria obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(Categoria obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
