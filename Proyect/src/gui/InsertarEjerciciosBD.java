package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Statement;

import domain.Ejercicio;




public class InsertarEjerciciosBD {
    
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

    public static void main(String[] args) {
    	// PECTORAL
    	insertarEjercicio(1, "Press de banca con barra", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(2, "Press de banca inclinado con barra", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(3, "Press de banca con mancuernas", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(4, "Press inclinado con mancuernas", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(5, "Aperturas en banco plano con mancuernas", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.HOMBRO.toString());
    	insertarEjercicio(6, "Fondos", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(7, "Press en máquina", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(8, "Cruce de cables superior", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.HOMBRO.toString());
    	insertarEjercicio(9, "Cruce de cables medio", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.HOMBRO.toString());
    	insertarEjercicio(10, "Cruce de cables inferior", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(11, "Press declinado con mancuernas", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(12, "Pec Deck", Ejercicio.Musculo.PECTORAL.toString(), Ejercicio.Musculo.HOMBRO.toString());

    	// ESPALDA
    	insertarEjercicio(13, "Remo con barra", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.TRAPECIO.toString());
    	insertarEjercicio(14, "Remo en máquina", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.BICEPS.toString());
    	insertarEjercicio(15, "Remo sentado con polea", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.BICEPS.toString());
    	insertarEjercicio(16, "Peso muerto convencional", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.GLUTEO.toString());
    	insertarEjercicio(17, "Dominadas", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.BICEPS.toString());
    	insertarEjercicio(18, "Jalón al pecho abierto en polea", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.BICEPS.toString());
    	insertarEjercicio(19, "Jalón al pecho cerrado en polea", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.BICEPS.toString());
    	insertarEjercicio(20, "Dominadas con agarre neutro", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.BICEPS.toString());
    	insertarEjercicio(21, "Rack Pull", Ejercicio.Musculo.ESPALDA_ALTA.toString(), Ejercicio.Musculo.TRAPECIO.toString());
    	insertarEjercicio(22, "Remo invertido con agarre supino", Ejercicio.Musculo.ESPALDA_ALTA.toString(), Ejercicio.Musculo.BICEPS.toString());
    	insertarEjercicio(23, "Muscle-up asistido", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(24, "Peso muerto sumo", Ejercicio.Musculo.DORSAL.toString(), Ejercicio.Musculo.GLUTEO.toString());
    	insertarEjercicio(25, "Remo en T", Ejercicio.Musculo.ESPALDA_ALTA.toString(), Ejercicio.Musculo.TRAPECIO.toString());

    	// HOMBRO
    	insertarEjercicio(26, "Press militar con barra", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(27, "Press militar con mancuernas", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(28, "Elevaciones laterales con mancuernas", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.TRAPECIO.toString());
    	insertarEjercicio(29, "Elevaciones frontales con mancuernas", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.PECTORAL.toString());
    	insertarEjercicio(30, "Elevaciones laterales con polea", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.TRAPECIO.toString());
    	insertarEjercicio(31, "Press de hombros en máquina", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(32, "Face Pull", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.ESPALDA_ALTA.toString());
    	insertarEjercicio(33, "Press de hombros sentado en máquina Smith", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.TRICEPS.toString());
    	insertarEjercicio(34, "Elevación lateral en máquina", Ejercicio.Musculo.HOMBRO.toString(), Ejercicio.Musculo.TRAPECIO.toString());

    	// BÍCEPS
    	insertarEjercicio(35, "Curl con barra", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(36, "Curl con mancuernas alternado", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(37, "Curl martillo con mancuernas", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(38, "Curl predicador con barra", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(39, "Curl inclinado con mancuernas", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(40, "Curl en cable con cuerda", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(41, "Curl araña", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(42, "Curl en banco scott", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(43, "Curl bayesian en polea", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(44, "Curl martillo en polea", Ejercicio.Musculo.BICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());

    	// TRÍCEPS
    	insertarEjercicio(45, "Extensión de tríceps en polea", Ejercicio.Musculo.TRICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(46, "Press francés con barra", Ejercicio.Musculo.TRICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(47, "Fondos", Ejercicio.Musculo.TRICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(48, "Extensión de tríceps por encima de la cabeza en polea", Ejercicio.Musculo.TRICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());

    	// CUÁDRICEPS
    	insertarEjercicio(49, "Sentadilla búlgara con mancuernas", Ejercicio.Musculo.CUADRICEPS.toString(), Ejercicio.Musculo.GLUTEO.toString());
    	insertarEjercicio(50, "Sentadilla libre con barra", Ejercicio.Musculo.CUADRICEPS.toString(), Ejercicio.Musculo.GLUTEO.toString());
    	insertarEjercicio(51, "Prensa", Ejercicio.Musculo.CUADRICEPS.toString(), Ejercicio.Musculo.GLUTEO.toString());
    	insertarEjercicio(52, "Extensiones de piernas", Ejercicio.Musculo.CUADRICEPS.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(53, "Sentadilla Hack en máquina", Ejercicio.Musculo.CUADRICEPS.toString(), Ejercicio.Musculo.GLUTEO.toString());
    	insertarEjercicio(54, "Sentadilla con mancuernas", Ejercicio.Musculo.CUADRICEPS.toString(), Ejercicio.Musculo.GLUTEO.toString());

    	// GEMELOS
    	insertarEjercicio(55, "Elevación de gemelos de pie con barra", Ejercicio.Musculo.GEMELO.toString(), Ejercicio.Musculo.GLUTEO.toString());
    	insertarEjercicio(56, "Elevación de gemelos sentado", Ejercicio.Musculo.GEMELO.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());

    	// GLÚTEOS
    	insertarEjercicio(57, "Hip Thrust", Ejercicio.Musculo.GLUTEO.toString(), Ejercicio.Musculo.CUADRICEPS.toString());
    	insertarEjercicio(58, "Abducción en máquina", Ejercicio.Musculo.GLUTEO.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(59, "Peso muerto rumano con mancuernas", Ejercicio.Musculo.GLUTEO.toString(), Ejercicio.Musculo.CUADRICEPS.toString());
    	insertarEjercicio(60, "Patadas de burro en polea", Ejercicio.Musculo.GLUTEO.toString(), Ejercicio.Musculo.CUADRICEPS.toString());

    	// ABDOMEN
    	insertarEjercicio(61, "Crunch en máquina", Ejercicio.Musculo.ABDOMINAL.toString(), Ejercicio.Musculo.ANTEBRAZO.toString());
    	insertarEjercicio(62, "Mountain Climbers", Ejercicio.Musculo.ABDOMINAL.toString(), Ejercicio.Musculo.GLUTEO.toString());
    	insertarEjercicio(63, "Plancha", Ejercicio.Musculo.ABDOMINAL.toString(), Ejercicio.Musculo.DORSAL.toString());
    	insertarEjercicio(64, "Plancha lateral", Ejercicio.Musculo.ABDOMINAL.toString(), Ejercicio.Musculo.DORSAL.toString());

    }
}
