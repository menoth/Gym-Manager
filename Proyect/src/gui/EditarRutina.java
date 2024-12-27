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
import java.util.List;

import javax.sound.sampled.spi.FormatConversionProvider;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import domain.Ejercicio;
import domain.EjercicioEnEntrenamiento;
import domain.Entrenamiento;
import domain.Rutina;
import domain.Serie;

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
			
		//Panel norte donde va el nombre y descripcion de la rutina (editable) y boton de volver y guardar cambios
//----------------------------------------------PANEL NORTE------------------------------------------------		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(Color.red);
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
		
		System.out.println(entrenamientosSemana);
		
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
		
		panelCentral.setBackground(Color.blue);
		panelCentral.setLayout(new GridLayout(1, 3));
	
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.green);
		
		//Panel 1 con los entrenamientos de la semana
		panel1.setLayout(new GridLayout(7, 1));

		
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.orange);
		panel2.setLayout(new FlowLayout());
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.red);
		
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
				
				//Action listener de editar
				editar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
						int numEjercicios = rutina.getEntrenamientos().get(indice2).getEjercicios().size();
						
						
					
						panel2.removeAll();
						
						int indice3 = 0;
						
						for (EjercicioEnEntrenamiento ejercicio : rutina.getEntrenamientos().get(indice2).getEjercicios()) {
							
							JPanel panelEjercicio = new JPanel();
							
							panelEjercicio.setLayout(new BoxLayout(panelEjercicio, BoxLayout.Y_AXIS));
							
							int id = ejercicio.getID_Ejercicio();

							JPanel panelSubEjercicio = new JPanel();
							panelSubEjercicio.setLayout(new GridLayout(1, 3));
							panelSubEjercicio.setBorder(new EmptyBorder(25,10,10,25));
							panelSubEjercicio.setPreferredSize(new Dimension(panel2.getWidth(),100));
							
							panelSubEjercicio.add(new JLabel(nombreEjercicio(id)));
													
							JButton botonEditar = new JButton("Editar");
							panelSubEjercicio.add(botonEditar);
							
							int indice4 = indice3;
							botonEditar.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									
									List<Serie> listaSeries = rutina.getEntrenamientos().get(indice2).getEjercicios().get(indice4).getSeries();
									
									for (Serie serie : listaSeries) {
										JPanel panelSerie = new JPanel();
										//panelSerie.setLayout(new GridLayout()))
									}
									
								}
							});
							
							JButton botonEliminar = new JButton("Eliminar");
							panelSubEjercicio.add(botonEliminar);
							
							panelEjercicio.add(panelSubEjercicio);
							
							JPanel panelAñadirEjercicio = new JPanel();
							panelAñadirEjercicio.setLayout(new FlowLayout());
							panelAñadirEjercicio.setBorder(new EmptyBorder(20,20,20,20));
							JButton añadirEjercicio = new JButton("Añadir");
							añadirEjercicio.setPreferredSize(new Dimension(100, 50));
							
							panelAñadirEjercicio.add(añadirEjercicio);							
							panelEjercicio.add(panelAñadirEjercicio);
							
							panel2.add(panelEjercicio);
							
							indice3 += 1;
							
						}
						panel2.updateUI();
						
						
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
				panel.add(panel12);
				panel.add(new JLabel(""));
				panel1.add(panel);
			}
			
			
			
		}
		
		panelCentral.add(panel1);

		JScrollPane scrollPane = new JScrollPane(panel2);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        panelCentral.add(scrollPane);
		
		panelCentral.add(panel3);
		
		this.add(panelCentral, BorderLayout.CENTER);
		
		// Detalles ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	protected ArrayList<Serie> cargarSerie(int id) {
		ArrayList<Serie> listaSeries = new ArrayList<>();
		
		return listaSeries;
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
			// TODO: handle exception
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
			// TODO: handle exception
		}
		
	}

	
}
	


