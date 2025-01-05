package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class formularioAdmin extends JFrame {

    private static final long serialVersionUID = 1L;

    // Lista de ejercicios dinámicos desde la base de datos
    private List<String> datos;

    public formularioAdmin() {

        // Cargar ejercicios desde la base de datos
        datos = cargarEjerciciosDesdeBD();

        // Detalles de la ventana
        setSize(new Dimension(1000, 400));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con GridLayout(1,1) para dividir la ventana en 2
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(1, 1));

        // panelAnadir es el panel del Oeste
        JPanel panelAnadir = new JPanel();
        panelAnadir.setLayout(new BorderLayout());
        panelAnadir.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelAnadir.setBackground(Color.GRAY);

        // panelEliminar es el panel del Este
        JPanel panelEliminar = new JPanel();
        panelEliminar.setLayout(new BorderLayout());
        panelEliminar.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelEliminar.setBackground(Color.GRAY);

        // ---------------------------------LABELS SUPERIORES (AÑADIR Y ELIMINAR)--------------------------
        
        JPanel anadirSuperior = new JPanel();
        anadirSuperior.setLayout(new FlowLayout());
        anadirSuperior.setBackground(Color.green);

        JPanel eliminarSuperior = new JPanel();
        eliminarSuperior.setLayout(new FlowLayout());
        eliminarSuperior.setBackground(Color.red);

        JLabel añadir = new JLabel("AÑADIR");
        anadirSuperior.add(añadir);

        JLabel eliminar = new JLabel("ELIMINAR");
        eliminarSuperior.add(eliminar);
        
        //--------------------------------------------------------------------------------------------------
        
        // -------------------------CAMPO ESCRIBIR NUEVO EJERCICIO------------------------------------------
        
        JPanel panelElementosAñadir = new JPanel();
        //panelElementosAñadir.setLayout(new GridLayout(8, 1, 0, 5));
        panelElementosAñadir.setLayout(new FlowLayout());
        panelElementosAñadir.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel introduceNombre = new JLabel("Introduce el nombre del ejercicio:");
        JTextField campoNombre = new JTextField(10);
        JLabel introduceMusculoPrincipal = new JLabel("Introduce el músculo principal:");
        JTextField campoMusculoPrincipal = new JTextField(10);
        JLabel introduceMusculoSecundario = new JLabel("Introduce el músculo secundario:");
        JTextField campoMusculoSecundario = new JTextField(10);
        JButton botonAñadir = new JButton("Confirmar");
        JButton foto = new JButton("AÑADIR");
        
        foto.addActionListener(new ActionListener() {
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
                    String nuevoNombreFoto = campoNombre.getText() + getExtension(selectedFile.getName());
                    File destino = new File("Sources/imagenes/" + nuevoNombreFoto);

                    try {
                        // Copiar la imagen a la carpeta del proyecto
                    	
                    	//Files.copy hecho con ChatGPT4
                        Files.copy(selectedFile.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        dispose();

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al copiar la imagen", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Action listener para el botón Añadir
        botonAñadir.addActionListener(e -> {
            String nombreEjercicio = campoNombre.getText().trim();
            String musculoPrincipal = campoMusculoPrincipal.getText().trim();
            String musculoSecundario = campoMusculoSecundario.getText().trim();

            if (nombreEjercicio.isEmpty() || musculoPrincipal.isEmpty() || musculoSecundario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            // Generar un nuevo ID basado en el tamaño actual de los datos
            int nuevoId = datos.size();

            try {
                // Usar el método estático para insertar el ejercicio
                InsertarDatosBD.insertarEjercicio(nuevoId, nombreEjercicio, musculoPrincipal, musculoSecundario);
                datos.add(nombreEjercicio); // Actualiza la lista local
                JOptionPane.showMessageDialog(this, "Ejercicio añadido correctamente.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al añadir el ejercicio.");
            }
        });

            

        // Añade los campos al panel
        panelElementosAñadir.add(introduceNombre);
        panelElementosAñadir.add(campoNombre);
        panelElementosAñadir.add(introduceMusculoPrincipal);
        panelElementosAñadir.add(campoMusculoPrincipal);
        panelElementosAñadir.add(introduceMusculoSecundario);
        panelElementosAñadir.add(campoMusculoSecundario);
        panelElementosAñadir.add(foto);
        panelElementosAñadir.add(botonAñadir);
        panelAnadir.add(panelElementosAñadir, BorderLayout.CENTER);
        
        //-----------------------------------------------------------------------------------
        
        // -------------------------CAMPO ELIMINAR EJERCICIO----------------------------------
        
        JPanel panelElementosEliminar = new JPanel();
        panelElementosEliminar.setLayout(new GridLayout(3, 1, 0, 30));
        panelElementosEliminar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel introduceNombreEliminar = new JLabel("Introduce el nombre del ejercicio:");
        JTextField campoNombreEliminar = new JTextField(10);
        JButton botonEliminar = new JButton("Confirmar");

        // Action listener para el botón Eliminar
        botonEliminar.addActionListener(e -> {
            String texto = campoNombreEliminar.getText().trim();
            if (!texto.isEmpty()) {
                if (!datos.contains(texto)) {
                    JOptionPane.showMessageDialog(this, "El ejercicio no existe en la base de datos.");
                } else {
                    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
                        String sql = "DELETE FROM Ejercicio WHERE Nombre = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, texto);
                        pstmt.executeUpdate();
                        datos.remove(texto); // Actualiza la lista local
                        JOptionPane.showMessageDialog(this, "Ejercicio eliminado correctamente.");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al eliminar el ejercicio de la base de datos.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "El nombre del ejercicio no puede estar vacío.");
            }
        });

        panelElementosEliminar.add(introduceNombreEliminar);
        panelElementosEliminar.add(campoNombreEliminar);
        panelElementosEliminar.add(botonEliminar);

        panelEliminar.add(panelElementosEliminar, BorderLayout.CENTER);

        // Añadimos las partes superiores a los paneles
        panelAnadir.add(anadirSuperior, BorderLayout.NORTH);
        panelEliminar.add(eliminarSuperior, BorderLayout.NORTH);

        // Añadimos los dos paneles al principal
        panelPrincipal.add(panelAnadir);
        panelPrincipal.add(panelEliminar);

        // Detalles finales de la ventana
        add(panelPrincipal);
        setVisible(true);
    }

  //Generado con ChatGPT4
  	protected String getExtension(String filename) {
  		int lastIndex = filename.lastIndexOf('.');	
         return lastIndex == -1 ? "" : filename.substring(lastIndex);
  		
  	}	
    
    // Método para cargar los ejercicios desde la base de datos
    private List<String> cargarEjerciciosDesdeBD() {
        List<String> ejercicios = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
            String sql = "SELECT Nombre FROM Ejercicio";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ejercicios.add(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los ejercicios desde la base de datos.");
        }
        return ejercicios;
    }
}
