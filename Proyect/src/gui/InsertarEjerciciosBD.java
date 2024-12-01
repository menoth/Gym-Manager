package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Statement;

import domain.Ejercicio;




public class InsertarEjerciciosBD {
    
	public static void insertarEjercicio(String nombre, int idEntrenamiento, String musculoPrincipal, String musculoSecundario, int ordenEnEntrenamiento) {
		
    	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		
    	try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO Ejercicio (ID_Ejercicio, Nombre, ID_Entrenamiento, Musculo_Principal, Musculo_Secundario, OrdenEnEntrenamiento) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			 pstmt.setString(2, nombre); // Asignar el nombre
	         pstmt.setInt(1, idEntrenamiento); // Asignar el ID de entrenamiento
	         pstmt.setString(3, musculoPrincipal); // Asignar el músculo principal
	         pstmt.setString(4, musculoSecundario); // Asignar el músculo secundario
	         pstmt.setInt(5, ordenEnEntrenamiento); // Asignar el orden en el entrenamiento
			
	
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
	}

    public static void main(String[] args) {
        insertarEjercicio("Press de banca con barra", 1, Ejercicio.Musculo.ABDOMINAL.toString(), null, 0); // Pecho
    }
}
