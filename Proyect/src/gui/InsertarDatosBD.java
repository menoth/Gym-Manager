package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;





public class InsertarDatosBD {
    
	public static void insertarEjercicio(int idEjercicio, String nombreEjercicio, String musculoPrincipal, String musculoSecundario) {
		
    	try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Se ha cargado el driver correctamente.");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		
    	try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
			System.out.println("Se ha conectado a la base de datos.");
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO Ejercicio (ID_Ejercicio, Nombre, Musculo_Principal, Musculo_Secundario) VALUES (?, ?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, idEjercicio); // Asignar el ID de entrenamiento
			pstmt.setString(2, nombreEjercicio); // Asignar el nombre
	        pstmt.setString(3, musculoPrincipal); // Asignar el músculo principal
	        pstmt.setString(4, musculoSecundario); // Asignar el músculo secundario
			
	
			pstmt.executeUpdate();
			System.out.println("Ejercicio Insertado correctamente. Verifica la BD.");
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se ha podido cargar el ejercicio.");
		}		
	}
	
	public static void insertarMusculo(String Nombre, String TamanoMusculo) {
		
    	try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Se ha cargado el driver correctamente.");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		
    	try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
			System.out.println("Se ha conectado a la base de datos.");
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO Musculo VALUES (?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, Nombre); // Asignar el nombre del musculo
	        pstmt.setString(2, TamanoMusculo); // Asignar el tamaño del musculo
			
	
			pstmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se ha podido cargar el musculo.");
		}		
	}
	
	public static boolean musculoExiste(String nombreMusculo) {
	    String sql = "SELECT COUNT(*) FROM Musculo WHERE Nombre = ?";
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, nombreMusculo);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	
	public static void insertarRetoDiario(int ID_RetoDiario, String Nombre, String Fecha, int Dificultad, int Completado, String Usuario) {
		
    	try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Se ha cargado el driver correctamente.");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		
    	try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
			System.out.println("Se ha conectado a la base de datos.");
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO RetoDiario VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, ID_RetoDiario); // Asignar el id del reto
	        pstmt.setString(2, Nombre); // Asignar el nombre del reto
	        pstmt.setString(3, Fecha); // Asignar la fecha del reto
	        pstmt.setInt(4, Dificultad); //Asignar la dificultad del reto
	        pstmt.setInt(5, Completado); //Asignar si el reto esta completado
	        pstmt.setString(6, Usuario); // Asignar el usuario del reto
	
			pstmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se ha podido cargar el musculo.");
		}		
	}
	
	
	

    public static void main(String[] args) {
    	// PECHO
    	insertarEjercicio(1, "Press de banca con barra", "PECTORAL", "TRICEPS");
    	insertarEjercicio(2, "Press de banca inclinado con barra", "PECTORAL", "TRICEPS");
    	insertarEjercicio(3, "Press de banca con mancuernas", "PECTORAL", "TRICEPS");
    	insertarEjercicio(4, "Press inclinado con mancuernas", "PECTORAL", "TRICEPS");
    	insertarEjercicio(5, "Aperturas en banco plano con mancuernas", "PECTORAL", "HOMBRO");
    	insertarEjercicio(6, "Fondos", "PECTORAL", "TRICEPS");
    	insertarEjercicio(7, "Press en máquina", "PECTORAL", "TRICEPS");
    	insertarEjercicio(8, "Cruce de cables superior", "PECTORAL", "HOMBRO");
    	insertarEjercicio(9, "Cruce de cables medio", "PECTORAL", "HOMBRO");
    	insertarEjercicio(10, "Cruce de cables inferior", "PECTORAL", "TRICEPS");
    	insertarEjercicio(11, "Press declinado con mancuernas", "PECTORAL", "TRICEPS");
    	insertarEjercicio(12, "Pec Deck", "PECTORAL", "HOMBRO");

    	// ESPALDA
    	insertarEjercicio(13, "Remo con barra", "DORSAL", "TRAPECIO");
    	insertarEjercicio(14, "Remo en máquina", "DORSAL", "BICEPS");
    	insertarEjercicio(15, "Remo sentado con polea", "DORSAL", "BICEPS");
    	insertarEjercicio(16, "Peso muerto convencional", "DORSAL", "GLUTEO");
    	insertarEjercicio(17, "Dominadas", "DORSAL", "BICEPS");
    	insertarEjercicio(18, "Jalón al pecho abierto en polea", "DORSAL", "BICEPS");
    	insertarEjercicio(19, "Jalón al pecho cerrado en polea", "DORSAL", "BICEPS");
    	insertarEjercicio(20, "Dominadas con agarre neutro", "DORSAL", "BICEPS");
    	insertarEjercicio(21, "Rack Pull", "ESPALDA_ALTA", "TRAPECIO");
    	insertarEjercicio(22, "Remo invertido con agarre supino", "ESPALDA_ALTA", "BICEPS");
    	insertarEjercicio(23, "Muscle-up asistido", "DORSAL", "TRICEPS");
    	insertarEjercicio(24, "Peso muerto sumo", "DORSAL", "GLUTEO");
    	insertarEjercicio(25, "Remo en T", "ESPALDA_ALTA", "TRAPECIO");

    	// HOMBRO
    	insertarEjercicio(26, "Press militar con barra", "HOMBRO", "TRICEPS");
    	insertarEjercicio(27, "Press militar con mancuernas", "HOMBRO", "TRICEPS");
    	insertarEjercicio(28, "Elevaciones laterales con mancuernas", "HOMBRO", "TRAPECIO");
    	insertarEjercicio(29, "Elevaciones frontales con mancuernas", "HOMBRO", "PECTORAL");
    	insertarEjercicio(30, "Elevaciones laterales con polea", "HOMBRO", "TRAPECIO");
    	insertarEjercicio(31, "Press de hombros en máquina", "HOMBRO", "TRICEPS");
    	insertarEjercicio(32, "Face Pull", "HOMBRO", "ESPALDA_ALTA");
    	insertarEjercicio(33, "Press de hombros sentado en máquina Smith", "HOMBRO", "TRICEPS");
    	insertarEjercicio(34, "Elevación lateral en máquina", "HOMBRO", "TRAPECIO");

    	// BÍCEPS
    	insertarEjercicio(35, "Curl con barra", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(36, "Curl con mancuernas alternado", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(37, "Curl martillo con mancuernas", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(38, "Curl predicador con barra", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(39, "Curl inclinado con mancuernas", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(40, "Curl en cable con cuerda", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(41, "Curl araña", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(42, "Curl en banco scott", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(43, "Curl bayesian en polea", "BICEPS", "ANTEBRAZO");
    	insertarEjercicio(44, "Curl martillo en polea", "BICEPS", "ANTEBRAZO");

    	// TRÍCEPS
    	insertarEjercicio(45, "Extensión de tríceps en polea", "TRICEPS", "ANTEBRAZO");
    	insertarEjercicio(46, "Press francés con barra", "TRICEPS", "ANTEBRAZO");
    	insertarEjercicio(47, "Fondos", "TRICEPS", "ANTEBRAZO");
    	insertarEjercicio(48, "Extensión de tríceps por encima de la cabeza en polea", "TRICEPS", "ANTEBRAZO");

    	// CUÁDRICEPS
    	insertarEjercicio(49, "Sentadilla búlgara con mancuernas", "CUADRICEPS", "GLUTEO");
    	insertarEjercicio(50, "Sentadilla libre con barra", "CUADRICEPS", "GLUTEO");
    	insertarEjercicio(51, "Prensa", "CUADRICEPS", "GLUTEO");
    	insertarEjercicio(52, "Extensiones de piernas", "CUADRICEPS", "ANTEBRAZO");
    	insertarEjercicio(53, "Sentadilla Hack en máquina", "CUADRICEPS", "GLUTEO");
    	insertarEjercicio(54, "Sentadilla con mancuernas", "CUADRICEPS", "GLUTEO");

    	// GEMELOS
    	insertarEjercicio(55, "Elevación de gemelos de pie con barra", "GEMELO", "GLUTEO");
    	insertarEjercicio(56, "Elevación de gemelos sentado", "GEMELO", "ANTEBRAZO");

    	// GLÚTEOS
    	insertarEjercicio(57, "Hip Thrust", "GLUTEO", "CUADRICEPS");
    	insertarEjercicio(58, "Abducción en máquina", "GLUTEO", "ANTEBRAZO");
    	insertarEjercicio(59, "Peso muerto rumano con mancuernas", "GLUTEO", "CUADRICEPS");
    	insertarEjercicio(60, "Patadas de burro en polea", "GLUTEO", "CUADRICEPS");

    	// ABDOMEN
    	insertarEjercicio(61, "Crunch en máquina", "ABDOMINAL", "ANTEBRAZO");
    	insertarEjercicio(62, "Mountain Climbers", "ABDOMINAL", "GLUTEO");
    	insertarEjercicio(63, "Plancha", "ABDOMINAL", "DORSAL");
    	insertarEjercicio(64, "Plancha lateral", "ABDOMINAL", "DORSAL");

    	
    	String[][] musculos = {
                {"PECTORAL", "GRANDE"},
                {"ANTEBRAZO", "MEDIANO"},
                {"ABDOMINAL", "MEDIANO"},
                {"CUADRICEPS", "GRANDE"},
                {"CUELLO", "PEQUENO"},
                {"TRAPECIO", "MEDIANO"},
                {"HOMBRO", "PEQUENO"},
                {"BICEPS", "MEDIANO"},
                {"DORSAL", "GRANDE"},
                {"ESPALDA_ALTA", "GRANDE"},
                {"FEMORAL", "GRANDE"},
                {"GEMELO", "MEDIANO"},
                {"TRICEPS", "MEDIANO"},
                {"GLUTEO", "GRANDE"},
                {"CARDIO", "GRANDE"} // Cardio tecniacamente no tiene tamaño físico pero como lo consideramos importante le ponemos grande
            };
    	for (String[] musculo : musculos) {
    		insertarMusculo(musculo[0], musculo[1]);
    	}
    	insertarRetoDiario(0, "Flexiones", "06/12/2024", 1, 1, "aaa");
    }
}
