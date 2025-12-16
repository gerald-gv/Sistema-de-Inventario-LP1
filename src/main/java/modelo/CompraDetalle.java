package modelo;

import java.math.BigDecimal;

public class CompraDetalle {
	private int idDetalle;
	private Compras compras;
	private Libros libro;
	private int cantidad;
	private BigDecimal precioUniCompra;
	
	public CompraDetalle() {}

	public CompraDetalle(int idDetalle, Compras compras, Libros libro, int cantidad, BigDecimal precioUniCompra) {
		this.idDetalle = idDetalle;
		this.compras = compras;
		this.libro = libro;
		this.cantidad = cantidad;
		this.precioUniCompra = precioUniCompra;
	}

	public int getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Compras getCompras() {
		return compras;
	}

	public void setCompras(Compras compras) {
		this.compras = compras;
	}

	public Libros getLibro() {
		return libro;
	}

	public void setLibro(Libros libro) {
		this.libro = libro;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUniCompra() {
		return precioUniCompra;
	}

	public void setPrecioUniCompra(BigDecimal precioUniCompra) {
		this.precioUniCompra = precioUniCompra;
	}
}
