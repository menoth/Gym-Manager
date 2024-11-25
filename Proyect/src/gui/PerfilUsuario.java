package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		

		// Creamos un jTextArea que será la descripción del usuario
		JTextArea desc = new JTextArea(uElegido.getDescripcion());
		
		// Detalles del JTextArea
		desc.setWrapStyleWord(true); // Ajusta palabras completas en la línea siguiente
		desc.setLineWrap(true); // Habilita el ajuste de línea
		desc.setEditable(false); // No editable
		desc.setFont(new Font("Arial", Font.PLAIN, 18));
		desc.setPreferredSize(new Dimension(400, 200));
		
		// Añadimos la descripcion a panelSubOeste1
		panelIz2.add(desc);
			
		//FotoVitrina1
		ImageIcon fotoVitrina1 = new ImageIcon("Sources/imagenes/banca5SIN.png");
		Image imagenVitrina1 = fotoVitrina1.getImage(); // Obtener el objeto Image
	    Image nuevaImagen1 = imagenVitrina1.getScaledInstance(300, 200, Image.SCALE_SMOOTH); // Ajustar tamaño
	    fotoVitrina1 = new ImageIcon(nuevaImagen1);
		JLabel prueba1 = new JLabel(fotoVitrina1);
		panelIz3.add(prueba1);
		
		//FotoVitrina2
		ImageIcon fotoVitrina2 = new ImageIcon("Sources/imagenes/banca5SIN.png");
		Image imagenVitrina2 = fotoVitrina2.getImage(); // Obtener el objeto Image
	    Image nuevaImagen2 = imagenVitrina2.getScaledInstance(300, 200, Image.SCALE_SMOOTH); // Ajustar tamaño
	    fotoVitrina2 = new ImageIcon(nuevaImagen2);
		JLabel prueba2 = new JLabel(fotoVitrina2);
		panelIz3.add(prueba2);
		
		//FotoVitrina3
		ImageIcon fotoVitrina3 = new ImageIcon("Sources/imagenes/banca5SIN.png");
		Image imagenVitrina3 = fotoVitrina3.getImage(); // Obtener el objeto Image
	    Image nuevaImagen3 = imagenVitrina3.getScaledInstance(300, 200, Image.SCALE_SMOOTH) ; // Ajustar tamaño
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
		add(pIzquierda);
		pIzquierda.add(panelIz1);
		pIzquierda.add(panelIz2);
		pIzquierda.add(panelIz3);
		
//------------------------LADO DERECHO------------------------------
		JPanel pDerecha = new JPanel();
		add(pDerecha);
		
		 	
		
		// Detalles ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
	}	
}