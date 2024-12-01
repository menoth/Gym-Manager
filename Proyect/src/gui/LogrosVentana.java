package gui;
import javax.swing.*;


import domain.Usuario;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class LogrosVentana extends JFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	LogrosVentana(Usuario usuario) {
        
    	//Crear ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 800);
        setLayout(new BorderLayout());
        setTitle("Selecciona tus logros");

        JLabel info = new JLabel("Selecciona hasta 3 logros:", JLabel.CENTER);
        info.setFont(new Font("Arial", Font.BOLD, 16)); //ChatGPT
        add(info, BorderLayout.NORTH);

        // Panel para los logros
        JPanel logrosPanel = new JPanel(new GridLayout(0, 2, 0, 0));

        // Nombres de las imágenes
        String[] logrosImagenes = {
            "100kgsBanca.png",
            "100kgsSentadilla.png",
            "banca3SIN.png",
            "banca4SIN.png",
            "banca5SIN.png",
            "banca6SIN.png"
        };
        
        // Nombres de los textos
        String[] logrosTexto = {
            "100kgs en Press de Banca",
            "100kgs en Sentadilla",
            "logro3333333333333",
            "Logro4",
            "Logro5",
            "Logro6",
            "Logro7"
        };
        
        //Cargar en la BD los nombres de los logros
        CargarEnBD(logrosTexto);
        
        
        
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        for (int i = 0; i < logrosImagenes.length; i++) {
            JPanel logroPanel = new JPanel();
            logroPanel.setLayout(new BorderLayout());

            // Cargar Imagenes
            ImageIcon icon = null;
            try {
                icon = new ImageIcon(getClass().getResource("/imagenes/" + logrosImagenes[i]));
                Image EscalaImagen = icon.getImage().getScaledInstance(280, 350, Image.SCALE_SMOOTH); //ChatGPT
                icon = new ImageIcon(EscalaImagen);
            } catch (Exception e) {
                System.err.println("No se encontró la imagen: " + logrosImagenes[i]); 
                
            }

            JLabel imagenLabel = new JLabel(icon);
            imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logroPanel.add(imagenLabel, BorderLayout.CENTER);

            // Texto debajo de la imagen
            JLabel textoLabel = new JLabel(logrosTexto[i], JLabel.CENTER);
            textoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            logroPanel.add(textoLabel, BorderLayout.SOUTH);

            // Checkbox para la imagen
            JCheckBox checkBox = new JCheckBox();
            checkBoxes.add(checkBox);
            logroPanel.add(checkBox, BorderLayout.WEST);

            logrosPanel.add(logroPanel);
        }

        // Agregar logros a un JScrollPanel por si hay muchos
        add(new JScrollPane(logrosPanel), BorderLayout.CENTER); 

        // Solo se pueden seleccionar 3 logros
        ArrayList<JCheckBox> seleccionados = new ArrayList<>();
        
        //ChatGPT------------
        
        for (JCheckBox checkBox : checkBoxes) {
            checkBox.addItemListener(e -> {
                if (checkBox.isSelected()) {
                    if (seleccionados.size() < 3) {
                        seleccionados.add(checkBox);
                        //----------------
                        
                    } else {
                        checkBox.setSelected(false);
                        JOptionPane.showMessageDialog(
                            null,
                            "Solo puedes seleccionar hasta 3 logros.",
                            "Límite alcanzado",
                            JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else {
                    seleccionados.remove(checkBox);
                }
            });
        }
        //Botón para cancelar 
        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Arial", Font.BOLD, 14));
        cancelar.addActionListener(e -> {
        	dispose();
        	new EditarPerfil(usuario);    	      	
        });
        
        

        // Botón para confirmar selección
        JButton confirmar = new JButton("Confirmar");
        confirmar.setFont(new Font("Arial", Font.BOLD, 14));
        confirmar.addActionListener(e -> {
        	//Si no se selecciona ningún logro
            if (seleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No has seleccionado ningún logro.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
            	
            	String url = "jdbc:sqlite:Sources/bd/baseDeDatos.db";

                try (Connection conn = DriverManager.getConnection(url)) {
                    // Eliminar los logros actuales del usuario
                    String deleteQuery = "DELETE FROM ConsigueLogro WHERE Usuario = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                        deleteStmt.setString(1, usuario.getUsuario());
                        deleteStmt.executeUpdate();
                    }

                    // Insertar los logros seleccionados
                    String insertQuery = "INSERT INTO ConsigueLogro (Usuario, Logro) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        for (JCheckBox checkBox : seleccionados) {
                            int index = checkBoxes.indexOf(checkBox);
                            String logroNombre = logrosTexto[index];

                            // Obtener el ID del logro correspondiente
                            String selectLogroQuery = "SELECT ID_Logro FROM Logro WHERE Nombre = ?";
                            try (PreparedStatement selectStmt = conn.prepareStatement(selectLogroQuery)) {
                                selectStmt.setString(1, logroNombre);
                                var rs = selectStmt.executeQuery();
                                if (rs.next()) {
                                    int logroID = rs.getInt("ID_Logro");

                                    // Insertar logro y usuario en la tabla ConsigueLogro
                                    insertStmt.setString(1, usuario.getUsuario());
                                    insertStmt.setInt(2, logroID);
                                    insertStmt.executeUpdate();
                                }
                            }
                        }
                    }
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Error al cargar los logros y usuarios correspondientes");
                }
            }
            dispose();
            new PerfilUsuario(usuario.getUsuario());
            
            	
            
        });

        // Añadir botones a un GridLayout
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        panel.add(confirmar);
        panel.add(cancelar);
        add(panel, BorderLayout.SOUTH);
        
        setVisible(true);
        
        
    }
	
	 private void CargarEnBD(String[] logrosTexto) {
		 String url = "jdbc:sqlite:Sources/bd/baseDeDatos.db";

	        
	        String insertarSQL = "INSERT OR IGNORE INTO Logro (Nombre) VALUES (?)";
	        String eliminarSQL = "DELETE FROM Logro WHERE Nombre NOT IN (%s)";

	        try  {
	        	Connection conn = DriverManager.getConnection(url);
	            // Insertar logros
	            try (PreparedStatement stmtInsertar = conn.prepareStatement(insertarSQL)) {
	                for (String logro : logrosTexto) {
	                    if (logro != null && !logro.isEmpty()) {
	                        stmtInsertar.setString(1, logro);
	                        stmtInsertar.executeUpdate();
	                    }
	                }
	            }

	            // Preparar la consulta para eliminar logros no incluidos en la lista
	            // ChatGPT-----------------
	         // Se crea un objeto StringBuilder para construir dinámicamente una cadena de texto.
	            StringBuilder placeholders = new StringBuilder(); 

	            // Bucle para iterar sobre los elementos de 'logrosTexto'.
	            for (int i = 0; i < logrosTexto.length; i++) { 
	                // Añade un signo de interrogación ("?") al StringBuilder. 
	                // Este "?" suele representar un parámetro en consultas SQL.
	                placeholders.append("?"); 
	                
	                // Si no es el último elemento del arreglo, añade una coma (",").
	                if (i < logrosTexto.length - 1) { 
	                    placeholders.append(","); 
	                }
	            }

	            // Construye la consulta SQL final reemplazando un marcador dentro de 'eliminarSQL'
	            // con los placeholders generados (por ejemplo: "?, ?, ?").
	            String query = String.format(eliminarSQL, placeholders); 

	            //----------------
	            
	            try (PreparedStatement stmtEliminar = conn.prepareStatement(query)) {
	                for (int i = 0; i < logrosTexto.length; i++) {
	                    stmtEliminar.setString(i + 1, logrosTexto[i]);
	                }
	                stmtEliminar.executeUpdate();
	                stmtEliminar.close();
	            }
	            
	            conn.close();

	        } catch (SQLException ex) {
	            System.err.println("Error al actualizar la base de datos");
	            ex.printStackTrace();
	        }
	    }
	
	
}
