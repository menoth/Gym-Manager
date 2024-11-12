package gui;

import javax.swing.*;

import domain.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InicioSesion extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton botonLogin;
    private JButton botonCancel;
    private JButton botonRegistrarse;
    
    public InicioSesion() {
    	
    	// Ajuste 
        setTitle("Inicio de Sesión");
        setSize(720, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
             
        // Creación del panel y su ajuste
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 30, 30));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));
        
        
        
        // Apartado de la petición de usuario
        JLabel userLabel = new JLabel("Usuario:");
        userTextField = new JTextField();
        panel.add(userLabel);
        panel.add(userTextField);
        
        // Apartado de la petición de contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();
        panel.add(passwordLabel);
        panel.add(passwordField);
        
        
        // Creación de los botones
        botonLogin = new JButton("Iniciar Sesión");
        botonCancel = new JButton("Cancelar");
        
        // Creamos un boderLayout para el boton registrarse
        setLayout(new BorderLayout());
        botonRegistrarse = new JButton("Registrarse");
        botonRegistrarse.setPreferredSize(new Dimension(665, 50));
        JPanel panelAbajo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelAbajo.setLayout(new GridLayout(2,1));
        panelAbajo.add(new JLabel());
        panelAbajo.add(botonRegistrarse);
        add(panelAbajo, BorderLayout.SOUTH);	
        
        
        // Action listener para el boton registrarse que lleva a otra ventana
        botonRegistrarse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				abrirRegistrarse();
			}
        });
        
        
        // Añadimos Action Listeners
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = new String(passwordField.getPassword());
                if (validarLogin(username, password)) {
                    JOptionPane.showMessageDialog(InicioSesion.this, "Inicio de sesión exitoso");
                    dispose();
                    PrincipalWindow pw = new PrincipalWindow();
                    
            		pw.setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else {
                    JOptionPane.showMessageDialog(InicioSesion.this, "Usuario o contraseña incorrectos");
                }
            }
        });
        
        // Metodo para borrar lo escrito al darle a Cancelar
        botonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userTextField.setText("");
                passwordField.setText("");
            }
        });
        
        // Añadimos los paneles
        panel.add(botonLogin);
        panel.add(botonCancel);
        
        add(panel, BorderLayout.CENTER);
        
      // WindowListener para cerrar aplicación
        addWindowListener(new WindowAdapter() { 
			 	@Override 
			 	public void windowClosing(WindowEvent e) { 
			 	 	confirmarSalida();
			 	} 
			}); 
    }
    
    // Leer la BD para verificar el log in
    private boolean validarLogin(String username, String password) {
    	Boolean sesion = false;
    	List<Usuario> usuarios = new ArrayList<>();
    	ConectarBaseDeDatos.ConectarBaseDeDatos(usuarios);
    	for(Usuario u : usuarios) {
    		if (((username.equals(u.getUsuario())) && password.equals(u.getContraseña())) || ((username.equals(u.getCorreoElectronico())) && password.equals(u.getContraseña()))) {
				sesion = true;
			}
    	}
		return sesion;
    	
    }
    
    // Método para abrir la ventana de registro al hacer click en el boton registrarse
    private static void abrirRegistrarse() {
   	 Registro registro = new Registro();
   	 registro.setVisible(true);
	}
    
  // Dialogo para salir mediante el botón x
  	private void confirmarSalida() {
  		int respuesta = JOptionPane.showConfirmDialog(
  				this,
  				"¿Desea cerrar la aplicación?",
  				"Confirmar salida",
  				JOptionPane.YES_NO_OPTION,
  				JOptionPane.QUESTION_MESSAGE);
  		if(respuesta == JOptionPane.YES_OPTION) {
  			System.exit(0);
  		}
  	}
  	
}