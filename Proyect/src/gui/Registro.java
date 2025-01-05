package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Usuario;

public class Registro extends JFrame {

    private static final long serialVersionUID = 1L;
    private JButton botonRegistrarse;
    private JButton botonCancel;
    private JButton botonInicioSesion;
    private static final Color COLOR_PRINCIPAL = new Color(70, 130, 180); // Color principal

    public Registro() {
        // Configuración inicial de la ventana
        setTitle("GYM Routine Manager - Registro");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Superior: Título con diseño atractivo
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(COLOR_PRINCIPAL);
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        JLabel labelTitulo = new JLabel("GYM Routine Manager");
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Serif", Font.BOLD, 32));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel labelSubtitulo = new JLabel("Crea tu cuenta para empezar");
        labelSubtitulo.setForeground(Color.WHITE);
        labelSubtitulo.setFont(new Font("Serif", Font.PLAIN, 18));
        labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitulo.add(Box.createVerticalStrut(20));
        panelTitulo.add(labelTitulo);
        panelTitulo.add(labelSubtitulo);
        panelTitulo.add(Box.createVerticalStrut(20));
        add(panelTitulo, BorderLayout.NORTH);

        // Panel Central: Formulario de registro
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setFont(new Font("Serif", Font.BOLD, 18));
        labelNombre.setForeground(COLOR_PRINCIPAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(labelNombre, gbc);

        JTextField textFieldNombre = new JTextField();
        textFieldNombre.setFont(new Font("Serif", Font.PLAIN, 18));
        textFieldNombre.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        panelFormulario.add(textFieldNombre, gbc);

        JLabel labelApellido = new JLabel("Apellidos:");
        labelApellido.setFont(new Font("Serif", Font.BOLD, 18));
        labelApellido.setForeground(COLOR_PRINCIPAL);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(labelApellido, gbc);

        JTextField textFieldApellido = new JTextField();
        textFieldApellido.setFont(new Font("Serif", Font.PLAIN, 18));
        textFieldApellido.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        panelFormulario.add(textFieldApellido, gbc);

        JLabel labelUsuario = new JLabel("Usuario:");
        labelUsuario.setFont(new Font("Serif", Font.BOLD, 18));
        labelUsuario.setForeground(COLOR_PRINCIPAL);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(labelUsuario, gbc);

        JTextField textFieldUsuario = new JTextField();
        textFieldUsuario.setFont(new Font("Serif", Font.PLAIN, 18));
        textFieldUsuario.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        panelFormulario.add(textFieldUsuario, gbc);

        JLabel labelCorreo = new JLabel("Correo electrónico:");
        labelCorreo.setFont(new Font("Serif", Font.BOLD, 18));
        labelCorreo.setForeground(COLOR_PRINCIPAL);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(labelCorreo, gbc);

        JTextField textFieldCorreo = new JTextField();
        textFieldCorreo.setFont(new Font("Serif", Font.PLAIN, 18));
        textFieldCorreo.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        panelFormulario.add(textFieldCorreo, gbc);

        JLabel labelContrasena = new JLabel("Contraseña:");
        labelContrasena.setFont(new Font("Serif", Font.BOLD, 18));
        labelContrasena.setForeground(COLOR_PRINCIPAL);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(labelContrasena, gbc);

        JPasswordField textFieldContraseña = new JPasswordField();
        textFieldContraseña.setFont(new Font("Serif", Font.PLAIN, 18));
        textFieldContraseña.setPreferredSize(new Dimension(300, 40));
        gbc.gridx = 1;
        panelFormulario.add(textFieldContraseña, gbc);

        botonCancel = new JButton("Cancelar");
        botonCancel.setFont(new Font("Serif", Font.BOLD, 18));
        botonCancel.setBackground(new Color(244, 67, 54));
        botonCancel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(botonCancel, gbc);

        botonRegistrarse = new JButton("Registrarse");
        botonRegistrarse.setFont(new Font("Serif", Font.BOLD, 18));
        botonRegistrarse.setBackground(COLOR_PRINCIPAL);
        botonRegistrarse.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(botonRegistrarse, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel Inferior: Botón para iniciar sesión
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.WHITE);
        botonInicioSesion = new JButton("¿Ya tienes una cuenta? Inicia sesión");
        botonInicioSesion.setFont(new Font("Serif", Font.PLAIN, 14));
        botonInicioSesion.setForeground(COLOR_PRINCIPAL);
        botonInicioSesion.setBorderPainted(false);
        botonInicioSesion.setContentAreaFilled(false);
        botonInicioSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelInferior.add(botonInicioSesion);

        add(panelInferior, BorderLayout.SOUTH);

        // Listeners para los botones
        botonInicioSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InicioSesion inicioSesion = new InicioSesion();
                dispose();
                inicioSesion.setVisible(true);
            }
        });

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
				List<Usuario> usuarios = new ArrayList<>();
		    	ConectarBaseDeDatos.ConectarBaseDeDatos(usuarios);
				for(Usuario u : usuarios) {
					if(buscarCoincidencia(correo, u.getCorreoElectronico())) {
						JOptionPane.showMessageDialog(Registro.this, 
								"Este correo electrónico ya está asociado a una cuenta");
						return;
					}else if(buscarCoincidencia(usuario, u.getUsuario())) {
						JOptionPane.showMessageDialog(Registro.this, 
								"Este nombre de usuario ya está en uso");
						return;
					}
				}
				
				completarRegistro(nombre, apellidos, usuario, correo, contraseña);
				nuevoPrincipal(usuario);
				
			}	
			
        });
    }
    
    //Este metodo lee la BD y si encuentra el correo en la base de datos
  	//devuelve 1 y si encuentra el usuario devuelve 2. Teniendo preferencia
  	//la busqueda del correo electrónico mediante un break.
    protected boolean buscarCoincidencia(String dato1, String dato2) {
		Boolean coincidencia = false;
    	if(dato1.equals(dato2)) {
    		coincidencia = true;
    	}
		return coincidencia;
		
	}
    
    //Metodo para cambiar a la ventana principal después del registro
  	protected void nuevoPrincipal(String usuario) {
  		
  		dispose();
  		JOptionPane.showMessageDialog(Registro.this, "Registro exitoso");
  		PrincipalWindow principal = new PrincipalWindow(usuario);
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
