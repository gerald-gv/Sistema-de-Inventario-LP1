package modeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.Conexion;
import interfaz.CRUD;
import modelo.Compras;
import modelo.Proveedor;
import modelo.Usuario;

public class ComprasDAO implements CRUD<Compras> {

    private static final String SQL_LISTAR = """
            SELECT c.id_compra, c.total_compra, c.fecha,
                   p.id_proveedor, p.company_name,
                   u.id_usuario, u.username
            FROM compras c
            JOIN proveedores p ON c.id_proveedor = p.id_proveedor
            JOIN usuarios u ON c.id_usuario = u.id_usuario
            """;

    private static final String SQL_BUSCAR_POR_ID = """
            SELECT c.id_compra, c.total_compra, c.fecha,
                   p.id_proveedor, p.company_name,
                   u.id_usuario, u.username
            FROM compras c
            JOIN proveedores p ON c.id_proveedor = p.id_proveedor
            JOIN usuarios u ON c.id_usuario = u.id_usuario
            WHERE c.id_compra = ?
            """;

    private static final String SQL_INSERTAR = """
            INSERT INTO compras (id_proveedor, id_usuario, total_compra, fecha)
            VALUES (?, ?, ?, ?)
            """;

    private static final String SQL_ELIMINAR = "DELETE FROM compras WHERE id_compra = ?";

    private static final String SQL_ULTIMO_ID = "SELECT MAX(id_compra) FROM compras";

    private static final String SQL_ACTUALIZAR_TOTAL = """
            UPDATE compras c
            SET total_compra = (
                SELECT IFNULL(SUM(cd.cantidad * cd.precio_uni_compra), 0)
                FROM compra_detalle cd
                WHERE cd.id_compra = ?
            )
            WHERE c.id_compra = ?
            """;

    @Override
    public List<Compras> listar() {
        List<Compras> lista = new ArrayList<>();

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCompra(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Compras list(int id) {
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR_POR_ID)) {

            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCompra(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean add(Compras obj) {
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)) {

            ps.setInt(1, obj.getProveedor().getId());
            ps.setInt(2, obj.getUsuario().getId());
            ps.setBigDecimal(3, obj.getTotalCompra());
            ps.setDate(4, obj.getFecha());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean edit(Compras obj) {
        // Normalmente NO se edita una compra
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int obtenerUltimoId() {
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(SQL_ULTIMO_ID);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener último ID de compra: " + e);
        }
        
        return 0;
    }

    public void actualizarTotal(int idCompra) {
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR_TOTAL)) {

            ps.setInt(1, idCompra);
            ps.setInt(2, idCompra);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Compras mapearCompra(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id_usuario"));
        usuario.setUsername(rs.getString("username"));

        Proveedor proveedor = new Proveedor();
        proveedor.setId(rs.getInt("id_proveedor"));
        proveedor.setNombre(rs.getString("company_name"));

        Compras compra = new Compras();
        compra.setIdCompra(rs.getInt("id_compra"));
        compra.setUsuario(usuario);
        compra.setProveedor(proveedor);
        compra.setTotalCompra(rs.getBigDecimal("total_compra"));
        compra.setFecha(rs.getDate("fecha"));

        return compra;
    }
}