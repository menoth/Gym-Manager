package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioSesion extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton botonLogin;
    private JButton botonCancel;
    
    public InicioSesion() {
    	
    	// Ajuste 
        setTitle("Inicio de Sesión");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        // Creación del panel y su ajuste
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        
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
        
        //Creación de los botones
        botonLogin = new JButton("Iniciar Sesión");
        botonCancel = new JButton("Cancelar");
        
        //Añadimos Action Listeners
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

        botonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userTextField.setText("");
                passwordField.setText("");
            }
        });
        
        //Añadimos los paneles
        panel.add(botonLogin);
        panel.add(botonCancel);
        add(panel, BorderLayout.CENTER);
        
    }
    private boolean validarLogin(String username, String password) {
        return username.equals("admin") && password.equals("1234");
    }

  
}