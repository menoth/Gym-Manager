package gui;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import domain.EjercicioEnEntrenamiento;
import domain.Entrenamiento;
import domain.Rutina;
import domain.Serie;
import domain.Usuario;

public class VisitarPerfil extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VisitarPerfil(String usuario, String usuarioQueVisita) {
		setUndecorated(true);
//----------------------------------------------BD-------------------------------------------------------------------		
		List<Usuario> usuarios = new ArrayList<>();
		Usuario uElegido = new Usuario("a","a","a","a","a","a","a");
		
		//Carga del driver de JDBC para SQLITE
				try {
					Class.forName("org.sqlite.JDBC");
				} catch (ClassNotFoundException e) {
					System.out.println("No se ha podido cargar el driver de la BD");
				}
				
				//Conectar a la BD
				try {
					Connection conn = DriverManager.getConnection
							("jdbc:sqlite:Sources/bd/baseDeDatos.db");
					
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery
							("SELECT * FROM Usuario");
					
					while (rs.next()) {
						
						
						String usuarioDB = rs.getString("Usuario");
						if(usuarioDB.equals(usuario)) {
							String nombre = rs.getString("Nombre");
							String apellidos = rs.getString("Apellidos");
							String correo = rs.getString("Correo");
							String contraseña = rs.getString("Contraseña");
							String descripcion = rs.getString("Descripcion");
							String fotoPerfil = rs.getString("FotoDePerfil");
							uElegido = new Usuario(nombre, apellidos, usuarioDB, correo, contraseña, descripcion, fotoPerfil);
							usuarios.add(uElegido);
						}
					}
				
					stmt.close();
					conn.close(); 
				} catch (SQLException e) {
					e.printStackTrace();
				}	

				
//-----------------------------JFRAME------------------------------------------------------------
		
		// Detalles ventana
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Perfil");
		
		// Primer layout que divide en dos la ventana del Perfil
		setLayout(new GridLayout(1, 2));
		
		//El panel izquierda tendrá nombre, apellidos, vitrina... y 
		//el pane derecha tendrá las rutinas con sus estad´sticas
		JPanel pIzquierda = new JPanel();
		pIzquierda.setBackground(new Color(70, 130, 180));
		pIzquierda.setLayout(new GridLayout(3,1));
		
		// Margenes para el panelIzquierda
		pIzquierda.setBorder(new EmptyBorder(60, 50, 50, 50)); 		
		
		//El panel izquierda se divide en 3 paneles
		JPanel panelIz1 = new JPanel();
		panelIz1.setLayout(new FlowLayout(FlowLayout.CENTER ,50, 30));
		panelIz1.setBackground(new Color(70, 130, 180));
		
		JPanel panelIz2 = new JPanel();
		panelIz2.setBackground(new Color(70, 130, 180));
		
		JPanel panelIz3 = new JPanel();
		String url = "jdbc:sqlite:Sources/bd/baseDeDatos.db";
	    String consultaSQL = "SELECT L.Nombre FROM Logro L " +
	                         "JOIN ConsigueLogro CL ON L.ID_Logro = CL.Logro " +
	                         "WHERE CL.Usuario = ?";

	    try (Connection conn = DriverManager.getConnection(url);
	         PreparedStatement stmt = conn.prepareStatement(consultaSQL)) {

	        stmt.setString(1, usuario);
	        var rs = stmt.executeQuery();

	        // Limpiar el contenido previo de panelIz3
	        panelIz3.removeAll();

	        // Crear un panel con layout horizontal para mostrar los logros
	        JPanel logrosPanel = new JPanel();
	        logrosPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

	        // Agregar logros al panel
	        while (rs.next()) {
	            String nombreLogro = rs.getString("Nombre");

	            // Asumimos que la imagen se llama igual que el logro pero con extensión .png
	            String rutaImagen = "Sources/imagenes/" + nombreLogro + ".png";

	            // Verificar si la imagen existe antes de crear el ImageIcon
	            ImageIcon icon = null;
	            File archivoImagen = new File(rutaImagen);
	            if (archivoImagen.exists()) {
	                Image img = new ImageIcon(rutaImagen).getImage().getScaledInstance(200, 270, Image.SCALE_SMOOTH);
	                icon = new ImageIcon(img);
	            }

	            JLabel labelImagen = new JLabel(icon);
	            labelImagen.setToolTipText(nombreLogro); // Mostrar el nombre como tooltip
	            labelImagen.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
	            
	            
	            // Crear un panel individual para cada logro (imagen + texto)
	            JPanel logroPanel = new JPanel();
	            logroPanel.setLayout(new BoxLayout(logroPanel, BoxLayout.Y_AXIS));
	            logroPanel.add(labelImagen);
	            logroPanel.add(labelImagen);

	            // Agregar el panel del logro al panel principal
	            logrosPanel.add(logroPanel);
	        }

	        // Agregar el panel de logros al panelIz3
	        panelIz3.add(logrosPanel, BorderLayout.CENTER);
	        panelIz3.revalidate();
	        panelIz3.repaint();

	    } catch (SQLException ex) {
	        System.err.println("Error al cargar los logros: " + ex.getMessage());
	    }
		
		// Botón para volver a la ventana principal
		
		JButton botonPrincipal = new JButton("VOLVER");
		botonPrincipal.setPreferredSize(new Dimension(100, 40));
		
		botonPrincipal.setBackground(new Color(255, 255, 255));
		botonPrincipal.setForeground(new Color(70, 130, 180));
		botonPrincipal.setFont(new Font("Arial", Font.BOLD, 16));
		
		panelIz1.add(botonPrincipal);
		
		// Label para la foto de perfil
		ImageIcon fotoPerfil = new ImageIcon("Sources/imagenes/"+uElegido.getFotoPerfil());
		Image image = fotoPerfil.getImage(); // Obtener el objeto Image
	    Image newImg = image.getScaledInstance(160, 160, Image.SCALE_SMOOTH); // Ajustar tamaño
	    fotoPerfil = new ImageIcon(newImg); // Crear un nuevo ImageIcon con la imagen redimensionada

	    // Crear el JLabel y agregar la imagen
	    JLabel label = new JLabel(fotoPerfil);
		
		panelIz1.add(label);
		
		// Label del nombre y apellidos hecho con HTML para poder hacerlo en dos lineas
		String texto = "<html><b>" + uElegido.getNombre() + "</b><br>"
                + uElegido.getApellidos() + "</b><br>"
                + "@<b>" + uElegido.getUsuario() + "</b></html>";
			 
		JLabel nombreApellidos = new JLabel(texto);
		nombreApellidos.setForeground(new Color(255,255,255));
		
		// Cambiamos el tamaño de la fuente
		nombreApellidos.setFont(new Font("Arial", Font.BOLD, 16));
		nombreApellidos.setBackground(new Color(255, 255, 255));
		panelIz1.add(nombreApellidos);
		
		//Creamos un jTextArea que será la descripción del usuario
		JTextArea desc = new JTextArea(uElegido.getDescripcion());
		desc.setBackground(new Color(240, 240, 240));
		
		// Detalles del JTextArea
		desc.setWrapStyleWord(true);
		desc.setLineWrap(true);
		desc.setEditable(false);
		desc.setFont(new Font("Arial", Font.BOLD, 18));
		desc.setPreferredSize(new Dimension(400, 200));
		
		// Añadimos la descripcion a panelSubOeste1
		panelIz2.add(desc);
			
		// Listener para volver a la ventana principal cuando se presiona el
		// botón volver
		botonPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	PrincipalWindow principal = new PrincipalWindow(usuarioQueVisita);
                principal.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
		
		setBackground(Color.black);
		
		// Añadimos	los paneles
		add(pIzquierda, BorderLayout.WEST);
		pIzquierda.add(panelIz1);
		pIzquierda.add(panelIz2);
		pIzquierda.add(panelIz3);
		
//------------------------LADO DERECHO------------------------------
		JPanel pDerecha = new JPanel();
		pDerecha.setBackground(new Color(70, 130, 180));
		add(pDerecha);
		pDerecha.setLayout(new BorderLayout());
		
		//Label rutinas
		JLabel rutinas = new JLabel("RUTINAS");
		rutinas.setForeground(new Color(255, 255, 255));
		rutinas.setFont(new Font("Arial", Font.BOLD, 25));
		
		pDerecha.add(rutinas, BorderLayout.NORTH);
		pDerecha.setBorder(new EmptyBorder(40,40,0,0));
		
		//Lista para importar todas las rutinas de la bd
		ArrayList<Rutina> listaRutinas = new ArrayList<>();
		ConectarBaseDeDatos.ConectarBaseDeDatosRutina(listaRutinas); 
		
		//Lista en la que se meten las rutinas del usuario
		ArrayList<Rutina> rutinasUsuario = new ArrayList<>();
		for (Rutina rutina : listaRutinas) {
			if(rutina.getUsuario().equals(usuario)) {
				rutinasUsuario.add(rutina);	
			}
		}
		
		RutinaModel modelo = new RutinaModel();
		modelo.cargarDatosDesdeBD(usuario);
		JTable table = new JTable(modelo);
		table.getColumnModel().getColumn(3).setCellRenderer(new RendererBoton());
		table.getColumnModel().getColumn(3).setCellEditor(new EditorBoton(usuario, rutinasUsuario, usuarioQueVisita));
		
		//Ajustar el tamaño de Nombre
		table.getColumnModel().getColumn(1).setWidth(170);
		table.getColumnModel().getColumn(1).setMinWidth(170);
		table.getColumnModel().getColumn(1).setMaxWidth(170); 
		
		//Ajustar el tamaño de descripcion
		table.getColumnModel().getColumn(2).setWidth(340);
		table.getColumnModel().getColumn(2).setMinWidth(340);
		table.getColumnModel().getColumn(2).setMaxWidth(340);
		
		// Oculta la columna "id" (primera columna en el modelo original)
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		table.setRowHeight(120);

		JScrollPane scrollPane = new JScrollPane(table);
		pDerecha.add(scrollPane, BorderLayout.CENTER);	
		
		// Detalles ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
	}	
	
	class RutinaModel extends AbstractTableModel {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -857471165146589501L;

		private String[] nombreDatos = {"id", "Nombre", "Descripción", "Acciones"};

	    private Object[][] data = new Object[0][0];
	    
	    public void cargarDatosDesdeBD(String user) {
	    	try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("No se ha podido cargar el driver de la BD");
			}
			try {
				Connection conn = DriverManager.getConnection
					("jdbc:sqlite:Sources/bd/baseDeDatos.db");
		
				Statement stmt = conn.createStatement();
				String sql = "SELECT * FROM Rutina WHERE Usuario LIKE ?";
				PreparedStatement queryStmt = conn.prepareStatement(sql);
				queryStmt.setString(1, user);
				ResultSet rs = queryStmt.executeQuery();
				List<Object[]> listaDatos = new ArrayList<>();
				while (rs.next()) {
					int ID_Rutina = rs.getInt("ID_Rutina");
					String nombre = rs.getString("Nombre");
					String descripcion = rs.getString("Descripción");
	                listaDatos.add(new Object[] {ID_Rutina, nombre, descripcion, "Botones"});
	                data = listaDatos.toArray(new Object[0][]);
	                //Notifica a la tabla que los datos han cambiado
	                fireTableDataChanged();
	            }
				stmt.close();
				conn.close();	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    public int getRowCount() {
	        return data.length;
	    }

	    
	    @Override
	    public int getColumnCount() {
	    	//Para mantener la columna id oculta
	        return nombreDatos.length;
	    }

	    @Override
	    public String getColumnName(int column) {
	        return nombreDatos[column];
	    }

	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        return data[rowIndex][columnIndex];
	    }
	    
	    //Necesario poder editar la columna 2 para poder implementar los botones
	    @Override
	    public boolean isCellEditable(int rowIndex, int columnIndex) {
	        return columnIndex == 3; // Sólo la columna de botones es editable
	    }

	    @Override
	    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	        data[rowIndex][columnIndex] = aValue;
	    }
	}

	// Renderizador de celdas con botones
	class RendererBoton extends JPanel implements TableCellRenderer {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RendererBoton() {
	        setLayout(new FlowLayout(FlowLayout.LEFT));
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        // Limpia el panel para cada renderizado
	        this.removeAll();

	        //Icono copiar
	        ImageIcon iconoCopiar = new ImageIcon("Sources/imagenes/copiar.png");
	        Image img = iconoCopiar.getImage();
	        Image imgEscalada = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoCopiar = new ImageIcon(imgEscalada);
	        
	        //Icono expandir
	        ImageIcon iconoExpandir = new ImageIcon("Sources/imagenes/expandir.png");
	        Image img2 = iconoExpandir.getImage();
	        Image img2Escalada = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoExpandir = new ImageIcon(img2Escalada);
	        
	        //Icono estadisticas
	        ImageIcon iconoEstadisticas = new ImageIcon("Sources/imagenes/estadisticas.png");
	        Image img4 = iconoEstadisticas.getImage();
	        Image img4Escalada = img4.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoEstadisticas = new ImageIcon(img4Escalada);
	        
	        // Añade los botones necesarios
	        this.add(new JButton(iconoCopiar));
	        this.add(new JButton(iconoExpandir));
	        this.add(new JButton(iconoEstadisticas));

	        return this;
	    }
	}

	// Editor de celdas con botones
	class EditorBoton extends AbstractCellEditor implements TableCellEditor {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -3543889255680517811L;
		
		private JPanel panel;
		private JTable table;
		private int editingRow = -1;
		
	    public EditorBoton(String usuario, List<Rutina> rutinasUsuario, String usuarioQueVisita) {
	        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	      //Icono copiar
	        ImageIcon iconoCopiar = new ImageIcon("Sources/imagenes/copiar.png");
	        Image img = iconoCopiar.getImage();
	        Image imgEscalada = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoCopiar = new ImageIcon(imgEscalada);
	        
	        //Icono expandir
	        ImageIcon iconoExpandir = new ImageIcon("Sources/imagenes/expandir.png");
	        Image img2 = iconoExpandir.getImage();
	        Image img2Escalada = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoExpandir = new ImageIcon(img2Escalada);
	        
	        //Icono estadisticas
	        ImageIcon iconoEstadisticas = new ImageIcon("Sources/imagenes/estadisticas.png");
	        Image img4 = iconoEstadisticas.getImage();
	        Image img4Escalada = img4.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoEstadisticas = new ImageIcon(img4Escalada);
	        
	        JButton copyButton = new JButton(iconoCopiar);
	        JButton expandButton = new JButton(iconoExpandir);
	        JButton statsButton = new JButton(iconoEstadisticas);
			
	        
	        //Action listener para copiar
	        copyButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Rutina rutinaSeleccionada = null;
					Object id = table.getModel().getValueAt(editingRow, 0);
					
					//Con ese id obtenemos la rutina
					for (Rutina rutina2 : rutinasUsuario) {
						if (rutina2.getId() == Integer.parseInt(id.toString())) {
							rutinaSeleccionada = rutina2;
						}
					}
					
					copiarRutinaUsuario(rutinaSeleccionada, usuarioQueVisita);
					
					JOptionPane.showMessageDialog(VisitarPerfil.this, "La rutina se ha copiado a tu perfil");
					
				}
			});
	        
	      //Action listener expandir
	        expandButton.addActionListener(new ActionListener() {
	        	
	        	
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	        		Rutina rutinaSeleccionada = null;
					Object id = table.getModel().getValueAt(editingRow, 0);
					
					//Con ese id obtenemos la rutina
					for (Rutina rutina2 : rutinasUsuario) {
						if (rutina2.getId() == Integer.parseInt(id.toString())) {
							rutinaSeleccionada = rutina2;
						}
					}
	                new InterfazRutinaExpandida(rutinaSeleccionada, usuario);
	        	}
	        });
	        
	        //Action listener estadísticas
	        statsButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (editingRow != -1) {
						Object id = table.getModel().getValueAt(editingRow, 0);
						Rutina rutinaSeleccionada = null;
						
						//Con ese id obtenemos la rutina
						for (Rutina rutina2 : rutinasUsuario) {
							if (rutina2.getId() == Integer.parseInt(id.toString())) {
								rutinaSeleccionada = rutina2;
							}
						}
						int SerieTotales = 0;
						for(Entrenamiento entrenamiento : rutinaSeleccionada.getEntrenamientos()) {
							for(EjercicioEnEntrenamiento ejercicioEnEntrenamiento : entrenamiento.getEjercicios()) {
								for(@SuppressWarnings("unused") Serie serie : ejercicioEnEntrenamiento.getSeries()) {
									SerieTotales++;
								}
							}
						}
						if(SerieTotales <=0) {
							JOptionPane.showMessageDialog(VisitarPerfil.this, "Creacion de estadisticas erronea debido a rutina sin series");
						}else {
							EstadisticasRutina estadisticas = new EstadisticasRutina(rutinaSeleccionada);
							estadisticas.setExtendedState(JFrame.MAXIMIZED_BOTH);
						}
					}
					
					
				}
			});
	        
	        panel.add(copyButton);
	        panel.add(expandButton);
	        panel.add(statsButton);
	    }
	    
	    protected void copiarRutinaUsuario(Rutina rutina, String usuarioQueVisita) {
	        int id_Rutina = 0;

	        try {
	            // Cargar el driver
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("No se ha podido cargar el driver de la base de datos.");
	            e.printStackTrace();
	            return;
	        }

	        // Primera conexión: Copiar la rutina
	        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	            String sql = "INSERT INTO Rutina (Nombre, Descripción, Usuario) VALUES (?, ?, ?)";
	            try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	                queryStmt.setString(1, "Copia rutina de " + rutina.getUsuario());
	                queryStmt.setString(2, rutina.getDescripcionRutina());
	                queryStmt.setString(3, usuarioQueVisita);

	                int filasAfectadas = queryStmt.executeUpdate();
	                System.out.println("Rutina copiada: " + filasAfectadas + " filas afectadas.");

	                try (ResultSet generatedKeys = queryStmt.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        id_Rutina = generatedKeys.getInt(1);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Error al copiar la rutina.");
	            e.printStackTrace();
	            return;
	        }

	        // Segunda conexión: Introducir los entrenamientos
	        if (!rutina.getEntrenamientos().isEmpty()) {
	            for (Entrenamiento entrenamiento : rutina.getEntrenamientos()) {
	                String dia = entrenamiento.getDía().name();
	                String nombre = entrenamiento.getNombre();
	                String desc = entrenamiento.getDescripcionEntrenamiento();
	                int idEntrenamiento = 0;

	                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	                    String sql = "INSERT INTO Entrenamiento (Nombre, Descripción, ID_Rutina, Dia) VALUES (?, ?, ?, ?)";
	                    try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	                        queryStmt.setString(1, nombre);
	                        queryStmt.setString(2, desc);
	                        queryStmt.setInt(3, id_Rutina);
	                        queryStmt.setString(4, dia);

	                        int filasAfectadas = queryStmt.executeUpdate();
	                        System.out.println("Entrenamiento copiado: " + filasAfectadas + " filas afectadas.");

	                        try (ResultSet generatedKeys = queryStmt.getGeneratedKeys()) {
	                            if (generatedKeys.next()) {
	                                idEntrenamiento = generatedKeys.getInt(1);
	                            }
	                        }
	                    }
	                } catch (SQLException e) {
	                    System.err.println("Error al copiar el entrenamiento: " + nombre);
	                    e.printStackTrace();
	                    continue;
	                }

	                // Tercera conexión: Introducir los ejercicios
	                if (!entrenamiento.getEjercicios().isEmpty()) {
	                    for (EjercicioEnEntrenamiento ejercicio : entrenamiento.getEjercicios()) {
	                        int idEjercicioEnEntrenamiento = 0;

	                        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	                            String sql = "INSERT INTO EjercicioEnEntrenamiento (ID_Entrenamiento, ID_Ejercicio, OrdenEnEntrenamiento) VALUES (?, ?, ?)";
	                            try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	                                queryStmt.setInt(1, idEntrenamiento);
	                                queryStmt.setInt(2, ejercicio.getID_Ejercicio());
	                                queryStmt.setInt(3, ejercicio.getOrdeEnEntrenamiento());

	                                int filasAfectadas = queryStmt.executeUpdate();
	                                System.out.println("Ejercicio copiado: " + filasAfectadas + " filas afectadas.");

	                                try (ResultSet generatedKeys = queryStmt.getGeneratedKeys()) {
	                                    if (generatedKeys.next()) {
	                                        idEjercicioEnEntrenamiento = generatedKeys.getInt(1);
	                                    }
	                                }
	                            }
	                        } catch (SQLException e) {
	                            System.err.println("Error al copiar el ejercicio.");
	                            e.printStackTrace();
	                            continue;
	                        }

	                        // Cuarta conexión: Introducir las series
	                        if (!ejercicio.getSeries().isEmpty()) {
	                            for (Serie serie : ejercicio.getSeries()) {
	                                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
	                                    String sql = "INSERT INTO Serie (ID_EjercicioEnEntrenamiento, ID_RPE, Repeticiones, Peso, OrdenEnEjercicio) VALUES (?, ?, ?, ?, ?)";
	                                    try (PreparedStatement queryStmt = conn.prepareStatement(sql)) {
	                                        queryStmt.setInt(1, idEjercicioEnEntrenamiento);
	                                        queryStmt.setInt(2, serie.getEsfuerzo().ordinal()+1);
	                                        queryStmt.setInt(3, serie.getRepeticiones());
	                                        queryStmt.setFloat(4, serie.getPeso());
	                                        queryStmt.setInt(5, serie.getOrdenEnEjercicio());

	                                        int filasAfectadas = queryStmt.executeUpdate();
	                                        System.out.println("Serie copiada"+ filasAfectadas);
	                                    }
	                                } catch (SQLException e) {
	                                    System.err.println("Error al copiar la serie");
	                                    e.printStackTrace();
	                                 }
	                            }
	                          } 
	                    }
	                	}
	              }
	        }
	     }

		@Override
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	        this.table = table;
	        this.editingRow = row;
			return panel;
	    }

	    @Override
	    public Object getCellEditorValue() {
	        return null;
	    }
	}
	
}
