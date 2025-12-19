package modelo;

// CREE ESTA CLASE PARA LOS DETALLES DE VENTA
public class DetalleVenta {
	private int idDetalle;
	private int idVenta;
	private int idLibro;
	private String tituloLibro;
	private String autor;
	private String editorial;
	private int cantidad;
	private double precioUnitario;
	private double subtotal;
	
	
	public DetalleVenta() {
	}
	
	public DetalleVenta(int idDetalle, int idVenta, int idLibro, String tituloLibro, String autor, String editorial,
			int cantidad, double precioUnitario, double subtotal) {
		super();
		this.idDetalle = idDetalle;
		this.idVenta = idVenta;
		this.idLibro = idLibro;
		this.tituloLibro = tituloLibro;
		this.autor = autor;
		this.editorial = editorial;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
	}

	public int getIdDetalle() { return idDetalle; }
	public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
	
	public int getIdVenta() { return idVenta; }
	public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
	
	public int getIdLibro() { return idLibro; }
	public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
	
	public String getTituloLibro() { return tituloLibro; }
	public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
	
	public String getAutor() { return autor; }
	public void setAutor(String autor) { this.autor = autor; }
	
	public String getEditorial() { return editorial; }
	public void setEditorial(String editorial) { this.editorial = editorial; }
	
	public int getCantidad() { return cantidad; }
	public void setCantidad(int cantidad) { this.cantidad = cantidad; }
	
	public double getPrecioUnitario() { return precioUnitario; }
	public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
	
	public double getSubtotal() { return subtotal; }
	public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
