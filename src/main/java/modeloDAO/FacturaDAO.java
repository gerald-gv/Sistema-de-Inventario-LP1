package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.DetalleVenta;
import config.Conexion;
import interfaz.CRUD;
import modelo.Cliente;
import modelo.Factura;

public class FacturaDAO implements CRUD<Factura> {

	Connection cnx;
	PreparedStatement ps;
	ResultSet rs;

	@Override
	public List<Factura> listar() {
		List<Factura> lista = new ArrayList<Factura>();

		String sql = """
					SELECT * FROM ventas v
					JOIN clientes c
					ON c.id_cliente = v.id_cliente
					 ORDER BY v.id_venta ASC
				""";

		try {
			cnx = Conexion.Conectar();
			ps = cnx.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Factura factura = new Factura();

				factura.setId(rs.getInt("id_venta"));
				factura.setCliente(new Cliente(rs.getInt("id_cliente"), rs.getString("nombre_negocio"),
						rs.getString("customer_email"), rs.getString("customer_contact"), rs.getString("direccion"),
						rs.getString("tipo_cliente")));
				factura.setTotal(rs.getDouble("total_venta"));
				factura.setFecha(rs.getDate("fecha_venta").toLocalDate());

				lista.add(factura);
			}
		} catch (Exception e) {
			System.out.println("Error al listar las facturas: " + e.getMessage());
			e.printStackTrace();
		} finally {
			cerrarRecursos();
		}

		return lista;
	}
	//METODO PARA OBTENER LOS DETALLES
	public List<DetalleVenta> obtenerDetallesFactura(int idFactura) {
		List<DetalleVenta> detalles = new ArrayList<>();

		String sql = """
					SELECT vd.*, l.titulo, l.autor
					FROM ventas_detalle vd
					JOIN libros l ON l.id_libro = vd.id_libro
					WHERE vd.id_venta = ?
					ORDER BY vd.id_detalle
				""";

		try {
			cnx = Conexion.Conectar();
			ps = cnx.prepareStatement(sql);
			ps.setInt(1, idFactura);
			rs = ps.executeQuery();

			System.out.println("=== Buscando detalles para factura #" + idFactura + " ===");

			while (rs.next()) {
				DetalleVenta detalle = new DetalleVenta();
				detalle.setIdDetalle(rs.getInt("id_detalle"));
				detalle.setIdVenta(rs.getInt("id_venta"));
				detalle.setIdLibro(rs.getInt("id_libro"));
				detalle.setTituloLibro(rs.getString("titulo"));
				detalle.setAutor(rs.getString("autor"));
				detalle.setEditorial("N/A"); // No existe en la BD
				detalle.setCantidad(rs.getInt("cantidad"));
				detalle.setPrecioUnitario(rs.getDouble("precio_uni_venta"));
				detalle.setSubtotal(rs.getInt("cantidad") * rs.getDouble("precio_uni_venta"));

				System.out.println(
						"  Detalle encontrado: " + detalle.getTituloLibro() + " - Cant: " + detalle.getCantidad());

				detalles.add(detalle);
			}

			System.out.println("Total detalles encontrados: " + detalles.size());

		} catch (Exception e) {
			System.out.println("Error al obtener detalles de factura: " + e.getMessage());
			e.printStackTrace();
		} finally {
			cerrarRecursos();
		}

		return detalles;
	}

	public int crearFactura(int idCliente, LocalDate fecha, String[] idLibros, String[] cantidades, String[] precios) {

		int idFactura = 0;

		try {
			cnx = Conexion.Conectar();
			cnx.setAutoCommit(false); // Inicia transacción

			// VALIDACION DE PRODUCTOS
			if (idLibros == null || idLibros.length == 0) {
				return 0;
			}

			// CALCULAMOS EL TOTAL
			double totalVenta = 0;
			for (int i = 0; i < precios.length; i++) {
				totalVenta += Double.parseDouble(precios[i]) * Integer.parseInt(cantidades[i]);
			}

			// INSERTAMOS LA VENTA GAAA
			String sqlVenta = "INSERT INTO ventas (id_cliente, fecha_venta, total_venta) VALUES (?, ?, ?)";

			ps = cnx.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, idCliente);
			ps.setDate(2, java.sql.Date.valueOf(fecha));
			ps.setDouble(3, totalVenta);
			ps.executeUpdate();

			// OBTENER ID GENERADO
			rs = ps.getGeneratedKeys();
			if (!rs.next()) {
				cnx.rollback();
				return 0;
			}
			idFactura = rs.getInt(1);

			// INSERTAMOS LOS DETALLES
			String sqlDetalle = "INSERT INTO ventas_detalle (id_venta, id_libro, cantidad, precio_uni_venta) "
					+ "VALUES (?, ?, ?, ?)";

			ps.close();
			ps = cnx.prepareStatement(sqlDetalle);

			for (int i = 0; i < idLibros.length; i++) {
				ps.setInt(1, idFactura);
				ps.setInt(2, Integer.parseInt(idLibros[i]));
				ps.setInt(3, Integer.parseInt(cantidades[i]));
				ps.setDouble(4, Double.parseDouble(precios[i]));
				ps.addBatch();
			}

			ps.executeBatch();


			cnx.commit();

		} catch (Exception e) {
			try {
				if (cnx != null)
					cnx.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return 0;

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (cnx != null)
					cnx.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return idFactura;
	}

	private void cerrarRecursos() {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (cnx != null)
				cnx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Factura list(int id) {
		// TODO: Implementar si es necesario
		return null;
	}

	@Override
	public boolean add(Factura f) {
		String sql = "INSERT INTO ventas (id_cliente, fecha_venta, total_venta) VALUES (?, ?, ?)";
		try {
			cnx = Conexion.Conectar();
			ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, f.getCliente().getId());
			ps.setDate(2, java.sql.Date.valueOf(f.getFecha()));
			ps.setDouble(3, f.getTotal());
			int filas = ps.executeUpdate();

			if (filas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					f.setId(rs.getInt(1));
				}
			}

			return filas > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			cerrarRecursos();
		}
	}

	@Override
	public boolean edit(Factura obj) {
		return false;
	}

	@Override
	public boolean eliminar(int id) {
		boolean eliminado = false;

		String sqlDetalle = "DELETE FROM ventas_detalle WHERE id_venta = ?";
		String sqlVenta = "DELETE FROM ventas WHERE id_venta = ?";

		try {
			cnx = Conexion.Conectar();
			cnx.setAutoCommit(false);

			// PRIMERO ELIMINAMOS DETALLES DE VENTA
			ps = cnx.prepareStatement(sqlDetalle);
			ps.setInt(1, id);
			ps.executeUpdate();

			// ELIMINAMOS LA VENTA
			ps = cnx.prepareStatement(sqlVenta);
			ps.setInt(1, id);
			int filas = ps.executeUpdate();

			cnx.commit();
			eliminado = filas > 0;

		} catch (Exception e) {
			try {
				cnx.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			cerrarRecursos();
		}

		return eliminado;
	}

}