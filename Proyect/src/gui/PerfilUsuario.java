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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractCellEditor;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import domain.Rutina;
import domain.Usuario;

public class PerfilUsuario extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public class LogrosUsuarioLoader {
	    public static Map<String, List<String>> cargarLogrosPorUsuario() {
	        // Mapa que almacena los logros seleccionados por cada usuario
	        Map<String, List<String>> logrosPorUsuario = new HashMap<>();
	        
	        // Conexión a la base de datos
	        String url = "jdbc:sqlite:Sources/bd/baseDeDatos.db";
	        String query = """
	            SELECT U.Usuario AS Usuario, L.Nombre AS Logro
	            FROM ConsigueLogro CL
	            JOIN Usuario U ON CL.Usuario = U.Usuario
	            JOIN Logro L ON CL.Logro = L.ID_Logro
	            """;
	        try (Connection conn = DriverManager.getConnection(url);
	             PreparedStatement stmt = conn.prepareStatement(query);
	             ResultSet rs = stmt.executeQuery()) {
	            
	            // Recorrer los resultados
	            while (rs.next()) {
	                String usuario = rs.getString("Usuario");
	                String logro = rs.getString("Logro");
	                
	                // Si el usuario no está en el mapa, inicializamos su lista de logros
	                logrosPorUsuario.putIfAbsent(usuario, new ArrayList<>());
	                
	                // Añadimos el logro a la lista del usuario
	                logrosPorUsuario.get(usuario).add(logro);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return logrosPorUsuario;
	    }
	}
	
	public PerfilUsuario(String usuario) {
		
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
		pIzquierda.setBackground(new Color(176,224,230));
		pIzquierda.setLayout(new GridLayout(3,1));
		
		// Margenes para el panelIzquierda
		pIzquierda.setBorder(new EmptyBorder(60, 50, 50, 50)); 		
		
		//El panel izquierda se divide en 3 paneles
		JPanel panelIz1 = new JPanel();
		panelIz1.setLayout(new FlowLayout(FlowLayout.CENTER ,50, 30));
		panelIz1.setBackground(new Color(176,224,230));
		
		JPanel panelIz2 = new JPanel();
		panelIz2.setBackground(new Color(176,224,230));
		
		JPanel panelIz3 = new JPanel();
		panelIz3.setLayout(new GridLayout(2, 3));
		
		// Botón para volver a la ventana principal
		
		JButton botonPrincipal = new JButton("VOLVER");
		botonPrincipal.setPreferredSize(new Dimension(100, 40));
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
		
		// Cambiamos el tamaño de la fuente
		nombreApellidos.setFont(new Font("Arial",Font.PLAIN ,18));
		panelIz1.add(nombreApellidos);
		
		//Boton para editar datos
		JButton editarDatos = new JButton("EDITAR");
		editarDatos.setPreferredSize(new Dimension(100, 50));
		panelIz1.add(editarDatos); 
		
		//Boton para editar foto de perfil
		JButton botonCambiarFoto = new JButton("Editar foto");
		botonCambiarFoto.setPreferredSize(new Dimension(100, 50));
		
		//Para que no de errores el action listener
		Usuario uFinal = uElegido;
		
		//Action listener que cuando editas el perfil te lleva a la ventana editarPerfil
		editarDatos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				new EditarPerfil(uFinal);
				dispose();
				
				
			}
		});
		

		//Creamos un jTextArea que será la descripción del usuario
		JTextArea desc = new JTextArea(uElegido.getDescripcion());
		desc.setBackground(new Color(195,248,255));
		
		// Detalles del JTextArea
		desc.setWrapStyleWord(true);
		desc.setLineWrap(true);
		desc.setEditable(false);
		desc.setFont(new Font("Arial", Font.PLAIN, 18));
		desc.setPreferredSize(new Dimension(400, 200));
		
		// Añadimos la descripcion a panelSubOeste1
		panelIz2.add(desc);
			
		//FotoVitrina1
		ImageIcon fotoVitrina1 = new ImageIcon("Sources/imagenes/banca5SIN.png");
		Image imagenVitrina1 = fotoVitrina1.getImage();
	    Image nuevaImagen1 = imagenVitrina1.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	    fotoVitrina1 = new ImageIcon(nuevaImagen1);
		JLabel prueba1 = new JLabel(fotoVitrina1);
		panelIz3.add(prueba1);
		
		//FotoVitrina2
		ImageIcon fotoVitrina2 = new ImageIcon("Sources/imagenes/banca5SIN.png");
		Image imagenVitrina2 = fotoVitrina2.getImage();
	    Image nuevaImagen2 = imagenVitrina2.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
	    fotoVitrina2 = new ImageIcon(nuevaImagen2);
		JLabel prueba2 = new JLabel(fotoVitrina2);
		panelIz3.add(prueba2);
		
		//FotoVitrina3
		ImageIcon fotoVitrina3 = new ImageIcon("Sources/imagenes/banca5SIN.png");
		Image imagenVitrina3 = fotoVitrina3.getImage();
	    Image nuevaImagen3 = imagenVitrina3.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
		fotoVitrina3 = new ImageIcon(nuevaImagen3);
		JLabel prueba3 = new JLabel(fotoVitrina3);
		panelIz3.add(prueba3);
		
		
		JLabel prueba4 = new JLabel("100KG press banca", JLabel.CENTER);
		panelIz3.add(prueba4);
		JLabel prueba5 = new JLabel("100KG press banca", JLabel.CENTER);
		panelIz3.add(prueba5);
		JLabel prueba6 = new JLabel("100KG press banca", JLabel.CENTER);
		panelIz3.add(prueba6);		
		
		// Listener para volver a la ventana principal cuando se presiona el
		// botón volver
		String user3 = uElegido.getUsuario();
		botonPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                PrincipalWindow principal = new PrincipalWindow(user3);
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
		pDerecha.setBackground(new Color(176,224,230));
		add(pDerecha);
		pDerecha.setLayout(new BorderLayout());
		
		//Label rutinas
		JLabel rutinas = new JLabel("RUTINAS");
		rutinas.setFont(new Font("Arial", Font.PLAIN, 25));
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
		table.getColumnModel().getColumn(3).setCellEditor(new EditorBoton(usuario, rutinasUsuario));
		
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
	    
	    //Cambiar esta lista por rutinasUsuario cuando se terminen de introducir todo a la BD
	    private Object[][] data;
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

	        //Icono editar
	        ImageIcon iconoLapiz = new ImageIcon("Sources/imagenes/lapiz.png");
	        Image img = iconoLapiz.getImage();
	        Image imgEscalada = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoLapiz = new ImageIcon(imgEscalada);
	        
	        //Icono expandir
	        ImageIcon iconoExpandir = new ImageIcon("Sources/imagenes/expandir.png");
	        Image img2 = iconoExpandir.getImage();
	        Image img2Escalada = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoExpandir = new ImageIcon(img2Escalada);
	        
	        
	        //Icono eliminar
	        ImageIcon iconoEliminar = new ImageIcon("Sources/imagenes/eliminar.png");
	        Image img3 = iconoEliminar.getImage();
	        Image img3Escalada = img3.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoEliminar = new ImageIcon(img3Escalada);
	        
	        //Icono estadisticas
	        ImageIcon iconoEstadisticas = new ImageIcon("Sources/imagenes/estadisticas.png");
	        Image img4 = iconoEstadisticas.getImage();
	        Image img4Escalada = img4.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoEstadisticas = new ImageIcon(img4Escalada);
	        
	        // Añade los botones necesarios
	        this.add(new JButton(iconoLapiz));
	        this.add(new JButton(iconoExpandir));
	        this.add(new JButton(iconoEliminar));
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
		
	    public EditorBoton(String usuario, List<Rutina> rutinasUsuario) {
	        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	      //Icono editar
	        ImageIcon iconoLapiz = new ImageIcon("Sources/imagenes/lapiz.png");
	        Image img = iconoLapiz.getImage();
	        Image imgEscalada = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoLapiz = new ImageIcon(imgEscalada);
	        
	        //Icono expandir
	        ImageIcon iconoExpandir = new ImageIcon("Sources/imagenes/expandir.png");
	        Image img2 = iconoExpandir.getImage();
	        Image img2Escalada = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoExpandir = new ImageIcon(img2Escalada);
	        
	        
	        //Icono eliminar
	        ImageIcon iconoEliminar = new ImageIcon("Sources/imagenes/eliminar.png");
	        Image img3 = iconoEliminar.getImage();
	        Image img3Escalada = img3.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoEliminar = new ImageIcon(img3Escalada);
	        
	        //Icono estadisticas
	        ImageIcon iconoEstadisticas = new ImageIcon("Sources/imagenes/estadisticas.png");
	        Image img4 = iconoEstadisticas.getImage();
	        Image img4Escalada = img4.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	        iconoEstadisticas = new ImageIcon(img4Escalada);
	        
	        JButton editButton = new JButton(iconoLapiz);
	        JButton expandButton = new JButton(iconoExpandir);
	        JButton deleteButton = new JButton(iconoEliminar);
	        JButton statsButton = new JButton(iconoEstadisticas);
	        
	        //Action listener para editar
	        editButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					new PrincipalWindow(usuario);
					
				}
			});
	        
	        //Action listener expandir
	        expandButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					new PrincipalWindow(usuario);
					
				}
			});
	        
	        //Action listener eliminar
	        deleteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (editingRow != -1) {
						
						Object id = table.getModel().getValueAt(editingRow, 0);
						eliminarRutina(Integer.parseInt(id.toString()));
					}
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
						
						new EstadisticasRutina(rutinaSeleccionada);
					}
					
					
				}
			});
	        
	        panel.add(editButton);
	        panel.add(expandButton);
	        panel.add(deleteButton);
	        panel.add(statsButton);
	    }

	    protected void eliminarRutina(int id) {
	    	try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				System.out.println("No se ha podido cargar el driver de la BD");
			}
			
			//Conectar a la BD
			try {
				Connection conn = DriverManager.getConnection
						("jdbc:sqlite:Sources/bd/baseDeDatos.db");
				
				
				String sql = "DELETE FROM Rutinas WHERE id ="+id;
				PreparedStatement queryStmt = conn.prepareStatement(sql);
				
				queryStmt.close();
				conn.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			
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