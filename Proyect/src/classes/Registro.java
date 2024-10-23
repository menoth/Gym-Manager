package classes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Registro extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton botonRegistrarse;
    private JButton botonCancel;
    private JButton botonInicioSesion;

	public Registro() {
		
		//Ajustes básicos para la ventana
		setTitle("Registro");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Creación del panel 
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(6, 2, 30, 30));
        
        //Establecer bordes para el panel
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));
        
        //Etiqueta "Nombre" con su caja de texto
        JTextField textFieldNombre = new JTextField();
        JLabel labelNombre = new JLabel("Nombre:");
        
        //Etiqueta "Apellido" con su caja de texto
        JTextField textFieldApellido = new JTextField();
        JLabel labelApellido = new JLabel("Apellidos:");
        
      //Etiqueta "Usuario" con su caja de texto
        JTextField textFieldUsuario = new JTextField();
        JLabel labelUsuario = new JLabel("Usuario:");
        
        //Etiqueta "Correo electrónico" con su caja de texto
        JTextField textFieldCorreo = new JTextField();
        JLabel labelCorreo = new JLabel("Correo electrónico:");
        
        //Etiqueta "Contraseña" con su caja de texto
        JTextField textFieldContraseña = new JTextField();
        JLabel labelContraseña = new JLabel("Contraseña:");
        
        //Añadimos las etiquetas y las cajas de texto al Panel1 en el orden correspondiente
        panel1.add(labelNombre);
        panel1.add(textFieldNombre);
        
        panel1.add(labelApellido);
        panel1.add(textFieldApellido);
        
        panel1.add(labelUsuario);
        panel1.add(textFieldUsuario);
        
        panel1.add(labelCorreo);
        panel1.add(textFieldCorreo);
        
        panel1.add(labelContraseña);
        panel1.add(textFieldContraseña); 
        
        //Creamos los botones Cancelar y registrarse
        botonCancel = new JButton("Cancelar");
        botonRegistrarse = new JButton("Registrarse");
        
        //Añadimos esos dos botones al Panel1
        panel1.add(botonCancel);
        panel1.add(botonRegistrarse);
        
      
        //Creamos el boton Iniciar Sesión y le asigamos un tamaño
        botonInicioSesion = new JButton("Iniciar Sesión");
        botonInicioSesion.setPreferredSize(new Dimension(665, 45));
        
      //Creamos un nuevo panel2 para poder centrar el boton de Iniciar Sesión 
        JPanel panel2 = new JPanel();
        setLayout(new BorderLayout());
        
        //Le establecemos al panel un GridLayout de 2 filas 1 colummna
        panel2.setLayout(new GridLayout(2,1));
        
        //Para que quede centrado añadimos primero una etiqueta invisible y después el botón
        panel2.add(new JLabel());
        panel2.add(botonInicioSesion);
        
        
        //Agregamos el panel1 a la ventana
        add(panel1, BorderLayout.CENTER);
        
        //Agregamos el panel2 a la ventana
        add(panel2, BorderLayout.SOUTH);	
        
	}

}
