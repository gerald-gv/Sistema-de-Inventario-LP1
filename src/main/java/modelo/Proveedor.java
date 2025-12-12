package modelo;

public class Proveedor {
	private int id;
	private String nombre;
	private String ruc;
	private String telefono;
	private String direccion;
	private String email;
	
	public Proveedor() {
	}
	
	public Proveedor(String nombre, String ruc, String telefono, String direccion, String email) {
		super();
		this.id = 0;
		this.nombre = nombre;
		this.ruc = ruc;
		this.telefono = telefono;
		this.direccion = direccion;
		this.email = email;
	}
 
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
