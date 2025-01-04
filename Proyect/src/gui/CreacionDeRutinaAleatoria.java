package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import domain.Ejercicio;
import domain.EjercicioEnEntrenamiento;
import domain.Entrenamiento;
import domain.Musculo;
import domain.Serie;
import domain.Serie.Esfuerzo;
// Comentarios y correcion de un error con Chat-gpt
public class CreacionDeRutinaAleatoria{
	public CreacionDeRutinaAleatoria(String usuario, String nombreRutina, String descripcionRutina, ArrayList<DayOfWeek> dias, ArrayList<String> musculos) {
		// TODO Auto-generated constructor stub
		ArrayList<Entrenamiento> entrenamientos = new ArrayList<Entrenamiento>();
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		ArrayList<Ejercicio> ejercicios2 = new ArrayList<Ejercicio>();
		ConectarBaseDeDatos.ConectarBaseDeDatosEjercicios(ejercicios2);
		Set<Ejercicio> ejerciciosSet = new HashSet<>();
		for (String musculo : musculos) {
		    for (Ejercicio ejercicio2 : ejercicios2) {
		        if ((ejercicio2.getMusculoPrincipal() != null && 
		             ejercicio2.getMusculoPrincipal().getNombre().equals(musculo)) || 
		            (ejercicio2.getMusculoSecundario() != null && 
		             ejercicio2.getMusculoSecundario().getNombre().equals(musculo))) {
		            ejerciciosSet.add(ejercicio2);
		        }
		    }
		}

		// Convierte el conjunto a una lista
		ejercicios = new ArrayList<>(ejerciciosSet);

		// Verifica que la lista no esté vacía
		if (ejercicios.isEmpty()) {
		    throw new IllegalStateException("No se encontraron ejercicios que coincidan con los músculos proporcionados.");
		}
		ArrayList<EjercicioEnEntrenamiento> ejerciciosEnEntrenamientos = new ArrayList<EjercicioEnEntrenamiento>();
		int idSerie = 0;
		int idEjercicioEnEntrenamiento = 0;
		int idEntrenamiento = 0;
		int idRutina = 0;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
		
			Statement stmt = conn.createStatement();
			try (PreparedStatement queryStmt = conn.prepareStatement("SELECT MAX(ID_Serie) AS max_id FROM Serie");
				     ResultSet rs = queryStmt.executeQuery()) {
				    if (rs.next()) {
				        idSerie = rs.getInt("max_id");
				    }
				}
			stmt.close();
			
			
			String sql2 = "SELECT MAX(ID_EjercicioEnEntrenamiento) AS max_id FROM EjercicioEnEntrenamiento";
			PreparedStatement queryStmt2 = conn.prepareStatement(sql2);
			ResultSet rs2 = queryStmt2.executeQuery();
		
			idEjercicioEnEntrenamiento=rs2.getInt("max_id");
			
			
			
			String sql3 = "SELECT MAX(ID_Entrenamiento) AS max_id FROM Entrenamiento";
			PreparedStatement queryStmt3 = conn.prepareStatement(sql3);
			ResultSet rs3 = queryStmt3.executeQuery();
		
			idEntrenamiento=rs3.getInt("max_id");
			
			
			
			String sql4 = "SELECT MAX(ID_Rutina) AS max_id FROM Rutina";
			PreparedStatement queryStmt4 = conn.prepareStatement(sql4);
			ResultSet rs4 = queryStmt4.executeQuery();
		
			idRutina=rs4.getInt("max_id");
			
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < dias.size(); i++) {
			entrenamientos.add(new Entrenamiento(idEntrenamiento+i+1, nombreRutina, descripcionRutina, idRutina+1, dias.get(i), new ArrayList<EjercicioEnEntrenamiento>()));
		}
		ejerciciosEnEntrenamientos = RutinaAleatoria(entrenamientos, ejerciciosEnEntrenamientos, musculos, ejercicios, idSerie, idEjercicioEnEntrenamiento);
		for(Entrenamiento entrenamiento : entrenamientos) {
			for(EjercicioEnEntrenamiento ejercicio : ejerciciosEnEntrenamientos) {
				if (ejercicio.getID_Entrenamiento() == entrenamiento.getId()) {
					entrenamiento.getEjercicios().add(ejercicio);
				}
			}
		}
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			
			String sql = "INSERT INTO Rutina VALUES(?, ?, ?, ?)";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			queryStmt.setInt(1, idRutina+1);
			queryStmt.setString(2, nombreRutina);
			queryStmt.setString(3, descripcionRutina);
			queryStmt.setString(4, usuario);
			queryStmt.executeUpdate();
			queryStmt.close();
			
			for(Entrenamiento entrenamiento : entrenamientos) {
				String sql2 = "INSERT INTO Entrenamiento VALUES(?, ?, ?, ?, ?)";
				PreparedStatement queryStmt2 = conn.prepareStatement(sql2);
				queryStmt2.setInt(1, entrenamiento.getId());
				queryStmt2.setString(2, "EntrenamientoAleatorio"+entrenamiento.getId());
				queryStmt2.setString(3, "DescripcionAleatoria"+entrenamiento.getId());
				queryStmt2.setInt(4, idRutina+1);
				queryStmt2.setString(5, entrenamiento.getDía().toString());
				queryStmt2.executeUpdate();
				queryStmt2.close();
				
				for(EjercicioEnEntrenamiento ejercicioEnEntrenamiento : entrenamiento.getEjercicios()) {
					String sql3 = "INSERT INTO EjercicioEnEntrenamiento VALUES(?, ?, ?, ?)";
					PreparedStatement queryStmt3 = conn.prepareStatement(sql3);
					queryStmt3.setInt(1, ejercicioEnEntrenamiento.getId());
					queryStmt3.setInt(2, entrenamiento.getId());
					queryStmt3.setInt(3, ejercicioEnEntrenamiento.getID_Ejercicio());
					queryStmt3.setInt(4, ejercicioEnEntrenamiento.getOrdeEnEntrenamiento());
					queryStmt3.executeUpdate();
					queryStmt3.close();
					
					for(Serie serie : ejercicioEnEntrenamiento.getSeries()) {
						String sql4 = "INSERT INTO Serie VALUES(?, ?, ?, ?, ?, ?)";
						PreparedStatement queryStmt4 = conn.prepareStatement(sql4);
						queryStmt4.setInt(1, serie.getId());
						queryStmt4.setInt(2, ejercicioEnEntrenamiento.getId());
						queryStmt4.setInt(3, serie.getEsfuerzo().ordinal()+1);
						queryStmt4.setInt(4, serie.getRepeticiones());
						queryStmt4.setDouble(5, serie.getPeso());
						queryStmt4.setInt(6, serie.getOrdenEnEjercicio());
						queryStmt4.executeUpdate();
						queryStmt4.close();
					}
				}
			}
			
			
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	public ArrayList<EjercicioEnEntrenamiento> RutinaAleatoria(
		    ArrayList<Entrenamiento> entrenamientos,
		    ArrayList<EjercicioEnEntrenamiento> ejerciciosEntrenamiento,
		    ArrayList<String> musculos,
		    ArrayList<Ejercicio> ejercicios,
		    int idSerie,
		    int idEjercicioEnEntrenamiento
		) {
		    // Validar si las listas entrenamientos y ejercicios tienen elementos
		    if (entrenamientos.isEmpty()) {
		        System.out.println("Error: La lista 'entrenamientos' está vacía.");
		        return null; // Devuelve null o maneja el error según la lógica de tu aplicación
		    }
		    if (ejercicios.isEmpty()) {
		        System.out.println("Error: La lista 'ejercicios' está vacía.");
		        return null;
		    }

		    // Generar índices aleatorios seguros
		    int alea2 = (int) (Math.random() * entrenamientos.size());
		    int alea = (int) (Math.random() * ejercicios.size());

		    // Crear un nuevo EjercicioEnEntrenamiento
		    EjercicioEnEntrenamiento ejercicio = new EjercicioEnEntrenamiento(
		        idEjercicioEnEntrenamiento + 1,
		        entrenamientos.get(alea2).getId(),
		        ejercicios.get(alea).getId(),
		        entrenamientos.get(alea2).getEjercicios().size() + 1,
		        new ArrayList<Serie>()
		    );

		    // Agregar una serie inicial
		    ejercicio.getSeries().add(new Serie(
		        idSerie + 1,
		        10,
		        15,
		        idEjercicioEnEntrenamiento + 1,
		        Esfuerzo.APROXIMACION,
		        1
		    ));

		    // Crear series adicionales
		    int alea3 = (int) ((Math.random() * 4) + 1); // Número de series adicionales
		    for (int i = 0; i < alea3; i++) {
		        int alea4 = (int) ((Math.random() * 8) + 8); // Repeticiones
		        int alea5 = (int) ((Math.random() * 2) + 1); // Tipo de esfuerzo
		        ejercicio.getSeries().add(new Serie(
		            idSerie + 2 + i,
		            10,
		            alea4,
		            idEjercicioEnEntrenamiento + 1,
		            Esfuerzo.values()[alea5],
		            ejercicio.getSeries().size() + 1
		        ));
		    }

		    // Agregar el ejercicio a la lista de ejercicios de entrenamiento
		    ejerciciosEntrenamiento.add(ejercicio);

		    // Preparar para filtrar músculos y ejercicios
		    ArrayList<String> musculos2 = new ArrayList<>();
		    ArrayList<Ejercicio> ejercicios2 = new ArrayList<>();
		    ArrayList<Ejercicio> ejercicios3 = new ArrayList<>();

		    // Llenar ejercicios desde la base de datos
		    ConectarBaseDeDatos.ConectarBaseDeDatosEjercicios(ejercicios3);

		    // Eliminar músculos ya trabajados y obtener una nueva lista de músculos
		    musculos2 = eliminarMusculosOptimos(ejerciciosEntrenamiento, ejercicios3, musculos);

		    // Filtrar ejercicios relacionados con los músculos restantes
		    for (String musculo : musculos2) {
		        for (Ejercicio ejercicio3 : ejercicios3) {
		            if (ejercicio3.getMusculoPrincipal().getNombre().equals(musculo) ||
		                ejercicio3.getMusculoSecundario().getNombre().equals(musculo)) {
		                ejercicios2.add(ejercicio3);
		            }
		        }
		    }

		    // Llamada recursiva para continuar generando la rutina
		    if (!musculos2.isEmpty() && !ejercicios2.isEmpty()) {
		        return RutinaAleatoria(
		            entrenamientos,
		            ejerciciosEntrenamiento,
		            musculos2,
		            ejercicios2,
		            idSerie + alea3 + 2,
		            idEjercicioEnEntrenamiento + 1
		        );
		    }

		    // Si no hay más músculos o ejercicios, devolver los entrenamientos
		    System.out.println("Rutina generada exitosamente.");
		    return ejerciciosEntrenamiento;
		}

	
	private ArrayList<String> eliminarMusculosOptimos(ArrayList<EjercicioEnEntrenamiento> ejerciciosEntrenamiento, ArrayList<Ejercicio> ejercicios, ArrayList<String> musculos){
		HashMap<String, Double> mapa = new HashMap<String, Double>();
    	for(EjercicioEnEntrenamiento ejercicio : ejerciciosEntrenamiento) {
    		for(Ejercicio ejercicio2 : ejercicios) {
    			if (ejercicio.getID_Ejercicio()== ejercicio2.getId()) {
    				for(Serie serie : ejercicio.getSeries()) {
						Double valorTamaño = 0.0;
						Double valorTamaño2 = 0.0;
						Double valorRPE = 0.0;
						if (ejercicio2.getMusculoPrincipal().getTamanoMusculo().equals(Musculo.TamanoMusculo.PEQUENO.toString())) {
							valorTamaño = 1.5;
						} else if (ejercicio2.getMusculoPrincipal().getTamanoMusculo().equals(Musculo.TamanoMusculo.MEDIANO.toString())) {
							valorTamaño = 1.0;
						} else { 
							valorTamaño = 0.5;
						}
						if (ejercicio2.getMusculoSecundario().getTamanoMusculo().equals(Musculo.TamanoMusculo.PEQUENO.toString())) {
							valorTamaño2 = 0.75;
						} else if (ejercicio2.getMusculoSecundario().getTamanoMusculo().equals(Musculo.TamanoMusculo.MEDIANO.toString())) {
							valorTamaño2 = 0.5;
						} else { 
							valorTamaño2 = 0.25;
						}
						if (serie.getEsfuerzo().equals(Serie.Esfuerzo.TOPSET)) {
							valorRPE = 1.5;
						} else if (serie.getEsfuerzo().equals(Serie.Esfuerzo.ESTANDAR)) {
							valorRPE = 1.0;
						} else { 
							valorRPE = 0.5;
						}
						if (!mapa.containsKey(ejercicio2.getMusculoPrincipal().getNombre())) {
							mapa.put(ejercicio2.getMusculoPrincipal().getNombre(), valorTamaño*valorRPE);
						} else {
							double valorPuesto = mapa.get(ejercicio2.getMusculoPrincipal().getNombre());
							mapa.put(ejercicio2.getMusculoPrincipal().getNombre(), valorTamaño*valorRPE+valorPuesto);
						}
						if (!mapa.containsKey(ejercicio2.getMusculoSecundario().getNombre())) {
							mapa.put(ejercicio2.getMusculoSecundario().getNombre(), valorTamaño2*valorRPE);
						} else {
							double valorPuesto2 = mapa.get(ejercicio2.getMusculoSecundario().getNombre());
							mapa.put(ejercicio2.getMusculoSecundario().getNombre(), valorTamaño2*valorRPE+valorPuesto2);
						}
					}
    			}
    		}
    	}
    	ArrayList<String> musculos2 = new ArrayList<String>();
    	for (String musculo : musculos) {
    	    Double valor = mapa.get(musculo); // Obtén el valor del mapa
    	    if (!(valor != null && (10 <= valor && valor <= 15))) { // Verifica que no sea null antes de comparar
    	        musculos2.add(musculo);
    	    }
    	}
    	return musculos2;
	}
	
	private boolean esOptimo(ArrayList<EjercicioEnEntrenamiento> ejerciciosEntrenamiento, ArrayList<Ejercicio> ejercicios, ArrayList<String> musculos) {
		HashMap<String, Double> mapa = new HashMap<String, Double>();
		boolean resultado = true;
    	for(EjercicioEnEntrenamiento ejercicio : ejerciciosEntrenamiento) {
    		for(Ejercicio ejercicio2 : ejercicios) {
    			if (ejercicio.getID_Ejercicio()== ejercicio2.getId()) {
    				for(Serie serie : ejercicio.getSeries()) {
						Double valorTamaño = 0.0;
						Double valorTamaño2 = 0.0;
						Double valorRPE = 0.0;
						if (ejercicio2.getMusculoPrincipal().getTamanoMusculo().equals(Musculo.TamanoMusculo.PEQUENO.toString())) {
							valorTamaño = 1.5;
						} else if (ejercicio2.getMusculoPrincipal().getTamanoMusculo().equals(Musculo.TamanoMusculo.MEDIANO.toString())) {
							valorTamaño = 1.0;
						} else { 
							valorTamaño = 0.5;
						}
						if (ejercicio2.getMusculoSecundario().getTamanoMusculo().equals(Musculo.TamanoMusculo.PEQUENO.toString())) {
							valorTamaño2 = 0.75;
						} else if (ejercicio2.getMusculoSecundario().getTamanoMusculo().equals(Musculo.TamanoMusculo.MEDIANO.toString())) {
							valorTamaño2 = 0.5;
						} else { 
							valorTamaño2 = 0.25;
						}
						if (serie.getEsfuerzo().equals(Serie.Esfuerzo.TOPSET)) {
							valorRPE = 1.5;
						} else if (serie.getEsfuerzo().equals(Serie.Esfuerzo.ESTANDAR)) {
							valorRPE = 1.0;
						} else { 
							valorRPE = 0.5;
						}
						if (!mapa.containsKey(ejercicio2.getMusculoPrincipal().getNombre())) {
							mapa.put(ejercicio2.getMusculoPrincipal().getNombre(), valorTamaño*valorRPE);
						} else {
							double valorPuesto = mapa.get(ejercicio2.getMusculoPrincipal().getNombre());
							mapa.put(ejercicio2.getMusculoPrincipal().getNombre(), valorTamaño*valorRPE+valorPuesto);
						}
						if (!mapa.containsKey(ejercicio2.getMusculoSecundario().getNombre())) {
							mapa.put(ejercicio2.getMusculoSecundario().getNombre(), valorTamaño2*valorRPE);
						} else {
							double valorPuesto2 = mapa.get(ejercicio2.getMusculoSecundario().getNombre());
							mapa.put(ejercicio2.getMusculoSecundario().getNombre(), valorTamaño2*valorRPE+valorPuesto2);
						}
					}
    			}
    		}
    	}
    	
    	for(String musculo : musculos) {
    		if (!((10 <= mapa.get(musculo)) || (15>= mapa.get(musculo)))) {
    			resultado = false;
    			break;
			}
    	}
    	return resultado;
	}
}
