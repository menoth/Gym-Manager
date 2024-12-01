package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import domain.Ejercicio;
import domain.Entrenamiento;
import domain.Rutina;
import domain.Serie;
import domain.Usuario;

// Lo usaremos para conectar la base de datos con una lista de usuarios
public class ConectarBaseDeDatos {
	public static void main(String[] args) {
		
	}
	public static void ConectarBaseDeDatos(List<Usuario> usuarios) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Usuario";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			ResultSet rs = queryStmt.executeQuery();
	
			while (rs.next()) {
				String nombre = rs.getString("Nombre");
				String apellidos = rs.getString("Apellidos");
				String usuario = rs.getString("Usuario");
				String correoElectronico = rs.getString("Correo");
				String contraseña = rs.getString("Contraseña");
				String descripcion = rs.getString("Descripcion");
				String fotoDePerfil = rs.getString("FotoDePerfil");
				usuarios.add(new Usuario(nombre, apellidos, usuario, correoElectronico, contraseña, descripcion, fotoDePerfil));
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
	}
	// cargamos desde la base de datos de rutinas, entrenamientos... en una lista que recibe la funcion ConectarBaseDeDatosRutina
	public static void ConectarBaseDeDatosRutina(List<Rutina> rutinas) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Rutina";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			ResultSet rs = queryStmt.executeQuery();
	
			while (rs.next()) {
				int ID_Rutina = rs.getInt("ID_Rutina");
				String nombre = rs.getString("Nombre");
				String descripcion = rs.getString("Descripcion");
				String usuario = rs.getString("Usuario");
				ArrayList<Entrenamiento> entrenamientos = new ArrayList<Entrenamiento>();
				
				
				// cargamos desde la base de datos todos los entrenamientos que tengan esa ID_Rutina en la lista de entrenamientos de la rutina
				String sql2 = "SELECT * FROM Entrenamiento WHERE ID_Rutina = ?";
				PreparedStatement queryStmt2 = conn.prepareStatement(sql2);
				queryStmt2.setInt(0, ID_Rutina);
				ResultSet rs2 = queryStmt2.executeQuery();
				while (rs2.next()) {
					int ID_Entrenamiento = rs2.getInt("ID_Entrenamiento");
					String nombreEntrenamiento = rs2.getString("Nombre");
					String descripcionEntrenamiento = rs2.getString("Descripción");
					int ID_Rutina2 = rs2.getInt("ID_Rutina");
					String dia = rs2.getString("Dia");
					DayOfWeek diaSemanal = DayOfWeek.valueOf(dia);
					ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
					
					
					// cargamos desde la base de datos todos los ejercicios que tengan esa ID_Entrenamiento en la lista ejercicios de el entrenamiento
					String sql3 = "SELECT * FROM Ejercicio WHERE ID_Entrenamiento = ?";
					PreparedStatement queryStmt3 = conn.prepareStatement(sql3);
					queryStmt3.setInt(0, ID_Entrenamiento);
					ResultSet rs3 = queryStmt3.executeQuery();
					while (rs3.next()) {
						int ID_Ejercicio = rs.getInt("ID_Ejercicio");
						String nombreEjercicio = rs3.getString("Nombre");
						int ID_Entrenamiento2 = rs3.getInt("ID_Entrenamiento");
						int MusculoPrincipal = rs3.getInt("Musculo_Principal");
						int MusculoSecundario = rs3.getInt("MusculoSecundario");
						int OrdenEnEntrenamiento = rs3.getInt("OrdenEnEntrenamiento");
						ArrayList<Serie> series = new ArrayList<Serie>();
						
						// cargamos el nombre del musculo principal
						String sql4 = "SELECT * FROM Musculo WHERE ID_Musculo = ?";
						PreparedStatement queryStmt4 = conn.prepareStatement(sql4);
						queryStmt4.setInt(0, MusculoPrincipal);
						ResultSet rs4 = queryStmt4.executeQuery();
						String nombreMusculoPrincipal = rs4.getString("Nombre");
						
						// cargamos el nombre del musculo secundario
						String sql5 = "SELECT * FROM Musculo WHERE ID_Musculo = ?";
						PreparedStatement queryStmt5 = conn.prepareStatement(sql5);
						queryStmt5.setInt(0, MusculoSecundario);
						ResultSet rs5 = queryStmt5.executeQuery();
						String nombreMusculoSecundario = rs5.getString("Nombre");
						
						// cargamos desde la base de datos todas las series que tengan ese ID_Ejercicio en la lista de series del ejercicio
						String sql6 = "SELECT * FROM Serie WHERE ID_Ejercicio = ?";
						PreparedStatement queryStmt6 = conn.prepareStatement(sql6);
						queryStmt6.setInt(0, ID_Ejercicio);
						ResultSet rs6 = queryStmt6.executeQuery();
						while (rs6.next()) {
							int ID_Serie = rs6.getInt("ID_Serie");
							float Peso = rs6.getFloat("Peso");
							int Repeticiones = rs6.getInt("Repeticiones");
							int ID_Ejercicio2 = rs6.getInt("ID_Ejercicio");
							int ID_RPE = rs6.getInt("ID_RPE");
							int OrdenEnEjercicio = rs6.getInt("OrdenEnEjercicio");
							
							// añadimos todo a la lista de rutina que recibimos
							series.add(new Serie(ID_Serie, Peso, Repeticiones, ID_Ejercicio2, ID_RPE, OrdenEnEjercicio));
						}					
						ejercicios.add(new Ejercicio(ID_Ejercicio, nombreEjercicio, ID_Entrenamiento2, Ejercicio.Musculo.valueOf(nombreMusculoPrincipal), Ejercicio.Musculo.valueOf(nombreMusculoSecundario), series, OrdenEnEntrenamiento));
						
					}
					entrenamientos.add(new Entrenamiento(ID_Entrenamiento, nombreEntrenamiento, descripcionEntrenamiento, ID_Rutina2, diaSemanal, ejercicios));
					
				}
				rutinas.add(new Rutina(ID_Rutina, nombre, descripcion, entrenamientos, usuario));
			}
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
	}
}

