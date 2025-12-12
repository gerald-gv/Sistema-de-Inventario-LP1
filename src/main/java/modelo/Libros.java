package modelo;

import java.math.BigDecimal;

public class Libros {
	private int idLibro;
	private int idCat; //DEPENDE DE LA CATEGORIA
	private String titulo;
	private String autor;
	private String descripcion;
	private BigDecimal precioCompra;
	private BigDecimal precioVenta;
	private int stock;
	
	public Libros() {
	}

	public Libros(int idLibro, int idCat, String titulo, String autor, String descripcion, BigDecimal precioCompra,
			BigDecimal precioVenta, int stock) {
		this.idLibro = idLibro;
		this.idCat = idCat;
		this.titulo = titulo;
		this.autor = autor;
		this.descripcion = descripcion;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.stock = stock;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public int getIdCat() {
		return idCat;
	}

	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
