package modeloDAO;

import interfaz.CRUD;
import interfaz.CountMetrics;
import modelo.Categoria;
import config.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements CRUD<Categoria>, CountMetrics {

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria_libros";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e);
        }
        return lista;
    }

    @Override
    public Categoria list(int id) {
        Categoria c = new Categoria();
        String sql = "SELECT * FROM categoria_libros WHERE id_categoria=?";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c.setIdCategoria(rs.getInt("id_categoria"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener categoría: " + e);
        }
        return c;
    }

    @Override
    public boolean add(Categoria c) {
        String sql = "INSERT INTO categoria_libros(nombre, descripcion) VALUES (?,?)";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar categoría: " + e);
        }
        return false;
    }

    @Override
    public boolean edit(Categoria c) {
        String sql = "UPDATE categoria_libros SET nombre=?, descripcion=? WHERE id_categoria=?";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getIdCategoria());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al editar categoría: " + e);
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM categoria_libros WHERE id_categoria=?";

        try {
            Connection con = Conexion.Conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: " + e);
        }
        return false;
    }

	@Override
	public int count() {
		int total = 0;
		
		String sql = "SELECT COUNT(*) FROM categoria_libros";
		
		try {
			Connection con = Conexion.Conectar();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("Sucedio un error al obtener la cantidad de categorias: "+ e);
		}
		
		
		return total;
	}
}