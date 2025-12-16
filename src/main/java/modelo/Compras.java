package modelo;

import java.math.BigDecimal;
import java.sql.Date;

public class Compras {
	private int idCompra;
	private Proveedor proveedor;
	private BigDecimal totalCompra;
	private Date fecha;
	private Usuario usuario;

	
	public Compras() {}

	public Compras(int idCompra, Usuario usuario, Proveedor proveedor, BigDecimal totalCompra, Date fecha) {
		this.idCompra = idCompra;
		this.usuario = new Usuario();
		this.proveedor = proveedor;
		this.totalCompra = totalCompra;
		this.fecha = fecha;
	}

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}
	
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public BigDecimal getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
