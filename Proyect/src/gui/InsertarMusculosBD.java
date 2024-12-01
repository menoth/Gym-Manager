package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Ejercicio;

public class InsertarMusculosBD {
	
	public static void insertarMusculo(int idMusculo, Ejercicio.Musculo musculo) {
		
    	try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		
    	try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO Ejercicio (ID_Musculo, Nombre) VALUES (?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
	         pstmt.setInt(1, idMusculo); // Asignar el ID de entrenamiento
	         pstmt.setString(2, musculo.name()); // Asignar el músculo principal

			
	
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
	}


    public static void main(String[] args) {
        insertarMusculo(1, Ejercicio.Musculo.ABDOMINAL);
    }

}
