package modelo;

public class Categoria {
	private int idCat;
	private String nombre;

	public Categoria() {
	}

	public Categoria(int idCat, String nombre) {
		super();
		this.idCat = idCat;
		this.nombre = nombre;
	}

	public int getIdCat() {
		return idCat;
	}

	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
