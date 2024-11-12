package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import domain.Usuario;

public class Registro extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private JButton botonRegistrarse;
    private JButton botonCancel;
    private JButton botonInicioSesion;

	public Registro() {
		
		// Ajustes básicos para la ventana
		setTitle("Registro");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Creación del panel 
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(6, 2, 30, 30));
        
        // Establecer bordes para el panel
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));
        
        // Etiqueta "Nombre" con su caja de texto
        JTextField textFieldNombre = new JTextField();
        JLabel labelNombre = new JLabel("Nombre:");
        
        // Etiqueta "Apellido" con su caja de texto
        JTextField textFieldApellido = new JTextField();
        JLabel labelApellido = new JLabel("Apellidos:");
        
        // Etiqueta "Usuario" con su caja de texto
        JTextField textFieldUsuario = new JTextField();
        JLabel labelUsuario = new JLabel("Usuario:");
        
        // Etiqueta "Correo electrónico" con su caja de texto
        JTextField textFieldCorreo = new JTextField();
        JLabel labelCorreo = new JLabel("Correo electrónico:");
        
        // Etiqueta "Contraseña" con su caja de texto
        JPasswordField textFieldContraseña = new JPasswordField();
        JLabel labelContrasena = new JLabel("Contraseña:");
        
        // Añadimos las etiquetas y las cajas de texto al Panel1 en el orden correspondiente
        panel1.add(labelNombre);
        panel1.add(textFieldNombre);
        
        panel1.add(labelApellido);
        panel1.add(textFieldApellido);
        
        panel1.add(labelUsuario);
        panel1.add(textFieldUsuario);
        
        panel1.add(labelCorreo);
        panel1.add(textFieldCorreo);
        
        panel1.add(labelContrasena);
        panel1.add(textFieldContraseña); 
        
        // Creamos los botones Cancelar y registrarse
        botonCancel = new JButton("Cancelar");
        botonRegistrarse = new JButton("Registrarse");
        
        // Añadimos esos dos botones al Panel1
        panel1.add(botonCancel);
        panel1.add(botonRegistrarse);
        
      
        // Creamos el boton Iniciar Sesión y le asigamos un tamaño
        botonInicioSesion = new JButton("¿Ya tienes una cuenta?");
        botonInicioSesion.setPreferredSize(new Dimension(665, 45));
        
        // Creamos un nuevo panel2 para poder centrar el boton de Iniciar Sesión 
        JPanel panel2 = new JPanel();
        setLayout(new BorderLayout());
        
        // Le establecemos al panel un GridLayout de 2 filas 1 colummna
        panel2.setLayout(new GridLayout(2,1));
        
        // Para que quede centrado añadimos primero una etiqueta invisible y después el botón
        panel2.add(new JLabel());
        panel2.add(botonInicioSesion);
        
        
        // Agregamos el panel1 a la ventana
        add(panel1, BorderLayout.CENTER);
        
        // Agregamos el panel2 a la ventana
        add(panel2, BorderLayout.SOUTH);	
        
        // Añadimos 
        botonInicioSesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InicioSesion inicioSesion = new InicioSesion();
				dispose();
			   	inicioSesion.setVisible(true);
				
			}
        });
        	
        
        
      //Metodo para borrar lo escrito al darle a Cancelar
        botonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldNombre.setText("");
                textFieldApellido.setText("");
                textFieldUsuario.setText("");
                textFieldCorreo.setText("");
                textFieldContraseña.setText("");
               
            }
        });
        
        //Action listener del boton registrarse
        botonRegistrarse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = textFieldNombre.getText();
				String apellidos = textFieldApellido.getText();
				String usuario = textFieldUsuario.getText();
				String correo = textFieldCorreo.getText();
				String contraseña = new String(textFieldContraseña.getPassword());
				
				//Verificar que ningún campo está vacío
		        if (nombre.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
		            JOptionPane.showMessageDialog(Registro.this, 
		                    "Todos los campos deben estar completos.");
		            return;
		        }
		        
		        //Verificar que la contraseña tiene entre 8 y 12 carácteres y sin espacios
				if (contraseña.length() < 8 || contraseña.length() > 12 || contraseña.contains(" ")) {
		            JOptionPane.showMessageDialog(Registro.this, 
		                    "La contraseña debe tener entre 8 y 12 caracteres y no debe contener espacios.");
		            return;
		        }
				
				//Antes de comenzar con el registro buscamos en nuestro CSV
				//si el correo ya está registrado o si el usuario está en uso.
				if(buscarCoincidencia(correo, usuario) == 1) {
					JOptionPane.showMessageDialog(Registro.this, 
							"Este correo electrónico ya está asociado a una cuenta");
				}if(buscarCoincidencia(correo, usuario) == 2) {
					JOptionPane.showMessageDialog(Registro.this, 
							"Este nombre de usuario ya está en uso");
				}else {
					
					completarRegistro(nombre, apellidos, usuario, correo, contraseña);
					nuevoPrincipal();
				}
			}	
			
        });
        
	}
	
	//Este metodo lee la BD y si encuentra el correo en la base de datos
	//devuelve 1 y si encuentra el usuario devuelve 2. Teniendo preferencia
	//la busqueda del correo electrónico mediante un break.
	protected int buscarCoincidencia(String correo, String usuario) {
		Integer coincidencia = 0;
		List<Usuario> usuarios = new ArrayList<>();
    	ConectarBaseDeDatos.ConectarBaseDeDatos(usuarios);
    	for(Usuario u : usuarios) {
    		if (correo.equals(u.getCorreoElectronico())) {
				coincidencia = 1;
				break;
			}
    		if (usuario.equals(u.getUsuario())) {
				coincidencia = 2;
			}
    	}
		
		return coincidencia;
		
	}
	
	//Metodo para cambiar a la ventana principal después del registro
	protected void nuevoPrincipal() {
		
		dispose();
		JOptionPane.showMessageDialog(Registro.this, "Registro exitoso");
		PrincipalWindow principal = new PrincipalWindow();
	   	principal.setVisible(true);
	   	
	}
	
	//Metodo que escribe en la BD el registro
	protected void completarRegistro(String nombre, String apellidos, String usuario, String correo,
			String contrasena) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO Usuario VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			queryStmt.setString(1, nombre);
			queryStmt.setString(2, apellidos);
			queryStmt.setString(3, usuario);
			queryStmt.setString(4, correo);
			queryStmt.setString(5, contrasena);
			queryStmt.setString(6, "NULL");
			queryStmt.setString(7, "NULL");
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
