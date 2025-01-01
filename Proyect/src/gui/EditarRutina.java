package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import domain.EjercicioEnEntrenamiento;
import domain.Entrenamiento;
import domain.Rutina;
import domain.Serie;
import domain.Serie.Esfuerzo;

public class EditarRutina extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	public EditarRutina(String usuario, int idRutina, Rutina rutina) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Perfil");
		
		
		this.setLayout(new BorderLayout());
		
		//Ordenamos la rutina por dias de la semana
		rutina.getEntrenamientos().sort((e1, e2) -> e1.getDía().compareTo(e2.getDía()));

//----------------------------------------------PANEL NORTE------------------------------------------------		
		JPanel panelNorte = new JPanel();
		panelNorte.setLayout(new GridLayout(2, 1));
		
		JPanel panelNorteNorte = new JPanel();
		panelNorteNorte.setLayout(new FlowLayout(EditarRutina.WIDTH, 340, 30));
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setPreferredSize(new Dimension(120, 50));
		
		JTextField nombreRutina = new JTextField(rutina.getNombre());
		nombreRutina.setPreferredSize(new Dimension(250,40));
		nombreRutina.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JButton guardarCambios = new JButton("Guardar");
		guardarCambios.setPreferredSize(new Dimension(120, 50));
		
		panelNorteNorte.add(botonVolver);
		panelNorteNorte.add(nombreRutina);
		panelNorteNorte.add(guardarCambios);
		
		
		JPanel panelNorteSur = new JPanel();
		panelNorteSur.setLayout(new FlowLayout());
		
		//Creamos un jTextArea que será la descripción de la rutina
		JTextField desc = new JTextField(rutina.getDescripcionRutina());

		// Detalles del JTextArea
		desc.setFont(new Font("Arial", Font.PLAIN, 18));
		desc.setPreferredSize(new Dimension(1000, 40));
		
		panelNorteSur.add(desc);
		
		panelNorte.add(panelNorteNorte);
		panelNorte.add(panelNorteSur);
		
		//Action listener para el boton guardar
		guardarCambios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nombreRutina.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(EditarRutina.this, "El campo no puede estar vacío o contener solo espacios.");
					nombreRutina.setText("");
				}
				else if (desc.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(EditarRutina.this, "El campo no puede estar vacío o contener solo espacios.");
					desc.setText("");
				}
				
				else if ((nombreRutina.getText().length()>30) || (desc.getText().length()> 70)) {
							JOptionPane.showMessageDialog(EditarRutina.this, "La longitud maxima del nombre es de 30 caracteres y la de la descripción de 70");
						}
						else {
							actualizarNombreDesc(nombreRutina.getText(), desc.getText(), idRutina);
						}
					
			}
		});

		//Action listener boton volver
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				new PerfilUsuario(usuario);
				dispose();
				
			}
		});
		
		this.add(panelNorte, BorderLayout.NORTH);
		
//-------------------------------------PANEL CENTRAL-----------------------------------------------------	
		ArrayList<Integer> entrenamientosSemana = new ArrayList<>();
		cargarEntrenamientos(rutina, entrenamientosSemana);
		
		ArrayList<String> listaDias = new ArrayList<>();
		listaDias.add("Lunes");
	    listaDias.add("Martes");
	    listaDias.add("Miércoles");
	    listaDias.add("Jueves");
	    listaDias.add("Viernes");
	    listaDias.add("Sábado");
	    listaDias.add("Domingo");
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBorder(new EmptyBorder(10,10,10,10));
		
		panelCentral.setBackground(Color.black);
		panelCentral.setLayout(new GridLayout(1, 3));
	
		JPanel panel1 = new JPanel();
		
		//Panel 1 con los entrenamientos de la semana
		panel1.setLayout(new GridLayout(7, 1));

		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		
		JPanel panel3 = new JPanel();

		int indice = 0;
		for (int i = 0; i < 7; i++) {

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 3));
			
			JPanel panel11 = new JPanel();
			panel11.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			JLabel lunes = new JLabel(listaDias.get(i));
			
	        lunes.setFont(new Font("Arial", Font.PLAIN, 20));
	        lunes.setBackground(this.getBackground());
	        
			panel11.add(lunes);
			panel.add(lunes);
			
			
			if(entrenamientosSemana.get(i) == 1) {
				JPanel panel12 = new JPanel();
				panel12.setBorder(new EmptyBorder(10,10,10,10));
				panel12.setLayout(new GridLayout(2, 1, 30, 10));
				
				JTextField nombre = new JTextField(rutina.getEntrenamientos().get(indice).getNombre());
				JTextField desc2 = new JTextField(rutina.getEntrenamientos().get(indice).getDescripcionEntrenamiento());
				
				panel12.add(nombre);
				panel12.add(desc2);
				
				panel.add(panel12);
				
				JPanel panel13 = new JPanel();
				panel13.setBorder(new EmptyBorder(10,10,10,10));
				panel13.setLayout(new GridLayout(3, 1, 15, 5));
				
				JButton guardar = new JButton("Guardar");
				JButton eliminar = new JButton("Eliminar");
				JButton editar = new JButton("Editar");
				
				int indice2 = indice;
				
				//Action listener para guardar nombre y descripción del entrenamiento
				guardar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (nombre.getText().trim().isEmpty()) {
							JOptionPane.showMessageDialog(EditarRutina.this, "El campo no puede estar vacío o contener solo espacios.");
							nombre.setText("");
						}
						else if (desc2.getText().trim().isEmpty()) {
							JOptionPane.showMessageDialog(EditarRutina.this, "El campo no puede estar vacío o contener solo espacios.");
							desc2.setText("");
						}
						
						else if ((nombre.getText().length()>30) || (desc2.getText().length()> 70)) {
									JOptionPane.showMessageDialog(EditarRutina.this, "La longitud maxima del nombre es de 30 caracteres y la de la descripción de 70");
								}
								else {
									actualizarNombreDescEntrenamiento(nombre.getText(), desc2.getText(), rutina.getEntrenamientos().get(indice2).getId());
								}
					}
				});
				
				//Action listener para borrar un entrenamiento(semana)
				eliminar.addActionListener(new ActionListener() {			
					@Override
					public void actionPerformed(ActionEvent e) {
						EliminarEntrenamiento(rutina.getEntrenamientos().get(indice2).getId(), usuario); 	
						
					}
				});
				
				//Action listener de editar
				editar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
						
						panel2.removeAll();
						panel3.removeAll();
						
						
						int indice3 = 0;
						panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
						
						for (EjercicioEnEntrenamiento ejercicio : rutina.getEntrenamientos().get(indice2).getEjercicios()) {
							
							JPanel panelEjercicio = new JPanel();
							
							int id = ejercicio.getID_Ejercicio();

							JPanel panelSubEjercicio = new JPanel();
							panelSubEjercicio.setLayout(new FlowLayout());
							panelSubEjercicio.setBorder(new EmptyBorder(25,10,10,25));
							panelSubEjercicio.setPreferredSize(new Dimension(470, 100));
							
							panelSubEjercicio.add(new JLabel(nombreEjercicio(id)));
													
							JButton botonEditar = new JButton("Editar");
							botonEditar.setPreferredSize(new Dimension(70, 50));
							panelSubEjercicio.add(botonEditar);
							
							int indice4 = indice3;
							botonEditar.addActionListener(new ActionListener() {
								
								@Override
								
								public void actionPerformed(ActionEvent e) {
									panel3.removeAll();
									
									List<Serie> listaSeries = rutina.getEntrenamientos().get(indice2).getEjercicios().get(indice4).getSeries();
									
									JPanel panelSerie = new JPanel();
									panelSerie.setLayout(new BoxLayout(panelSerie, BoxLayout.Y_AXIS));
									
									for (Serie serie : listaSeries) {
										JPanel panelSubSerie = new JPanel();
										panelSubSerie.setLayout(new FlowLayout());
										panelSubSerie.setBorder(new EmptyBorder(25,10,10,25));
										panelSubSerie.setPreferredSize(new Dimension(panel3.getWidth(),100));
										
										JComboBox<Integer> ordenSerie = new JComboBox<>();
										for (int j = 0; j < listaSeries.size(); j++) {
											ordenSerie.addItem(j+1);
										}
										
										ordenSerie.setSelectedItem(serie.getOrdenEnEjercicio());
										
										
										JComboBox<Float> pesoSerie = new JComboBox<>();
										
										for (float i = 1; i <= 120; i += 0.5) {
										    pesoSerie.addItem(i);
										}
										
										pesoSerie.setSelectedItem(serie.getPeso());
										
										JComboBox<Integer> repeticionesSerie = new JComboBox<>();
										for (int i = 1; i <= 100; i++) {
										    repeticionesSerie.addItem(i);
										}
										
										repeticionesSerie.setSelectedItem(serie.getRepeticiones());
										
										JComboBox<Esfuerzo> esfuerzoSerie = new JComboBox<>();
										for (Esfuerzo esfuerzo : Esfuerzo.values()) {
											esfuerzoSerie.addItem(esfuerzo);
										}
										
										JButton eliminarSerie = new JButton("Eliminar serie");
										
										esfuerzoSerie.setSelectedItem(serie.getEsfuerzo());
										
										//Action listeners para cuando se hagan cambios en el JComboBox
										//Action listener ordenSerie
										ordenSerie.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												cambiarOrdenSerie(listaSeries, serie, (int) ordenSerie.getSelectedItem(), usuario, idRutina, rutina);
												
											}
										});
										
										//Action listener pesoSerie
										pesoSerie.addActionListener(new ActionListener() {
											
											@Override
											public void actionPerformed(ActionEvent e) {
												actualizarPesoSerie((float) pesoSerie.getSelectedItem(), serie, usuario);
												
											}
										});
										
										//Action listener repeticiones
										repeticionesSerie.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												actualizarRepeticionesSerie((int) repeticionesSerie.getSelectedItem(), serie, usuario);
												
											}
										});
										
										//Action listener Esfuerzo
										esfuerzoSerie.addActionListener(new ActionListener() {								
											@Override
											public void actionPerformed(ActionEvent e) {
												actualizarEsfuerzo((Esfuerzo)esfuerzoSerie.getSelectedItem(), serie, usuario);
												
											}
										});
										
										//Action listener eliminar serie
										eliminarSerie.addActionListener(new ActionListener() {											
											@Override
											public void actionPerformed(ActionEvent e) {
												eliminarSerie(serie, usuario);
											}
										});
										
										panelSubSerie.add(ordenSerie);
										panelSubSerie.add(pesoSerie);
										panelSubSerie.add(repeticionesSerie);
										panelSubSerie.add(esfuerzoSerie);
										panelSubSerie.add(eliminarSerie);
										
										
										panelSerie.add(panelSubSerie);
									}
									
									JPanel panelAñadirSerie = new JPanel();
									panelAñadirSerie.setLayout(new FlowLayout());
									panelAñadirSerie.setBorder(new EmptyBorder(20,20,20,20));
									
									JButton añadirSerie = new JButton("Añadir");
									añadirSerie.setPreferredSize(new Dimension(100, 50));
									
									//Action listener añadirSerie 
									añadirSerie.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											añadirSerie(rutina.getEntrenamientos().get(indice2).getEjercicios().get(indice4).getId(), usuario);
											
										}
									});
									
									panelAñadirSerie.add(añadirSerie);
									
									panelSerie.add(panelAñadirSerie);
									
									panel3.add(panelSerie);
									panel3.updateUI();
									
								}
							});
							
							JButton botonEliminar = new JButton("Eliminar");
							botonEliminar.setPreferredSize(new Dimension(80,50));
							panelSubEjercicio.add(botonEliminar);
							
							panelEjercicio.add(panelSubEjercicio);
							
							//Action listener de eliminar ejercicio entero y con ello sus series mediante delete on cascade
							botonEliminar.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									eliminarEjercicio(rutina.getEntrenamientos().get(indice2).getEjercicios().get(indice4).getId());
									dispose();
									new PerfilUsuario(usuario);
									
								}
							});

							panel2.add(panelEjercicio);
							indice3 += 1;
							
						}
						JButton añadirEjercicio = new JButton("Añadir");
						añadirEjercicio.setPreferredSize(new Dimension(70, 50));
						panel2.add(añadirEjercicio);
						
						//Action listener para añadir un nuevo ejercicio
						añadirEjercicio.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								añadirEjercicio(usuario, idRutina, rutina, rutina.getEntrenamientos().get(indice2).getId());
								
							}
						});
						
						panel2.updateUI();
						panel3.updateUI();
						
					}
				});
				
				indice += 1;
				panel13.add(guardar);
				panel13.add(eliminar);
				panel13.add(editar);
				
				panel.add(panel13);
				
				panel1.add(panel);
			}else {
				JPanel panel12 = new JPanel();
				panel12.setBorder(new EmptyBorder(20,10,10,10));
				panel12.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
				
				JButton botonAñadir = new JButton("AÑADIR");
				panel12.add(botonAñadir);
				int contadorEntrenamiento = i;
				
				//Action listener para añadir un nuevo Entrenamiento a la semana
				botonAñadir.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						AñadirEntrenamiento(contadorEntrenamiento, rutina.getId(), usuario);
					}
				});				
				panel.add(panel12);
				panel.add(new JLabel(""));
				panel1.add(panel);
			}
			
		}
		panelCentral.add(panel1);

		JScrollPane scrollPane2 = new JScrollPane(panel2);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        
        panelCentral.add(scrollPane2);
		
        JScrollPane scrollPane3 = new JScrollPane(panel3);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelCentral.add(scrollPane3);
		
		this.add(panelCentral, BorderLayout.CENTER);
		
		// Detalles ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	protected ArrayList<Serie> cargarSerie(int id) {
		ArrayList<Serie> listaSeries = new ArrayList<>();
		
		return listaSeries;
	}
	
	protected void EliminarEntrenamiento(int idEntrenamiento, String usuario) {
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha podido cargar el driver de la BD");
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	        String sql = "DELETE FROM Entrenamiento WHERE ID_Entrenamiento = ?";
	        try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	            queryStmt.setInt(1, idEntrenamiento); 

	            int filasAfectadas = queryStmt.executeUpdate();
	            if (filasAfectadas > 0) {
	                System.out.println("Serie eliminada correctamente.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    dispose();
	    new PerfilUsuario(usuario);
	}
	
	protected void AñadirEntrenamiento(int id, int idRutina, String usuario) {
		String dia = "";
		dia = DayOfWeek	.values()[id].name();
		    
		try {
			Class.forName("org.sqlite.JDBC");
			
		} catch (ClassNotFoundException e) {
		        System.out.println("No se ha podido cargar el driver de la BD");
		        e.printStackTrace();
		}
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {

	    	String sql = "INSERT INTO Entrenamiento (Nombre, Descripción, ID_Rutina, Dia) VALUES (?, ?, ?, ?)";
		        
	    	try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	    		queryStmt.setString(1, "Nombre");
		        queryStmt.setString(2, "Descripción");
		        queryStmt.setInt(3, idRutina);
		        queryStmt.setString(4, dia);
		        
				int filasAfectadas = queryStmt.executeUpdate();
		        System.out.println(filasAfectadas);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    dispose();
		    new PerfilUsuario(usuario);
		}
	
	protected void actualizarNombreDescEntrenamiento(String nombre, String desc, int idEntrenamiento) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "UPDATE Entrenamiento SET Nombre = ?, Descripción = ? WHERE ID_Entrenamiento = ?";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			queryStmt.setString(1, nombre);
			queryStmt.setString(2, desc);
			queryStmt.setInt(3, idEntrenamiento);

			queryStmt.executeUpdate();
			
			queryStmt.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void añadirEjercicio(String usuario, int idRutina, Rutina rutina, int idEntrenamiento) {
		new CatalogoEjerciciosEditarRutina(usuario, idRutina, rutina, idEntrenamiento);
		dispose();
	}
	
	protected void eliminarEjercicio(int idEjercicioEnEntrenamiento) {
		try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha podido cargar el driver de la BD");
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	        String sql = "DELETE FROM EjercicioEnEntrenamiento WHERE ID_EjercicioEnEntrenamiento = ?";
	        try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	            queryStmt.setInt(1, idEjercicioEnEntrenamiento);
	            int filasAfectadas = queryStmt.executeUpdate();
	            if (filasAfectadas > 0) {
	                System.out.println("Ejercicio eliminado correctamente.");
	            } else {
	                System.out.println("No se encontró el ejercicio con ese ID.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	protected void añadirSerie(int idEjercicioEnEntrenamiento, String usuario) {

	    float peso = (float) 1.0;
	    int repeticiones = 10;  

	    try {

	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha podido cargar el driver de la BD");
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {

	        String sql = "INSERT INTO Serie (ID_EjercicioEnEntrenamiento, OrdenEnEjercicio, Peso, Repeticiones, ID_RPE) VALUES (?, ?, ?, ?, ?)";
	        
	        try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	            queryStmt.setInt(1, idEjercicioEnEntrenamiento);
	            queryStmt.setInt(2, 1);
	            queryStmt.setFloat(3, peso);
	            queryStmt.setInt(4, repeticiones);
	            queryStmt.setInt(5, 1);

	            // Ejecutar la inserción
	            int filasAfectadas = queryStmt.executeUpdate();
	            System.out.println(filasAfectadas);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    dispose();
	    new PerfilUsuario(usuario);
	}

	
	protected void cambiarOrdenSerie(List<Serie> arrySeries, Serie serie, int ordenNuevo, String usuario, int idRutina, Rutina rutina) {
	    // Crear un mapa para almacenar las series con su orden actual
	    HashMap<Integer, Serie> mapa = new HashMap<>();
	    int contador = 1;

	    // Rellenar el mapa con las series existentes
	    for (Serie serie2 : arrySeries) {
	        mapa.put(contador, serie2);
	        contador++;
	    }

	    // Determinar el orden actual de la serie a modificar
	    int ordenActual = -1;
	    for (Map.Entry<Integer, Serie> entry : mapa.entrySet()) {
	        if (entry.getValue().equals(serie)) {
	            ordenActual = entry.getKey();
	            break;
	        }
	    }

	    // Eliminar la serie del orden actual
	    mapa.remove(ordenActual);

	    // Crear el mapa actualizado, moviendo las series
	    HashMap<Integer, Serie> mapaActualizado = new HashMap<>();
	    int contadorNuevo = 1;

	    // Insertar las series antes de la nueva posición
	    for (int i = 1; i < ordenNuevo; i++) {
	        Serie serieExistente = mapa.get(contadorNuevo);
	        if (serieExistente != null) {
	            mapaActualizado.put(i, serieExistente);
	            contadorNuevo++;
	        }
	    }

	    // Insertar la serie en el nuevo orden
	    mapaActualizado.put(ordenNuevo, serie);

	    // Insertar las series después de la nueva posición
	    for (int i = ordenNuevo + 1; i <= arrySeries.size(); i++) {
	        Serie serieExistente = mapa.get(contadorNuevo);
	        if (serieExistente != null) {
	            mapaActualizado.put(i, serieExistente);
	            contadorNuevo++;
	        }
	    }

	    // Actualizar la base de datos con el nuevo orden
	    for (Map.Entry<Integer, Serie> entry : mapaActualizado.entrySet()) {
	        Integer nuevoOrden = entry.getKey(); // El nuevo orden
	        Serie serieActual = entry.getValue(); // La serie correspondiente

	        try {
	            // Cargar el driver JDBC de SQLite
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.out.println("No se ha podido cargar el driver de la BD");
	            e.printStackTrace();
	        }

	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	            // Crear la sentencia SQL con parámetros
	            String sql = "UPDATE Serie SET OrdenEnEjercicio = ? WHERE ID_Serie = ?";
	            try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	                // Establecer los parámetros de la consulta
	                queryStmt.setInt(1, nuevoOrden); // Asignar el nuevo orden
	                queryStmt.setInt(2, serieActual.getId()); // Usar el ID de la serie

	                // Ejecutar la actualización
	                queryStmt.executeUpdate();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    dispose();
	    new PerfilUsuario(usuario);
	}

	public void actualizarPesoSerie(float nuevoPeso, Serie serie, String usuario) {
	    // Obtener el ID de la serie para realizar la actualización
	    int idSerie = serie.getId();

	    // Realizar la conexión y actualización en la base de datos
	    try {
	        // Cargar el driver JDBC de SQLite
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha podido cargar el driver de la BD");
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	        // Crear la sentencia SQL con parámetros para actualizar el peso
	        String sql = "UPDATE Serie SET Peso = ? WHERE ID_Serie = ?";
	        try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	            // Establecer los parámetros de la consulta
	            queryStmt.setFloat(1, nuevoPeso); // Asignar el nuevo peso
	            queryStmt.setInt(2, idSerie); // Usar el ID de la serie para identificar la fila

	            // Ejecutar la actualización
	            int filasAfectadas = queryStmt.executeUpdate();
	            if (filasAfectadas > 0) {
	                System.out.println("Peso actualizado correctamente");
	            } else {
	                System.out.println("No se encontró la serie con el ID: " + idSerie);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    dispose();
	    new PerfilUsuario(usuario);
	}

	public void actualizarRepeticionesSerie(int nuevasRepeticiones, Serie serie, String usuario) {
	    // Obtener el ID de la serie para realizar la actualización
	    int idSerie = serie.getId();

	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha podido cargar el driver de la BD");
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	        String sql = "UPDATE Serie SET Repeticiones = ? WHERE ID_Serie = ?";
	        try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	            queryStmt.setInt(1, nuevasRepeticiones);
	            queryStmt.setInt(2, idSerie);

	            // Ejecutar la actualización
	            int filasAfectadas = queryStmt.executeUpdate();
	            if (filasAfectadas > 0) {
	                System.out.println("Repeticiones actualizadas correctamente.");
	            } else {
	                System.out.println("No se encontró la serie con el ID: " + idSerie);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    dispose();
	    new PerfilUsuario(usuario);
	}

	protected void actualizarEsfuerzo(Esfuerzo esfuerzo, Serie serie, String usuario) {
		// Obtener el ID de la serie para realizar la actualización
	    int idSerie = serie.getId();

	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha podido cargar el driver de la BD");
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	        String sql = "UPDATE Serie SET ID_RPE = ? WHERE ID_Serie = ?";
	        try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	            queryStmt.setInt(1, esfuerzo.ordinal()+1);
	            queryStmt.setInt(2, idSerie);

	            // Ejecutar la actualización
	            int filasAfectadas = queryStmt.executeUpdate();
	            if (filasAfectadas > 0) {
	                System.out.println("Repeticiones actualizadas correctamente.");
	            } else {
	                System.out.println("No se encontró la serie con el ID: " + idSerie);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    dispose();
	    new PerfilUsuario(usuario);
	}
	
	public void eliminarSerie(Serie serie, String usuario) {
	    int idSerie = serie.getId();

	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha podido cargar el driver de la BD");
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	        String sql = "DELETE FROM Serie WHERE ID_Serie = ?";
	        try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	            queryStmt.setInt(1, idSerie); 

	            int filasAfectadas = queryStmt.executeUpdate();
	            if (filasAfectadas > 0) {
	                System.out.println("Serie eliminada correctamente.");
	            } else {
	                System.out.println("No se encontró la serie con el ID: " + idSerie);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    dispose();
	    new PerfilUsuario(usuario);
	}

	
	protected String nombreEjercicio(int id) {
		String nombre = "";
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "SELECT Nombre FROM Ejercicio WHERE ID_Ejercicio = ?";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			queryStmt.setInt(1, id);

			ResultSet resultado =  queryStmt.executeQuery();
			nombre = resultado.getString("Nombre");
			
			
			queryStmt.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombre;
		
	}

	private void cargarEntrenamientos(Rutina rutina, ArrayList<Integer> entrenamientosSemana) {
		ArrayList<Entrenamiento> entrenamientos = rutina.getEntrenamientos();
		
		for (int i = 0; i < 7; i++) {
			entrenamientosSemana.add(0);
		}
		
		for (Entrenamiento entrenamiento : entrenamientos) {
			
			if(entrenamiento.getDía().equals(DayOfWeek.MONDAY)) {
				entrenamientosSemana.set(0, 1);
			}
			if(entrenamiento.getDía().equals(DayOfWeek.TUESDAY)) {
				entrenamientosSemana.set(1, 1);
			}
			if(entrenamiento.getDía().equals(DayOfWeek.WEDNESDAY)) {
				entrenamientosSemana.set(2, 1);
			}
			if(entrenamiento.getDía().equals(DayOfWeek.THURSDAY)) {
				entrenamientosSemana.set(3, 1);
			}
			if(entrenamiento.getDía().equals(DayOfWeek.FRIDAY)) {
				entrenamientosSemana.set(4, 1);
			}
			if(entrenamiento.getDía().equals(DayOfWeek.SATURDAY)) {
				entrenamientosSemana.set(5, 1);
			}
			if(entrenamiento.getDía().equals(DayOfWeek.SUNDAY)) {
				entrenamientosSemana.set(6, 1);
			}
			
		}
		
	}

	protected void actualizarNombreDesc(String nombre, String desc, int id) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "UPDATE Rutina SET Nombre = ?, Descripción = ? WHERE ID_Rutina = ?";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			queryStmt.setString(1, nombre);
			queryStmt.setString(2, desc);
			queryStmt.setInt(3, id);

			queryStmt.executeUpdate();
			
			queryStmt.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}