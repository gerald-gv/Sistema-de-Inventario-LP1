package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    private int id;
    private Cliente cliente;
    private double total;
    private LocalDate fecha;
    private Usuario usuario;
    private List<DetalleVenta> detalles; // <-- esto faltaba

    // Constructor vacío
    public Factura() {
        cliente = new Cliente();
        detalles = new ArrayList<>(); // inicializamos la lista
    }
    // Constructor completo
    public Factura(int id, Cliente cliente, double total, LocalDate fecha, Usuario usuario, List<DetalleVenta> detalles) {
        this.id = id;
        this.cliente = cliente;
        this.total = total;
        this.fecha = fecha;
        this.usuario = usuario;
        this.detalles = detalles;
    }

    // Getters y setters existentes
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    // Getter y setter para la lista de detalles
    public List<DetalleVenta> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}
