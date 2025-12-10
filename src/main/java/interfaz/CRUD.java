package interfaz;

import java.util.List;

public interface CRUD<T> {
	
	public List<T> listar();
	
	public T list(int id);
	
	public boolean add(T obj);
	
	public boolean edit(T obj);
	
	public boolean eliminar(int id);
}
