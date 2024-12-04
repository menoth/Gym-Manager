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
import domain.EjercicioEnEntrenamiento;
import domain.Entrenamiento;
import domain.Musculo;
import domain.Musculo.TamanoMusculo;
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
	// funcion que recibe una lista de rutinas y carga desde la bd todo lo relacionado con ella: Rutina, Usuario, Entrenamiento, 
	// EjercicioEnEntranmiento, Serie
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
				String descripcion = rs.getString("Descripción");
				String usuario = rs.getString("Usuario");
				ArrayList<Entrenamiento> entrenamientos = new ArrayList<Entrenamiento>();
				
				
				// cargamos desde la base de datos todos los entrenamientos que tengan esa ID_Rutina en la lista de entrenamientos de la rutina
				String sql2 = "SELECT * FROM Entrenamiento WHERE ID_Rutina = ?";
				PreparedStatement queryStmt2 = conn.prepareStatement(sql2);
				queryStmt2.setInt(1, ID_Rutina);
				ResultSet rs2 = queryStmt2.executeQuery();
				while (rs2.next()) {
					int ID_Entrenamiento = rs2.getInt("ID_Entrenamiento");
					String nombreEntrenamiento = rs2.getString("Nombre");
					String descripcionEntrenamiento = rs2.getString("Descripción");
					int ID_Rutina2 = rs2.getInt("ID_Rutina");
					String dia = rs2.getString("Dia");
					DayOfWeek diaSemanal = DayOfWeek.valueOf(dia);
					ArrayList<EjercicioEnEntrenamiento> ejercicios = new ArrayList<EjercicioEnEntrenamiento>();
					
					
					// cargamos desde la base de datos todos los ejercicios que tengan esa ID_Entrenamiento en la lista ejercicios de el entrenamiento
					String sql3 = "SELECT * FROM EjercicioEnEntrenamiento WHERE ID_Entrenamiento = ?";
					PreparedStatement queryStmt3 = conn.prepareStatement(sql3);
					queryStmt3.setInt(1, ID_Entrenamiento);
					ResultSet rs3 = queryStmt3.executeQuery();
					while (rs3.next()) {
						int ID_EjercicioEnEntrenamiento = rs3.getInt("ID_EjercicioEnEntrenamiento");
						int ID_Entrenamiento2 = rs3.getInt("ID_Entrenamiento");
						int ID_Ejercicio = rs3.getInt("ID_Ejercicio");
						int OrdenEnEntrenamiento = rs3.getInt("OrdenEnEntrenamiento");
						ArrayList<Serie> series = new ArrayList<Serie>();
							
						// cargamos desde la base de datos todas las series que tengan ese ID_Ejercicio en la lista de series del ejercicio
						String sql4 = "SELECT * FROM Serie WHERE ID_EjercicioEnEntrenamiento = ?";
						PreparedStatement queryStmt4 = conn.prepareStatement(sql4);
						queryStmt4.setInt(1, ID_Ejercicio);
						ResultSet rs4 = queryStmt4.executeQuery();
						while (rs4.next()) {
							int ID_Serie = rs4.getInt("ID_Serie");
							float Peso = rs4.getFloat("Peso");
							int Repeticiones = rs4.getInt("Repeticiones");
							int ID_EjercicioEnEntrenamiento2 = rs4.getInt("ID_EjercicioEnEntrenamiento");
							int ID_RPE = rs4.getInt("ID_RPE");
							int OrdenEnEjercicio = rs4.getInt("OrdenEnEjercicio");
							
							// añadimos todo a la lista de rutina que recibimos
							series.add(new Serie(ID_Serie, Peso, Repeticiones, ID_RPE, ID_EjercicioEnEntrenamiento2, OrdenEnEjercicio));
						}					
						ejercicios.add(new EjercicioEnEntrenamiento(ID_EjercicioEnEntrenamiento, ID_Entrenamiento2, ID_Ejercicio, OrdenEnEntrenamiento, series));
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
	// funcion que recibe una lista de ejercicios y la llena de todos los ejercicios que tenemos en nuestra bd
	public static void ConectarBaseDeDatosEjercicios(List<Ejercicio> ejercicios) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Ejercicio";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			ResultSet rs = queryStmt.executeQuery();
	
			while (rs.next()) {
				int ID_Ejercicio = rs.getInt("ID_Ejercicio");
				String Nombre = rs.getString("Nombre");
				String MusculoPrincipal = rs.getString("MusculoPrincipal");
				String MusculoSecundario = rs.getString("MusculoSecundario");
				
				String sql2 = "SELECT * FROM Musculo WHERE Nombre LIKE ?";
				PreparedStatement queryStmt2 = conn.prepareStatement(sql2);
				queryStmt2.setString(1, MusculoPrincipal);
				ResultSet rs2 = queryStmt.executeQuery();
				String NombreMusculoPrincipal = rs2.getString("Nombre");
				String TamanoMusculoPrincipal = rs2.getString("TamanoMusculo");
				
				String sql3 = "SELECT * FROM Musculo WHERE Nombre LIKE ?";
				PreparedStatement queryStmt3 = conn.prepareStatement(sql3);
				queryStmt3.setString(1, MusculoSecundario);
				ResultSet rs3 = queryStmt.executeQuery();
				String NombreMusculoSecundario = rs3.getString("Nombre");
				String TamanoMusculoSecundario = rs3.getString("TamanoMusculo");
				
				ejercicios.add(new Ejercicio(ID_Ejercicio, Nombre, new Musculo(NombreMusculoPrincipal, TamanoMusculo.valueOf(TamanoMusculoPrincipal)), new Musculo(NombreMusculoSecundario, TamanoMusculo.valueOf(TamanoMusculoSecundario))));
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
	}
}
