package modeloDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import modelo.Categoria;

public class CategoriaDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try {
            con = Conexion.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

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

    public Categoria list(int id) {
        Categoria c = new Categoria();
        String sql = "SELECT * FROM categorias WHERE id_categoria=?";

        try {
            con = Conexion.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

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

    public boolean add(Categoria c) {
        String sql = "INSERT INTO categorias(nombre, descripcion) VALUES(?,?)";

        try {
            con = Conexion.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar categoría: " + e);
        }
        return false;
    }

    public boolean edit(Categoria c) {
        String sql = "UPDATE categorias SET nombre=?, descripcion=? WHERE id_categoria=?";

        try {
            con = Conexion.Conectar();
            ps = con.prepareStatement(sql);
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

    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id_categoria=?";

        try {
            con = Conexion.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: " + e);
        }
        return false;
    }
}

