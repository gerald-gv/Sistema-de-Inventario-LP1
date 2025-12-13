package modeloDAO;

import modelo.Libros;

import java.util.List;

public class test {

    public static void main(String[] args) {

        LibrosDAO dao = new LibrosDAO();
        List<Libros> lista = dao.listar();

        for (Libros l : lista) {
            System.out.println(
                l.getIdLibro() + " - " +
                l.getIdCat() + " - " +
                l.getTitulo() + " - " +
                l.getAutor() + " - " +
                l.getDescripcion() + " - " +
                l.getPrecioCompra() + " - " +
                l.getPrecioVenta() + " - " +
                l.getStock()
            );
        }
    }
}