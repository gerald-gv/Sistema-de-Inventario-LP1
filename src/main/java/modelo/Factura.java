package modelo;

import java.time.LocalDate;

public class Factura {
	private int id;
	private Cliente cliente;
	private double total;
	private LocalDate fecha;
	
	public Factura() {
		cliente = new Cliente();
	}

	public Factura(int id, Cliente cliente, double total, LocalDate fecha) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.total = total;
		this.fecha = fecha;
	}

	
	
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
	
	
}
