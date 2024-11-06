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
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        JTextField textFieldContrasena = new JTextField();
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
        panel1.add(textFieldContrasena); 
        
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
                textFieldContrasena.setText("");
               
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
				String contraseña = textFieldContrasena.getText();
				
				//Antes de comenzar con el registro buscamos en nuestro CSV
				//si el correo ya está registrado o si el usuario está en uso.
				if(buscarCoincidencia(correo, usuario) == 1) {
					JOptionPane.showMessageDialog(Registro.this, 
							"Este correo electrónico ya está asociado a una cuenta");
				}else if(buscarCoincidencia(correo, usuario) == 2) {
					JOptionPane.showMessageDialog(Registro.this, 
							"Este nombre de usuario ya está en uso");
				}else {
					completarRegistro(nombre, apellidos, usuario, correo, contraseña);
					nuevoPrincipal();
				}
			}	
			
        });
        
	}
	
	//Este metodo lee el CSV y si encuentra el correo en la base de datos
	//devuelve 1 y si encuentra el usuario devuelve 2. Teniendo preferencia
	//la busqueda del correo electrónico mediante un break.
	protected int buscarCoincidencia(String correo, String usuario) {
		Integer coincidencia = 0;
		
		File f = new File("baseDeDatos.csv");
		
		try {
			Scanner sc = new Scanner(f);
			while(sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] campos = linea.split(";");
				String nombreBase = campos[0];
				String apellidosBase = campos[1];
				String usuarioBase = campos[2];
				String correoElectronicoBase = campos[3];
				String contrasenaBase = campos[4];
				
				if(correo.equals(correoElectronicoBase)) {
					coincidencia = 1;
					break;
				}
				if(usuario.equals(usuarioBase)) {
					coincidencia = 2;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return coincidencia;
		
	}
	
	//Metodo para cambiar a la ventana principal después del registro
	protected void nuevoPrincipal() {
		PrincipalWindow principal = new PrincipalWindow();
		dispose();
		JOptionPane.showMessageDialog(Registro.this, "Registro exitoso");
	   	principal.setVisible(true);
	   	
	}
	
	//Metodo que escribe en el CSV el registro
	protected void completarRegistro(String nombre, String apellidos, String usuario, String correo,
			String contrasena) {
		
			try {
				PrintWriter pw = new PrintWriter(new FileWriter("baseDeDatos.csv", true));
				
				
					pw.println(nombre + ";" + apellidos + ";" + usuario + ";" + correo + ";" + contrasena + ";");
				
				
				pw.close();
			} catch (IOException e) {
				System.out.println("Error: no se ha podido abrir el fichero ");
			} 
		
		
	}

}
