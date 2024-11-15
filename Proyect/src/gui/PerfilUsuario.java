package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import domain.Usuario;

public class PerfilUsuario extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public PerfilUsuario(String usuario) {
		
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
		
		// Detalles ventana
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Perfil");
		
		// Primer layout
		setLayout(new BorderLayout());
		
		// Añadimos el panel en el norte y lo dividimos en 1 fila y 6 columnas
		JPanel panelNorte = new JPanel();
		panelNorte.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
		
		// Margenes para el panelNorte
		panelNorte.setBorder(new EmptyBorder(60, 20, 10, 10)); 		
		
		// Botón para volver a la ventana principal
		JButton botonPrincipal = new JButton("VOLVER");
		botonPrincipal.setPreferredSize(new Dimension(100, 40));
		panelNorte.add(botonPrincipal);
		
		// Label que simula la foto de perfil
		JLabel fotoPerfil = new JLabel("");
		fotoPerfil.setPreferredSize(new Dimension(130, 130));
		fotoPerfil.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelNorte.add(fotoPerfil);
		
		// Label del nombre y apellidos hecho con HTML para poder hacerlo en dos lineas
		String texto = "<html><b>" + uElegido.getNombre() + "</b><br>"
                + uElegido.getApellidos() + "</b><br>"
                + "@<b>" + uElegido.getUsuario() + "</b></html>";
			 
		JLabel nombreApellidos = new JLabel(texto);
		
		// Cambiamos el tamaño de la fuente
		nombreApellidos.setFont(new Font("Arial",Font.PLAIN ,18));
		panelNorte.add(nombreApellidos);
		
		// Añadimos el panelNorte
		add(panelNorte, BorderLayout.NORTH);
		
		//-------------------------------------------------------
		
		// Nuevo panel en la izquierda
		JPanel panelOeste = new JPanel();
		
		// Tendrá una columna y tres filas, una para la descripción del perfil
		// y 2 para la vitrina de logros.
		panelOeste.setLayout(new GridLayout(2, 1));
		
		
		// Margenes para el panelNorte
		panelOeste.setBorder(new EmptyBorder(60, 80, 10, 10)); 	
		
		// Creamos otro panel que irá en la posicion (1,1) del gridLayout
		// con el fin de ajustar el tamaño del JTextArea
		JPanel panelSubOeste1 = new JPanel();
		panelSubOeste1.setLayout(new FlowLayout());
		
		
		// Creamos un jTextArea que será la descripción del usuario
		JTextArea desc = new JTextArea(uElegido.getDescripcion());
		
		// Detalles del JTextArea
		desc.setWrapStyleWord(true); // Ajusta palabras completas en la línea siguiente
		desc.setLineWrap(true); // Habilita el ajuste de línea
		desc.setEditable(false); // No editable
		desc.setFont(new Font("Arial", Font.PLAIN, 18));
		desc.setPreferredSize(new Dimension(400, 200));
		
		// Añadimos la descripcion a panelSubOeste1
		panelSubOeste1.add(desc);
			
		// Nuevo panelSubOeste2 para el label vitrina
		JPanel panelSubOeste2 = new JPanel();
		panelSubOeste2.setLayout(new BorderLayout());
		
		JLabel labelVitrina = new JLabel("Vitrina");
		
		panelSubOeste2.add(labelVitrina, BorderLayout.NORTH);
		
		// Un panel de (2,3) para la vitrina
		JPanel vitrina = new JPanel();
		vitrina.setLayout(new GridLayout(2,3));
		
		JLabel prueba1 = new JLabel("aa");
		vitrina.add(prueba1);
		JLabel prueba2 = new JLabel("aa");
		vitrina.add(prueba2);
		JLabel prueba3 = new JLabel("aa");
		vitrina.add(prueba3);
		JLabel prueba4 = new JLabel("aa");
		vitrina.add(prueba4);
		JLabel prueba5 = new JLabel("aa");
		vitrina.add(prueba5);
		JLabel prueba6 = new JLabel("aa");
		vitrina.add(prueba6);
		
		panelSubOeste2.add(vitrina);
		
		// Añadimos el panelSubOeste1 a panelOeste
		panelOeste.add(panelSubOeste1);
		
		// Añadimos el panelSubOeste2 a panelOeste
		panelOeste.add(panelSubOeste2);
		
		// Añadimos el panelOeste
		add(panelOeste, BorderLayout.WEST);		
		
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
				
		// Detalles ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
	}
	
}