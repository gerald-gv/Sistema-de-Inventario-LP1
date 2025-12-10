package modelo;

public class Cliente {
	
	private int id;
	private String nombreNegocio;
	private String email;
	private String contacto;
	private String direccion;
	private String tipoCliente;
	
	public Cliente() {
		
	}

	public Cliente(int id, String nombreNegocio, String email, String contacto, String direccion, String tipoCliente) {
		super();
		this.id = id;
		this.nombreNegocio = nombreNegocio;
		this.email = email;
		this.contacto = contacto;
		this.direccion = direccion;
		this.tipoCliente = tipoCliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreNegocio() {
		return nombreNegocio;
	}

	public void setNombreNegocio(String nombreNegocio) {
		this.nombreNegocio = nombreNegocio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	
	
	
}
