package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/inventorysystem";
	private static final String USUARIO = "root";
	private static final String PASSWORD = "tucontraseñalocal :)";
	
	static {
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public Connection Conectar() {
		
		Connection cnx = null;
		
		
		try {
			cnx = DriverManager.getConnection(URL, USUARIO, PASSWORD);
			System.out.println("Se ha conectado correctamente a la BD");
		} catch (SQLException e) {
			System.out.println("Ha sucedio un error a la conexion: " + e);
			e.printStackTrace();
		}
		
		return cnx;
	}
}
