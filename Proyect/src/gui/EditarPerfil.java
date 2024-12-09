package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domain.Usuario;

public class EditarPerfil extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public EditarPerfil(Usuario usuario) {
		
		setSize(new Dimension(500, 650));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(176,224,230));
        panelPrincipal.setLayout(new GridLayout(12, 1, 0, 10));
        panelPrincipal.setBorder(new EmptyBorder(0, 40, 0, 40));
        
        //Panel para el botón volver
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.setBackground(new Color(176,224,230));
        panelPrincipal.add(panel2);
        
        //Volver
        JButton volver = new JButton("Volver");
        panel2.add(volver, BorderLayout.WEST);
        
        //Action Listener para Volver
        volver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PerfilUsuario(usuario.getUsuario());
				
			}
		});
        
        //Nombre
        JLabel nombre = new JLabel("Nombre");
        JTextField txtNombre = new JTextField();
        txtNombre.setText(usuario.getNombre());
        panelPrincipal.add(nombre);
        panelPrincipal.add(txtNombre);
        
        //Apellidos
        JLabel apellidos = new JLabel("Apellidos");
        JTextField txtApellidos = new JTextField();
        txtApellidos.setText(usuario.getApellidos());
        panelPrincipal.add(apellidos);
        panelPrincipal.add(txtApellidos);
        
        //Descripcion
        JLabel descripcion = new JLabel("Descripción");
        JTextField txtDescripcion = new JTextField();
        txtDescripcion.setText(usuario.getDescripcion());
        panelPrincipal.add(descripcion);
        panelPrincipal.add(txtDescripcion);
        
        //Guardar
        JButton guardar = new JButton("Guardar cambios");
        panelPrincipal.add(guardar);
        
        //Guardar
        JLabel otros = new JLabel("Otros cambios");
        panelPrincipal.add(otros);
        
        //Fotoperfil
        JButton fotoPerfil = new JButton("Foto de perfil");
        panelPrincipal.add(fotoPerfil);
        
        //Vitrina
        JButton vitrina = new JButton("Vitrina");
        panelPrincipal.add(vitrina);
        vitrina.addActionListener(e -> {
        	dispose();
        	new LogrosVentana(usuario);
        	
        });
        
        guardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreString = txtNombre.getText();
				String apellidosString = txtApellidos.getText();
				String descripcionString = txtDescripcion.getText();
				
				if(nombreString.length()>20) {
					JOptionPane.showMessageDialog
					(EditarPerfil.this, "El nombre no puede tener más de 20 caracteres");
				}
				else if(apellidosString.length()>25) {
					JOptionPane.showMessageDialog
					(EditarPerfil.this, "Los apellidos no puede tener más de 25 caracteres");
				}
				else if (descripcionString.length()>200){
					JOptionPane.showMessageDialog
					(EditarPerfil.this, "La descripción no puede tener más de 200 caracteres");
				}
				else {
					actualizarPerfil(nombreString, apellidosString, descripcionString, usuario.getUsuario());
					dispose();
					new PerfilUsuario(usuario.getUsuario());
					
				}
				
			}
				
		});
        
        
     // Acción del botón cambiar foto
        fotoPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear JFileChooser para seleccionar imagen
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // Filtro para permitir solo imágenes
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        "Imágenes (JPG, PNG, GIF)", "jpg", "png", "gif"));

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String nuevoNombreFoto = usuario.getUsuario() + "_fotoPerfil" + getExtension(selectedFile.getName());
                    File destino = new File("Sources/imagenes/" + nuevoNombreFoto);

                    try {
                        // Copiar la imagen a la carpeta del proyecto
                    	
                    	//Files.copy hecho con ChatGPT4
                        Files.copy(selectedFile.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        // Actualizar la base de datos con el nuevo nombre
                        actualizarFotoEnBaseDeDatos(usuario.getUsuario(), nuevoNombreFoto);
                        dispose();

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al copiar la imagen", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
		
	
        add(panelPrincipal);
		setVisible(true);
	}
	
	protected void actualizarPerfil(String nombreString, String apellidosString, String descripcionString, String idUsuario) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "UPDATE Usuario SET nombre = ?, apellidos = ?, descripcion = ? WHERE usuario = ?";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			queryStmt.setString(1, nombreString);
			queryStmt.setString(2, apellidosString);
			queryStmt.setString(3, descripcionString);
			queryStmt.setString(4, idUsuario);
			queryStmt.executeUpdate();
			
			queryStmt.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}


	
	protected void actualizarFotoEnBaseDeDatos(String usuario, String nuevoNombreFoto) {
		 try {
	            Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	            Statement stmt = conn.createStatement();

	            // Actualizar el nombre de la foto en la base de datos
	            String query = "UPDATE Usuario SET FotoDePerfil = '" + nuevoNombreFoto + "' WHERE Usuario = '" + usuario + "'";
	            stmt.executeUpdate(query);

	            stmt.close();
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error al actualizar la foto en la base de datos", "Error",
	                    JOptionPane.ERROR_MESSAGE);
	        }
		 	dispose();
			new PerfilUsuario(usuario);
		
	}


	//Generado con ChatGPT4
	protected String getExtension(String filename) {
		int lastIndex = filename.lastIndexOf('.');	
       return lastIndex == -1 ? "" : filename.substring(lastIndex);
		
	}	
}
 