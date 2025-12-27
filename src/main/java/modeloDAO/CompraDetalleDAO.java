package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import modelo.CompraDetalle;
import modelo.Compras;
import modelo.Libros;

public class CompraDetalleDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<CompraDetalle> listarPorCompra(int idCompra) {

        List<CompraDetalle> lista = new ArrayList<>();

        String sql = """
                SELECT d.id_detalle, d.cantidad, d.precio_uni_compra,
                       l.id_libro, l.titulo
                FROM compra_detalle d
                JOIN libros l ON d.id_libro = l.id_libro
                WHERE d.id_compra = ?
                ORDER BY d.id_detalle ASC
                """;

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCompra);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Libros libro = new Libros();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));

                Compras compra = new Compras();
                compra.setIdCompra(idCompra);

                CompraDetalle detalle = new CompraDetalle();
                detalle.setIdDetalle(rs.getInt("id_detalle"));
                detalle.setCompras(compra);
                detalle.setLibro(libro);
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioUniCompra(rs.getBigDecimal("precio_uni_compra"));

                lista.add(detalle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean add(CompraDetalle obj) {

        String sql = """
                INSERT INTO compra_detalle
                (id_compra, id_libro, cantidad, precio_uni_compra)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, obj.getCompras().getIdCompra());
            ps.setInt(2, obj.getLibro().getIdLibro());
            ps.setInt(3, obj.getCantidad());
            ps.setBigDecimal(4, obj.getPrecioUniCompra());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminarPorCompra(int idCompra) {
        String sql = "DELETE FROM compra_detalle WHERE id_compra = ?";

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCompra);
            ps.executeUpdate(); // puede ser 0 y no pasa nada
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}