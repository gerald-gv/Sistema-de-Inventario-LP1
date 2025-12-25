package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import interfaz.CRUD;
import interfaz.CountMetrics;
import modelo.Categoria;
import modelo.Libros;

public class LibrosDAO implements CRUD<Libros>, CountMetrics {

	Connection con;
	PreparedStatement ps;
	ResultSet rs;

	@Override
	public List<Libros> listar() {
		List<Libros> lista = new ArrayList<>();
		String sql = """
				    SELECT l.id_libro, l.titulo, l.autor, l.descripcion, l.precio_compra, l.precio_venta, l.stock,
				               c.id_categoria, c.nombre AS nombre_categoria
				        FROM libros l
				        JOIN categoria_libros c ON l.id_categoria = c.id_categoria
				        ORDER BY l.id_libro ASC
				"""; // AGREGE EL ORDER BY PARA QUE SALGA ORDENADO, SI SE QUEDO ES PORQUE FUNCIONO

		try (Connection con = Conexion.Conectar();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getInt("id_categoria"));
				categoria.setNombre(rs.getString("nombre_categoria"));

				Libros libro = new Libros();
				libro.setIdLibro(rs.getInt("id_libro"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setIdCat(categoria);
				libro.setDescripcion(rs.getString("descripcion"));
				libro.setPrecioCompra(rs.getBigDecimal("precio_compra"));
				libro.setPrecioVenta(rs.getBigDecimal("precio_venta"));
				libro.setStock(rs.getInt("stock"));

				lista.add(libro);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	@Override
	public Libros list(int id) {
		Libros libro = null;

		String sql = """
				l.id_libro, l.titulo, l.autor, l.descripcion,
				l.precio_compra, l.precio_venta, l.stock,
				c.id_categoria, c.nombre AS nombre_categoria
				FROM libros l
				JOIN categoria_libros c ON l.id_categoria = c.id_categoria
				ORDER BY c.id_categoria ASC, l.id_libro ASC
							""";

		try (Connection con = Conexion.Conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getInt("id_categoria"));
				categoria.setNombre(rs.getString("nombre_categoria"));

				libro = new Libros();
				libro.setIdLibro(rs.getInt("id_libro"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setDescripcion(rs.getString("descripcion"));
				libro.setPrecioCompra(rs.getBigDecimal("precio_compra"));
				libro.setPrecioVenta(rs.getBigDecimal("precio_venta"));
				libro.setStock(rs.getInt("stock"));
				libro.setIdCat(categoria);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return libro;
	}

	@Override
	public boolean add(Libros obj) {

		String sql = """
				    INSERT INTO libros
				    (titulo, autor, descripcion, precio_compra, precio_venta, stock, id_categoria)
				    VALUES (?, ?, ?, ?, ?, ?, ?)  
				""";  //LOS SIMBOLOS DE ? SON MARCADORES DE POSICION, SE USAN JUNTO A PREPAREDSTATEMENT, BASICAMENTE 
					//SON ESPACIOS VACIOS QUE SE LLENARAN CON LO DE ABAJO
		try (Connection con = Conexion.Conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, obj.getTitulo());
			ps.setString(2, obj.getAutor());
			ps.setString(3, obj.getDescripcion());
			ps.setBigDecimal(4, obj.getPrecioCompra());
			ps.setBigDecimal(5, obj.getPrecioVenta());
			ps.setInt(6, obj.getStock());
			ps.setInt(7, obj.getIdCat().getIdCategoria());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(Libros obj) {
		String sql = """
				    UPDATE libros SET
				        titulo = ?,
				        autor = ?,
				        descripcion = ?,
				        precio_compra = ?,
				        precio_venta = ?,
				        stock = ?,
				        id_categoria = ?
				    WHERE id_libro = ?
				""";

		try (Connection con = Conexion.Conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, obj.getTitulo());
			ps.setString(2, obj.getAutor());
			ps.setString(3, obj.getDescripcion());
			ps.setBigDecimal(4, obj.getPrecioCompra());
			ps.setBigDecimal(5, obj.getPrecioVenta());
			ps.setInt(6, obj.getStock());
			ps.setInt(7, obj.getIdCat().getIdCategoria());
			ps.setInt(8, obj.getIdLibro());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean eliminar(int id) {
		String sql = "DELETE FROM libros WHERE id_libro = ?";

		try (Connection con = Conexion.Conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Metodos de Metricas
	@Override
	public int count() {
		int total = 0;
		String sql = "SELECT COUNT(*) FROM libros";
		
		try {
			con = Conexion.Conectar();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("Sucedio un error al obtener la cantidad de la entidad libros: "+ e);
		}
		
		
		return total;
	}
	
	public int totalStock() {
		int total = 0;
		String sql = "SELECT SUM(stock) FROM libros";
		
		try {
			con = Conexion.Conectar();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (Exception e) {
			System.out.println("Sucedio un error al obtener la cantidad de Stock de la entidad libros: "+ e);
		}
		return total;
	}
	public int obtenerStock(int idLibro) {
	    int stock = 0;
	    String sql = "SELECT stock FROM libros WHERE id_libro = ?";

	    try (Connection con = Conexion.Conectar();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, idLibro);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            stock = rs.getInt("stock");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return stock;
	}
	
	public boolean actualizarStock(int idLibro, int nuevoStock) {
	    String sql = "UPDATE libros SET stock = ? WHERE id_libro = ?";

	    try (Connection con = Conexion.Conectar();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, nuevoStock);
	        ps.setInt(2, idLibro);

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
