package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PrincipalWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField campo_busqueda;
	private JButton boton_buscar;
	private DefaultListModel<String> lista;

	private List<String> datos = new ArrayList<>();

	public PrincipalWindow(String usuario) {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.setLayout(new BorderLayout());

		// ------------------------Panel norte----------------------------------------
		JPanel pNorte = new JPanel();
		pNorte.setLayout(new BorderLayout());
		pNorte.setBackground(new Color(70, 130, 180)); // Azul acero
		pNorte.setPreferredSize(new Dimension(0, 200));
		
		// Crear un JLabel para el mensaje de bienvenida
		JLabel labelBienvenido = new JLabel("Bienvenido " + usuario + "!");
		labelBienvenido.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
		labelBienvenido.setFont(new Font("Serif", Font.BOLD, 66));
		labelBienvenido.setForeground(Color.WHITE);
		iniciarAnimacionColor(labelBienvenido);

		// Botón para ir al perfil
		JPanel panelPerfil = new JPanel();
		panelPerfil.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alineación a la izquierda
		panelPerfil.setPreferredSize(new Dimension(200, 100));
		panelPerfil.setBorder(new EmptyBorder(70, 70, 0, 0));
		panelPerfil.setBackground(new Color(70, 130, 180));

		JButton profileButton = new JButton("PERFIL");
		profileButton.setPreferredSize(new Dimension(120, 60));
		
		profileButton.setBackground(new Color(255, 255, 255)); // Blanco
		profileButton.setForeground(new Color(70, 130, 180)); // Azul acero
		profileButton.setFont(new Font("Serif", Font.BOLD, 16));

		profileButton.addActionListener(e -> {
			setVisible(false);
			PerfilUsuario perfilUsuario = new PerfilUsuario(usuario);
			perfilUsuario.setExtendedState(JFrame.MAXIMIZED_BOTH);
			perfilUsuario.setVisible(true);
		});

		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelMenu.setPreferredSize(new Dimension(200, 100));
		panelMenu.setBorder(new EmptyBorder(70, 0, 0, 70));
		panelMenu.setBackground(new Color(70, 130, 180));

		JButton menuButton = new JButton("EXTRA");
		menuButton.setPreferredSize(new Dimension(120, 60));
		menuButton.setBackground(new Color(255, 255, 255)); // Blanco
		menuButton.setForeground(new Color(70, 130, 180)); // Azul acero
		menuButton.setFont(new Font("Serif", Font.BOLD, 16));

		JPopupMenu menuDesplegable = new JPopupMenu();

		JMenuItem opcion1 = new JMenuItem("ACCEDE COMO ADMINISTRADOR");
		opcion1.setBackground(new Color(240, 248, 255)); // Azul claro
		opcion1.setForeground(new Color(25, 25, 112)); // Azul medianoche
		
		JMenuItem opcion2 = new JMenuItem("RUTINAS GUARDADAS");
		opcion2.setBackground(new Color(240, 248, 255));
		opcion2.setForeground(new Color(25, 25, 112));
		
		JMenuItem opcion3 = new JMenuItem("SEGUIMIENTO PROPIO");
		opcion3.addActionListener(e -> new SeguimientoPersonal(usuario));
		opcion3.setBackground(new Color(240, 248, 255));
		opcion3.setForeground(new Color(25, 25, 112));
		
		JMenuItem opcion4 = new JMenuItem("GENERAR RUTINA ALEATORIA");
		opcion4.setBackground(new Color(240, 248, 255));
		opcion4.setForeground(new Color(25, 25, 112));
		
		JMenuItem opcion5 = new JMenuItem("RETO DIARIO");
		opcion5.setBackground(new Color(240, 248, 255));
		opcion5.setForeground(new Color(25, 25, 112));
		
		JMenuItem opcion6 = new JMenuItem("CERRAR SESIÓN");
		opcion6.setBackground(new Color(240, 248, 255));
		opcion6.setForeground(new Color(25, 25, 112));

		menuDesplegable.add(opcion1);
		menuDesplegable.add(opcion2);
		menuDesplegable.add(opcion3);
		menuDesplegable.add(opcion4);
		menuDesplegable.add(opcion5);
		menuDesplegable.addSeparator();
		menuDesplegable.add(opcion6);

		opcion6.addActionListener(e -> confirmarSalidaSesion());
        
        //Action listener para el boton reto diario
        opcion5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new RetoDiario(usuario);
				
			}
		});
        
        opcion4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new GenerarRutinaAleatoria(usuario);
			}
		});

		menuDesplegable.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2)); // Borde azul
		
		// Obtener el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Añadimos un mouseListener para cuando se haga click aparezca el menú
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	// Ajustar el tamaño del popup para que ocupe toda la parte derecha de la pantalla
                menuDesplegable.setPopupSize(screenSize.width / 6, screenSize.height);

                // Mostrar el menú en la esquina superior derecha de la ventana
                menuDesplegable.show(menuButton, menuButton.getWidth() / 2, 0);
            }
        });
        

		panelMenu.add(menuButton);
		pNorte.add(panelMenu, BorderLayout.EAST);

		panelPerfil.add(profileButton);
		pNorte.add(panelPerfil, BorderLayout.WEST);

		pNorte.add(labelBienvenido, BorderLayout.CENTER);
		
		// Panel Oeste
        JPanel pOeste = new JPanel();
        pOeste.setBackground(new Color(224, 255, 255)); // Azul pálido
        pOeste.setPreferredSize(new Dimension(300, 0));

        // Añadir imagen al panel Oeste
        JLabel labelOeste = new JLabel();
        labelOeste.setIcon(new ImageIcon("Sources/imagenes/Gigachad1.png"));
        pOeste.add(labelOeste);

        // Panel Este
        JPanel pEste = new JPanel();
        pEste.setBackground(new Color(224, 255, 255));
        pEste.setPreferredSize(new Dimension(300, 0));

        // Añadir imagen al panel Este
        JLabel labelEste = new JLabel();
        labelEste.setIcon(new ImageIcon("Sources/imagenes/Gigachad.png"));
        pEste.add(labelEste);

		JPanel pCentral = new JPanel();
		pCentral.setLayout(new BorderLayout());
		pCentral.setBackground(new Color(245, 245, 245)); // Gris claro

		// Buscador
		JPanel pBuscador = new JPanel();
		pBuscador.setLayout(new FlowLayout());
		pBuscador.setBackground(new Color(245, 245, 245));

		// Crear el campo de búsqueda con un placeholder
		campo_busqueda = new JTextField("Buscar usuario...");
		campo_busqueda.setFont(new Font("Serif", Font.ITALIC, 23)); // Estilo cursiva para el placeholder
		campo_busqueda.setForeground(Color.GRAY); // Color gris claro para el placeholder
		campo_busqueda.setPreferredSize(new Dimension(500, 40)); // Ajustar tamaño del campo

		// Agregar FocusListener para manejar el comportamiento del placeholder
		campo_busqueda.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        // Cuando el campo obtiene el enfoque
		        if (campo_busqueda.getText().equals("Buscar usuario...")) {
		            campo_busqueda.setText(""); // Limpiar el texto
		            campo_busqueda.setFont(new Font("Serif", Font.PLAIN, 23)); // Cambiar a fuente normal
		            campo_busqueda.setForeground(Color.BLACK); // Cambiar a color negro
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        // Cuando el campo pierde el enfoque
		        if (campo_busqueda.getText().isEmpty()) {
		            campo_busqueda.setText("Buscar usuario..."); // Restaurar el placeholder
		            campo_busqueda.setFont(new Font("Serif", Font.ITALIC, 23)); // Regresar a fuente cursiva
		            campo_busqueda.setForeground(Color.GRAY); // Cambiar a color gris
		        }
		    }
		});

		boton_buscar = new JButton("Buscar");
		boton_buscar.setFont(new Font("Serif", Font.BOLD, 26));
		boton_buscar.setBackground(new Color(70, 130, 180));
		boton_buscar.setForeground(Color.WHITE);
		boton_buscar.setPreferredSize(new Dimension(190,50));

		lista = new DefaultListModel<>();
		new JList<>(lista);
		
		//Ponemos en el buscador todos los nombres de usuario que haya en la base de datos
	    datosUsuario((ArrayList<String>) datos);
	   	boton_buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (String string : datos) {
					if(string.equals(campo_busqueda.getText())) {
						dispose();
						new VisitarPerfil(string, usuario);
					}
				}
				
			}
		});
	     
		
		// Espaciador para bajar el buscador
		pBuscador.add(Box.createVerticalStrut(250)); // Baja el buscador más abajo
		pBuscador.add(campo_busqueda);
		pBuscador.add(boton_buscar);
		pCentral.add(pBuscador, BorderLayout.NORTH);

		// Botón "Añadir Rutina" (MÁS ABAJO)
		JPanel pRutina = new JPanel();
		pRutina.setBackground(new Color(245, 245, 245));
		JButton añadirEntreno = new JButton("AÑADIR RUTINA");
		añadirEntreno.addActionListener(e -> {
			new nombreRutinaInterfaz(this, usuario);
		});
		añadirEntreno.setFont(new Font("Serif", Font.BOLD, 24));
		añadirEntreno.setPreferredSize(new Dimension(300, 80));
		añadirEntreno.setBackground(new Color(70, 130, 180));
		añadirEntreno.setForeground(Color.WHITE);

		// Espaciador para bajar "Añadir Rutina"
		pRutina.add(Box.createVerticalStrut(150));
		pRutina.add(añadirEntreno);
		pCentral.add(pRutina, BorderLayout.CENTER);

		// Añadir paneles
		add(pNorte, BorderLayout.NORTH);
		add(pOeste, BorderLayout.WEST);
		add(pEste, BorderLayout.EAST);
		add(pCentral, BorderLayout.CENTER);
		
		//WindowListener para cerrar aplicación
        addWindowListener(new WindowAdapter() { 
			 	@Override 
			 	public void windowClosing(WindowEvent e) { 
			 	 	confirmarSalida();
			 	} 
		 
			});

		setVisible(true);
	}
	
	private void iniciarAnimacionColor(JLabel label) {
	    new Thread(() -> {
	        boolean aumentando = true; // Controla si aumenta o disminuye la intensidad
	        float progreso = 0;        // Controla el progreso de la transición (de 0.0 a 1.0)

	        // Colores inicial y final
	        Color colorInicio = Color.WHITE;
	        Color colorFinal = new Color(70, 130, 180);

	        while (true) {
	            // Ajustar el progreso
	            if (aumentando) {
	                progreso += 0.01f; // Incrementar progreso
	                if (progreso >= 1.0f) aumentando = false; // Si llega al final, invertir
	            } else {
	                progreso -= 0.01f; // Decrementar progreso
	                if (progreso <= 0.0f) aumentando = true; // Si regresa al inicio, invertir
	            }

	            // Interpolar entre blanco y el color final
	            int r = (int) ((1 - progreso) * colorInicio.getRed() + progreso * colorFinal.getRed());
	            int g = (int) ((1 - progreso) * colorInicio.getGreen() + progreso * colorFinal.getGreen());
	            int b = (int) ((1 - progreso) * colorInicio.getBlue() + progreso * colorFinal.getBlue());

	            // Asegurar que los valores de color estén en el rango [0, 255]
	            r = Math.max(0, Math.min(255, r));
	            g = Math.max(0, Math.min(255, g));
	            b = Math.max(0, Math.min(255, b));

	            // Crear el color fuera de la expresión lambda
	            Color nuevoColor = new Color(r, g, b);

	            // Actualizar el color en el hilo de la interfaz gráfica
	            SwingUtilities.invokeLater(() -> label.setForeground(nuevoColor));

	            // Pausa para suavizar la animación
	            try {
	                Thread.sleep(15); // Ajusta la velocidad del cambio de color
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}


	
	//Dialogo para salir mediante el botón x
		private void confirmarSalida() {
			int respuesta = JOptionPane.showConfirmDialog(
					this,
					"¿Desea salir? Si lo hace se cerrará su sesión.",
					"Confirmar salida",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if(respuesta == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		
		//Dialogo para cerrar sesión mediante el botón de cerrar sesión
		private void confirmarSalidaSesion() {
			int respuesta = JOptionPane.showConfirmDialog(
					this,
					"¿Desea cerrar sesión?",
					"Cerrar sesión",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if(respuesta == JOptionPane.YES_OPTION) {
				dispose();
				openMainProyecto();
			}
		}
		
	     private static void openMainProyecto() {
	    	 InicioSesion inicioSesion = new InicioSesion();
	    	 inicioSesion.setVisible(true);
		}


	     //Metodo para añadir todos los usuarios a el PopUpMenu
	     private void datosUsuario(ArrayList<String> datos) {
	    	 try {
	 			Class.forName("org.sqlite.JDBC");
	 		} catch (ClassNotFoundException e) {
	 			System.out.println("No se ha podido cargar el driver de la BD");
	 		}
	 		try {
	 			Connection conn = DriverManager.getConnection
	 				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	 	
	 			Statement stmt = conn.createStatement();
	 			String sql = "SELECT Usuario FROM Usuario";
	 			PreparedStatement queryStmt = conn.prepareStatement(sql);
	 			ResultSet rs = queryStmt.executeQuery();
	 	
	 			while (rs.next()) {
	 				String usuarioBD = rs.getString("Usuario");
	 				datos.add(usuarioBD);
	 			}
	 			stmt.close();
	 			conn.close();
	 		} catch (SQLException e) {
	 			e.printStackTrace();
	 		}
		 }
	
}
