package modeloDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.DetalleVenta;
import config.Conexion;
import interfaz.CRUD;
import interfaz.CountMetrics;
import modelo.Cliente;
import modelo.Factura;
import modelo.Usuario;

public class FacturaDAO implements CRUD<Factura>, CountMetrics {

	Connection cnx;
	PreparedStatement ps;
	ResultSet rs;

	@Override
	public List<Factura> listar() {
		List<Factura> lista = new ArrayList<Factura>();

		String sql = """
				SELECT *
FROM ventas v
JOIN clientes c ON c.id_cliente = v.id_cliente
LEFT JOIN usuarios u ON u.id_usuario = v.id_usuario
ORDER BY v.id_venta ASC
								""";

		try {
			cnx = Conexion.Conectar();
			ps = cnx.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
			    Factura factura = new Factura();

			    factura.setId(rs.getInt("id_venta"));
			    factura.setTotal(rs.getDouble("total_venta"));
			    factura.setFecha(rs.getDate("fecha_venta").toLocalDate());

			    // ESTO PARA EL CLIENTE
			    factura.setCliente(new Cliente(
			        rs.getInt("id_cliente"),
			        rs.getString("nombre_negocio"),
			        rs.getString("customer_email"),
			        rs.getString("customer_contact"),
			        rs.getString("direccion"),
			        rs.getString("tipo_cliente")
			    ));

			    // USUARIO
			    Usuario usuario = new Usuario();
			    usuario.setId(rs.getInt("id_usuario"));
			    usuario.setUsername(rs.getString("username"));

			    factura.setUsuario(usuario);

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

	// METODO PARA OBTENER LOS DETALLES
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


				detalles.add(detalle);
			}

		} catch (Exception e) {
			System.out.println("Error al obtener detalles de factura: " + e.getMessage());
			e.printStackTrace();
		} finally {
			cerrarRecursos();
		}

		return detalles;
	}

	public int crearFactura(int idCliente, int idUsuario,LocalDate fecha, String[] idLibros, String[] cantidades, String[] precios) {

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
			String sqlVenta = "INSERT INTO ventas (id_cliente, id_usuario, fecha_venta, total_venta)\r\n"
					+ "    VALUES (?, ?, ?, ?)";

			ps = cnx.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, idCliente);
			ps.setInt(2, idUsuario);
			ps.setDate(3, java.sql.Date.valueOf(fecha));
			ps.setDouble(4, totalVenta);
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
		String sql = "INSERT INTO ventas (id_cliente, id_usuario, fecha_venta, total_venta) VALUES (?, ?, ?, ?)";
		try {
			cnx = Conexion.Conectar();
			ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, f.getCliente().getId());
			ps.setInt(2, f.getUsuario().getId());
			ps.setDate(3, java.sql.Date.valueOf(f.getFecha()));
			ps.setDouble(4, f.getTotal());
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
	public Factura obtenerInfoFactura(int idFactura) {
	    Factura factura = null;

	    String sqlFactura = """
	        SELECT v.id_venta, v.fecha_venta,
	               u.id_usuario, u.username AS vendedor,
	               c.id_cliente, c.nombre_negocio AS cliente, c.tipo_cliente,
	               c.customer_email AS email, c.customer_contact AS telefono
	        FROM ventas v
	        LEFT JOIN usuarios u ON u.id_usuario = v.id_usuario
	        JOIN clientes c ON c.id_cliente = v.id_cliente
	        WHERE v.id_venta = ?
	    """;

	    try {
	        cnx = Conexion.Conectar();
	        ps = cnx.prepareStatement(sqlFactura);
	        ps.setInt(1, idFactura);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            
	            Usuario usuario = new Usuario();
	            usuario.setId(rs.getInt("id_usuario"));
	            usuario.setUsername(rs.getString("vendedor"));

	            Cliente cliente = new Cliente();
	            cliente.setId(rs.getInt("id_cliente"));
	            cliente.setNombreNegocio(rs.getString("cliente"));
	            cliente.setTipoCliente(rs.getString("tipo_cliente"));
	            cliente.setEmail(rs.getString("email"));
	            cliente.setContacto(rs.getString("telefono"));

	            factura = new Factura();
	            factura.setId(rs.getInt("id_venta"));
	            factura.setFecha(rs.getDate("fecha_venta").toLocalDate());
	            factura.setUsuario(usuario);
	            factura.setCliente(cliente);
	            
	        }

	        // Obtener los detalles
	        String sqlDetalles = """
	            SELECT vd.id_detalle, vd.id_libro, l.titulo AS tituloLibro, l.autor, l.editorial,
	                   vd.cantidad, vd.precio AS precioUnitario, (vd.cantidad * vd.precio) AS subtotal
	            FROM ventas_detalle vd
	            JOIN libros l ON l.id_libro = vd.id_libro
	            WHERE vd.id_venta = ?
	            ORDER BY vd.id_detalle
	        """;

	        ps = cnx.prepareStatement(sqlDetalles);
	        ps.setInt(1, idFactura);
	        rs = ps.executeQuery();

	        List<DetalleVenta> detalles = new ArrayList<>();
	        while (rs.next()) {
	            DetalleVenta detalle = new DetalleVenta();
	            detalle.setIdDetalle(rs.getInt("id_detalle"));
	            detalle.setIdVenta(idFactura);
	            detalle.setIdLibro(rs.getInt("id_libro"));
	            detalle.setTituloLibro(rs.getString("tituloLibro"));
	            detalle.setAutor(rs.getString("autor"));
	            detalle.setEditorial(rs.getString("editorial"));
	            detalle.setCantidad(rs.getInt("cantidad"));
	            detalle.setPrecioUnitario(rs.getDouble("precioUnitario"));
	            detalle.setSubtotal(rs.getDouble("subtotal"));

	            detalles.add(detalle);
	        }
	       

	        // ASIGNAR DETALLES Y TOTAL DENTRO DEL TRY
	        if (factura != null) {
	            factura.setDetalles(detalles);
	            factura.setTotal(detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum());
	        } else {
	            System.out.println("LA FACTURA ESTA VACIA");
	        }

	    } catch (Exception e) {
	        System.out.println("NO SE PUEDO ENCONTRAR obtenerInfoFactura:");
	        e.printStackTrace();
	    } finally {
	        cerrarRecursos();
	    }

	    
	    return factura;
	}

	@Override
	public int count() {
		int total = 0;
		
		String sql = "SELECT COUNT(*) FROM ventas";
		try {
			cnx = Conexion.Conectar();
			ps = cnx.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("Sucedio un error al obtener la cantidad de la entidad ventas: "+ e);
		}
		
		
		return total;
	}
	
	public BigDecimal sumaTotalVentas() {
		BigDecimal sumaTotal = BigDecimal.ZERO;
		String sql = "SELECT SUM(total_venta) FROM ventas";
		
		try {
			cnx = Conexion.Conectar();
			ps = cnx.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				sumaTotal = rs.getBigDecimal(1);
			}
			
		} catch(SQLException e) {
			System.out.println("Sucedio un error al intentar obtener el importe total de venta "+ e);
		}
		
		return sumaTotal;
	}
}