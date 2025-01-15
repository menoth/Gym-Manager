package gui;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class CatalogoEjercicio extends JFrame {

    private static final long serialVersionUID = 1L;
    
    //Cuando el usuario completa la selección (ejercicio, series, repeticiones y peso), se llama al método:
    public interface Callback {
        void onEjercicioSeleccionado(String nombreEjercicio, List<Map<String, Integer>> seriesData);
    }

    private JTextField campoBusqueda;
    private JPanel gridPrincipal;
    private List<String> listaEjercicios;
    private final Callback callback;
    private final String nombreRutina;
    private final String descripcionRutina;
    private final String usuario;
    private static final Color COLOR_PRINCIPAL = new Color(70, 130, 180);
    
    private final InterfazRutina interfazRutina;

    public CatalogoEjercicio(String usuario, String nombreRutina, Callback callback, InterfazRutina interfazRutina) {
    	this.usuario = usuario;
    	this.nombreRutina = nombreRutina;
    	
    	if (interfazRutina != null) {
    	    this.descripcionRutina = interfazRutina.getDescripcionRutina();
    	} else {
    	    this.descripcionRutina = "Sin descripción";
    	}
        this.callback = callback;
        this.interfazRutina = interfazRutina;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(COLOR_PRINCIPAL);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel labelTitulo = new JLabel("Catálogo de Ejercicios", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 34));
        labelTitulo.setForeground(Color.WHITE);
        panelSuperior.add(labelTitulo, BorderLayout.NORTH);

        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setBackground(Color.WHITE);
        botonVolver.setForeground(COLOR_PRINCIPAL);
        botonVolver.setFont(new Font("Serif", Font.BOLD, 17));
        botonVolver.setPreferredSize(new Dimension(180, 10));
        botonVolver.addActionListener(e -> {
            dispose();
            if (interfazRutina != null) {
                interfazRutina.setVisible(true);
            } else {
                System.err.println("Error: InterfazRutina es nulo.");
            }
        });
        panelSuperior.add(botonVolver, BorderLayout.WEST);
        
        JButton botonAñadirEjercicio = new JButton("EDITAR EJERCICIOS");
        botonAñadirEjercicio.setBackground(Color.WHITE);
        botonAñadirEjercicio.setForeground(COLOR_PRINCIPAL);
        botonAñadirEjercicio.setFont(new Font("Serif", Font.BOLD, 14));
        botonAñadirEjercicio.setPreferredSize(new Dimension(180, 10));
        botonAñadirEjercicio.addActionListener(e -> {mostrarDialogoAñadir_EliminarEjercicio(usuario);});
        panelSuperior.add(botonAñadirEjercicio, BorderLayout.EAST);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBusqueda.setBackground(COLOR_PRINCIPAL);

        campoBusqueda = new JTextField("Busca el ejercicio que desees");
		campoBusqueda.setForeground(Color.GRAY); // Color gris claro para el placeholder
        campoBusqueda.setPreferredSize(new Dimension(500, 40));
        campoBusqueda.setFont(new Font("Serif", Font.ITALIC, 17));
        campoBusqueda.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoBusqueda.getText().equals("Busca el ejercicio que desees")) {
                    campoBusqueda.setText(""); // Limpiar el texto
                    campoBusqueda.setFont(new Font("Serif", Font.PLAIN, 23)); // Cambiar a fuente normal
                    campoBusqueda.setForeground(Color.BLACK); // Cambiar a color negro
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campoBusqueda.getText().isEmpty()) {
                    campoBusqueda.setText("Busca el ejercicio que desees"); // Restaurar el placeholder
                    campoBusqueda.setFont(new Font("Serif", Font.ITALIC, 23)); // Regresar a fuente cursiva
                    campoBusqueda.setForeground(Color.GRAY); // Cambiar a color gris
                    actualizarCatalogo(listaEjercicios); // Restaurar catálogo completo
                }
            }
        });

        
        campoBusqueda.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarResultados();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarResultados();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarResultados();
            }

            private void filtrarResultados() {
                String textoBusqueda = campoBusqueda.getText().toLowerCase();
                if (textoBusqueda.isEmpty() || textoBusqueda.equals("busca el ejercicio que desees")) {
                    // Mostrar todos los ejercicios si el campo está vacío o contiene el texto predeterminado
                    actualizarCatalogo(listaEjercicios);
                } else {
                    // Filtrar ejercicios que contengan el texto de búsqueda
                    List<String> ejerciciosFiltrados = new ArrayList<>();
                    for (String ejercicio : listaEjercicios) {
                        if (ejercicio.toLowerCase().contains(textoBusqueda)) {
                            ejerciciosFiltrados.add(ejercicio);
                        }
                    }
                    // Actualizar el catálogo con los resultados filtrados
                    actualizarCatalogo(ejerciciosFiltrados);
                }
            }

        });
        
        panelBusqueda.add(campoBusqueda);
        
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        gridPrincipal = new JPanel(new GridLayout(0, 4, 20, 20));
        gridPrincipal.setBackground(Color.WHITE);
        gridPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        listaEjercicios = cargarEjerciciosDesdeBD();
        actualizarCatalogo(listaEjercicios);

        JScrollPane scrollPane = new JScrollPane(gridPrincipal);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }
    
    private void actualizarCatalogo(List<String> ejercicios) {
        gridPrincipal.removeAll(); // Limpia el catálogo anterior
        
        for (String nombreEjercicio : ejercicios) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createLineBorder(COLOR_PRINCIPAL, 2));

            JButton boton = new JButton(nombreEjercicio);
            boton.setBackground(COLOR_PRINCIPAL);
            boton.setForeground(Color.WHITE);
            boton.setFont(new Font("Serif", Font.BOLD, 14));
            int ancho = (int) (((Toolkit.getDefaultToolkit().getScreenSize().getWidth()) / 4) - 30);
            panel.setPreferredSize(new Dimension(ancho, ancho + 40)); 
            
            // Asocia el método mostrarDialogoSeries
            boton.addActionListener(e -> mostrarDialogoSeries(nombreEjercicio));

            panel.add(boton, BorderLayout.SOUTH);

            JLabel label = new JLabel();
            label.setFont(new Font("Serif", Font.BOLD, 16));
            label.setForeground(COLOR_PRINCIPAL);

            String imagePath = "Sources/imagenes/" + nombreEjercicio.trim() + ".png";
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon originalIcon = new ImageIcon(imagePath);
                Image resizedImage = originalIcon.getImage().getScaledInstance(ancho, ancho, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                label.setIcon(resizedIcon);
            } else {
                label.setText("Imagen no encontrada");
            }

            panel.add(label, BorderLayout.CENTER);
            gridPrincipal.add(panel);
        }

        gridPrincipal.revalidate();
        gridPrincipal.repaint();
    }


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
        }
        return ejercicios;
    }
    
    private void mostrarDialogoAñadir_EliminarEjercicio(String usuario) {
        if (usuario.equals("admin")) {
            JDialog dialogoAñadir_Eliminar = new JDialog(this, "Gestión de Ejercicios", true);
            dialogoAñadir_Eliminar.setLayout(new GridLayout(1, 2, 10, 10));
            dialogoAñadir_Eliminar.setSize(600, 300);
            dialogoAñadir_Eliminar.setLocationRelativeTo(this);

            // Panel Añadir Ejercicio
            JPanel añadirEjercicio = new JPanel();
            añadirEjercicio.setLayout(new BoxLayout(añadirEjercicio, BoxLayout.Y_AXIS));
            añadirEjercicio.setBackground(new Color(70, 130, 180));
            añadirEjercicio.setBorder(BorderFactory.createTitledBorder(
            		BorderFactory.createEtchedBorder(),
                    "AÑADIR EJERCICIO",
                    TitledBorder.CENTER,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Serif", Font.BOLD, 16),
                    Color.WHITE
            ));

            JLabel nombreEjercicio = new JLabel("Nombre:");
            nombreEjercicio.setForeground(Color.WHITE);
            nombreEjercicio.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JTextField añadirNombre = new JTextField();
            añadirNombre.setMaximumSize(new Dimension(600, 30));
            añadirNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel nombreMusculo1 = new JLabel("Músculo Principal:");
            nombreMusculo1.setForeground(Color.WHITE);
            nombreMusculo1.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JComboBox<String> comboMusculo1 = new JComboBox<>();
            comboMusculo1.setAlignmentX(Component.LEFT_ALIGNMENT);
            List<String> listaMusculos = cargarMusculosDesdeBD();
            for (String musculo : listaMusculos) {
                comboMusculo1.addItem(musculo);
            }
            comboMusculo1.setMaximumSize(new Dimension(200, 30));

            JLabel nombreMusculo2 = new JLabel("Músculo Secundario:");
            nombreMusculo2.setForeground(Color.WHITE);
            nombreMusculo2.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JComboBox<String> comboMusculo2 = new JComboBox<>();
            for (String musculo : listaMusculos) {
                comboMusculo2.addItem(musculo);
            }
            comboMusculo2.setMaximumSize(new Dimension(200, 30));
            comboMusculo2.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton añadirFoto = new JButton("AÑADIR FOTO (PNG)");
            añadirFoto.setEnabled(false); // Deshabilitado inicialmente

            final File[] fotoSeleccionada = {null}; // Variable para almacenar temporalmente la imagen seleccionada

            // Habilitar o deshabilitar el botón "AÑADIR FOTO" según el campo "Nombre"
            añadirNombre.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    toggleAñadirFotoButton();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    toggleAñadirFotoButton();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    toggleAñadirFotoButton();
                }

                private void toggleAñadirFotoButton() {
                    añadirFoto.setEnabled(!añadirNombre.getText().trim().isEmpty());
                }
            });

            // Listener del botón "AÑADIR FOTO"
            añadirFoto.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Imágenes (JPG, PNG, GIF)", "jpg", "png", "gif"));

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    fotoSeleccionada[0] = fileChooser.getSelectedFile(); // Almacenar temporalmente la imagen seleccionada
                    JOptionPane.showMessageDialog(
                        null,
                        "Imagen seleccionada correctamente.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            });

            JButton botonAñadir = new JButton("AÑADIR EJERCICIO");
            botonAñadir.setBackground(Color.WHITE);
            botonAñadir.setForeground(new Color(70, 130, 180));
            botonAñadir.setFont(new Font("Arial", Font.BOLD, 14));
            botonAñadir.addActionListener(e -> {
                String nombre = añadirNombre.getText();
                String musculo1 = (String) comboMusculo1.getSelectedItem();
                String musculo2 = (String) comboMusculo2.getSelectedItem();

                if (musculo1 != null && musculo2 != null && musculo1.equals(musculo2)) {
                    JOptionPane.showMessageDialog(
                        CatalogoEjercicio.this,
                        "No es posible seleccionar el mismo músculo principal y secundario.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                if (!nombre.isEmpty() && musculo1 != null && musculo2 != null) {
                    try {
                        int nuevoId = listaEjercicios.size() + 1;

                        // Guardar la imagen si se seleccionó
                        if (fotoSeleccionada[0] != null) {
                            String nuevoNombreFoto = nombre.trim() + getExtension(fotoSeleccionada[0].getName());
                            File destino = new File("Sources/imagenes/" + nuevoNombreFoto);
                            Files.copy(fotoSeleccionada[0].toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }

                        InsertarDatosBD.insertarEjercicio(nuevoId, nombre, musculo1, musculo2);
                        listaEjercicios = cargarEjerciciosDesdeBD();
                        actualizarCatalogo(listaEjercicios);

                        JOptionPane.showMessageDialog(
                            CatalogoEjercicio.this,
                            "Ejercicio añadido correctamente.",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        JOptionPane.showMessageDialog(
                            CatalogoEjercicio.this,
                            "Error al guardar la imagen.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(
                            CatalogoEjercicio.this,
                            "Error al añadir el ejercicio.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                        CatalogoEjercicio.this,
                        "Todos los campos deben estar llenos.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            });

            añadirEjercicio.add(nombreEjercicio);
            añadirEjercicio.add(añadirNombre);
            añadirEjercicio.add(Box.createVerticalStrut(10));
            añadirEjercicio.add(nombreMusculo1);
            añadirEjercicio.add(comboMusculo1);
            añadirEjercicio.add(Box.createVerticalStrut(10));
            añadirEjercicio.add(nombreMusculo2);
            añadirEjercicio.add(comboMusculo2);
            añadirEjercicio.add(añadirFoto);
            añadirEjercicio.add(Box.createVerticalStrut(10));
            añadirEjercicio.add(botonAñadir);

            dialogoAñadir_Eliminar.add(añadirEjercicio);

            // Panel Eliminar Ejercicio (sin cambios)
            JPanel eliminarEjercicio = new JPanel();
            eliminarEjercicio.setBackground(new Color(70, 130, 180));
            eliminarEjercicio.setLayout(new BoxLayout(eliminarEjercicio, BoxLayout.Y_AXIS));
            eliminarEjercicio.setBorder(BorderFactory.createTitledBorder(
            		BorderFactory.createEtchedBorder(),
                    "ELIMINAR EJERCICIO",
                    TitledBorder.CENTER,
                    TitledBorder.DEFAULT_POSITION,
                    new Font("Serif", Font.BOLD, 16),
                    Color.WHITE
            ));

            JLabel nombreEjercicioEliminar = new JLabel("Nombre:");
            nombreEjercicioEliminar.setForeground(Color.WHITE);
            JTextField añadirEjercicioEliminar = new JTextField();
            añadirEjercicioEliminar.setMaximumSize(new Dimension(600, 30));

            JButton botonEliminar = new JButton("ELIMINAR EJERCICIO");
            botonEliminar.setBackground(Color.WHITE);
            botonEliminar.setForeground(new Color(70, 130, 180));
            botonEliminar.setFont(new Font("Arial", Font.BOLD, 14));
            botonEliminar.addActionListener(e -> {
                String nombre = añadirEjercicioEliminar.getText();
                if (!nombre.isEmpty()) {
                	eliminarFotoEjercicio(nombre);
                    eliminarEjercicioDeBaseDeDatos(nombre);
                    dialogoAñadir_Eliminar.dispose();
                } else {
                    JOptionPane.showMessageDialog(
                        CatalogoEjercicio.this,
                        "El campo no puede estar vacío.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            });

            eliminarEjercicio.add(nombreEjercicioEliminar);
            eliminarEjercicio.add(añadirEjercicioEliminar);
            eliminarEjercicio.add(Box.createVerticalStrut(10));
            eliminarEjercicio.add(botonEliminar);

            dialogoAñadir_Eliminar.add(eliminarEjercicio);

            dialogoAñadir_Eliminar.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Acceso restringido. Solo administradores pueden editar ejercicios",
                "Información",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }



    private void eliminarEjercicioDeBaseDeDatos(String nombre) {
        String sql = "DELETE FROM Ejercicio WHERE Nombre = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Ejercicio eliminado correctamente.");
                listaEjercicios = cargarEjerciciosDesdeBD();
                actualizarCatalogo(listaEjercicios);
            } else {
                JOptionPane.showMessageDialog(this, "El ejercicio no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el ejercicio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void mostrarDialogoSeries(String ejercicio) {
        JDialog dialogoSeries = new JDialog(this, "Número de Series - " + ejercicio, true);
        dialogoSeries.setSize(400, 200);
        dialogoSeries.setLocationRelativeTo(this);

        // Configuración del panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(new Color(70, 130, 180)); // Fondo principal
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta de título
        JLabel labelTitulo = new JLabel("Elige la cantidad de series");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Spinner para seleccionar series
        JSpinner spinnerSeries = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        spinnerSeries.setFont(new Font("Arial", Font.PLAIN, 16));
        bloquearEdicionSpinner(spinnerSeries);
        spinnerSeries.setMaximumSize(new Dimension(100, 30));
        spinnerSeries.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(70, 130, 180));
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setFont(new Font("Arial", Font.BOLD, 14));
        botonAceptar.setBackground(Color.WHITE);
        botonAceptar.setForeground(new Color(70, 130, 180));
        botonAceptar.addActionListener(e -> {
            int numSeries = (int) spinnerSeries.getValue();
            dialogoSeries.dispose();
            mostrarDialogoRepeticiones(numSeries, ejercicio);
        });

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        botonCancelar.setBackground(Color.WHITE);
        botonCancelar.setForeground(new Color(70, 130, 180));
        botonCancelar.addActionListener(e -> dialogoSeries.dispose());
        
        panelBotones.add(botonCancelar);
        panelBotones.add(botonAceptar);
        

        // Agregar componentes al panel principal
        panelPrincipal.add(labelTitulo);
        panelPrincipal.add(Box.createVerticalStrut(20)); // Espaciado
        panelPrincipal.add(spinnerSeries);
        panelPrincipal.add(Box.createVerticalStrut(20)); // Espaciado
        panelPrincipal.add(panelBotones);

        dialogoSeries.add(panelPrincipal);
        dialogoSeries.setVisible(true);
    }

    private void mostrarDialogoRepeticiones(int numSeries, String ejercicio) {
        JDialog dialogoRepeticiones = new JDialog(this, "Repeticiones, Peso y Esfuerzo - " + ejercicio, true);
        dialogoRepeticiones.setLayout(new GridBagLayout());
        dialogoRepeticiones.setSize(700, 100 + (numSeries * 50));
        dialogoRepeticiones.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margen entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel[] etiquetasSeries = new JLabel[numSeries];
        JSpinner[] spinnersRepeticiones = new JSpinner[numSeries];
        JLabel[] etiquetasPeso = new JLabel[numSeries];
        JSpinner[] spinnersPeso = new JSpinner[numSeries];
        JLabel[] etiquetasEsfuerzo = new JLabel[numSeries];
        ButtonGroup[] gruposEsfuerzo = new ButtonGroup[numSeries];
        JRadioButton[][] botonesEsfuerzo = new JRadioButton[numSeries][3]; // Para "Bajo", "Medio", "Alto"

        for (int i = 0; i < numSeries; i++) {
            // Etiqueta Serie
            etiquetasSeries[i] = new JLabel("Serie " + (i + 1) + " - Repeticiones:");
            etiquetasSeries[i].setForeground(Color.WHITE); // Texto blanco
            gbc.gridx = 0; gbc.gridy = i;
            dialogoRepeticiones.add(etiquetasSeries[i], gbc);

            // Spinner Repeticiones
            spinnersRepeticiones[i] = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            spinnersRepeticiones[i].setFont(new Font("Arial", Font.PLAIN, 14));
            bloquearEdicionSpinner(spinnersRepeticiones[i]);
            gbc.gridx = 1;
            dialogoRepeticiones.add(spinnersRepeticiones[i], gbc);

            // Etiqueta Peso
            etiquetasPeso[i] = new JLabel("Peso:");
            etiquetasPeso[i].setForeground(Color.WHITE); // Texto blanco
            gbc.gridx = 2;
            dialogoRepeticiones.add(etiquetasPeso[i], gbc);

            // Spinner Peso
            spinnersPeso[i] = new JSpinner(new SpinnerNumberModel(0, 0, 500, 1));
            spinnersPeso[i].setFont(new Font("Arial", Font.PLAIN, 14));
            bloquearEdicionSpinner(spinnersPeso[i]);
            gbc.gridx = 3;
            dialogoRepeticiones.add(spinnersPeso[i], gbc);

            // Etiqueta Esfuerzo
            etiquetasEsfuerzo[i] = new JLabel("Esfuerzo:");
            etiquetasEsfuerzo[i].setForeground(Color.WHITE); // Texto blanco
            gbc.gridx = 4;
            dialogoRepeticiones.add(etiquetasEsfuerzo[i], gbc);

            // Botones de Esfuerzo
            gruposEsfuerzo[i] = new ButtonGroup();
            JPanel panelEsfuerzo = new JPanel(new GridLayout(1, 3));
            panelEsfuerzo.setBackground(new Color(70, 130, 180)); // Fondo principal
            botonesEsfuerzo[i][0] = new JRadioButton("Aprox.(W)");
            botonesEsfuerzo[i][1] = new JRadioButton("Estándar(E)");
            botonesEsfuerzo[i][2] = new JRadioButton("Topset(T)");

            for (JRadioButton boton : botonesEsfuerzo[i]) {
                boton.setForeground(Color.WHITE); // Texto blanco
                boton.setBackground(new Color(70, 130, 180)); // Fondo principal
                gruposEsfuerzo[i].add(boton);
                panelEsfuerzo.add(boton);
            }
            botonesEsfuerzo[i][1].setSelected(true); // Selecciona "Medio" por defecto
            gbc.gridx = 5;
            dialogoRepeticiones.add(panelEsfuerzo, gbc);
        }

        // Botón Aceptar
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.setFont(new Font("Arial", Font.BOLD, 14));
        botonAceptar.setBackground(Color.WHITE); // Fondo blanco
        botonAceptar.setForeground(new Color(70, 130, 180)); // Texto azul

        botonAceptar.addActionListener(e -> {
            List<Map<String, Integer>> seriesData = new ArrayList<>();

            for (int i = 0; i < numSeries; i++) {
                Map<String, Integer> datosSerie = new HashMap<>();
                datosSerie.put("repeticiones", (Integer) spinnersRepeticiones[i].getValue());
                datosSerie.put("peso", (Integer) spinnersPeso[i].getValue());

                // Obtener esfuerzo seleccionado como un entero
                int esfuerzo = 0;
                if (botonesEsfuerzo[i][0].isSelected()) esfuerzo = 1; // Bajo
                else if (botonesEsfuerzo[i][1].isSelected()) esfuerzo = 2; // Medio
                else if (botonesEsfuerzo[i][2].isSelected()) esfuerzo = 3; // Alto

                datosSerie.put("esfuerzo", esfuerzo);
                seriesData.add(datosSerie);
            }

            if (callback != null) {
                callback.onEjercicioSeleccionado(ejercicio, seriesData);
            }
            
            if (interfazRutina != null) {
                interfazRutina.setVisible(true);
            } else {
                System.err.println("Error: InterfazRutina es nulo.");
            }
            
            if (interfazRutina == null) {
                InterfazRutina nuevaInterfaz = new InterfazRutina(nombreRutina, descripcionRutina, usuario);
                nuevaInterfaz.setVisible(true);
            } else {
                interfazRutina.setVisible(true);
            }

            dialogoRepeticiones.dispose();
            this.dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = numSeries;
        gbc.gridwidth = 6;
        dialogoRepeticiones.add(botonAceptar, gbc);

        // Cambiar fondo del diálogo
        dialogoRepeticiones.getContentPane().setBackground(new Color(70, 130, 180)); // Fondo principal

        dialogoRepeticiones.setVisible(true);
    }





    private void bloquearEdicionSpinner(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor spinnerEditor) {
            spinnerEditor.getTextField().setEditable(false);
        }
    }	
    
  //Generado con ChatGPT4
  	protected String getExtension(String filename) {
  		int lastIndex = filename.lastIndexOf('.');	
         return lastIndex == -1 ? "" : filename.substring(lastIndex);
  		
  	}
  	
  	private void eliminarFotoEjercicio(String nombreEjercicio) {
  	    File carpetaImagenes = new File("Sources/imagenes");
  	    if (!carpetaImagenes.exists()) {
  	        JOptionPane.showMessageDialog(
  	            null,
  	            "La carpeta de imágenes no existe.",
  	            "Error",
  	            JOptionPane.ERROR_MESSAGE
  	        );
  	        return;
  	    }


  	    File[] archivos = carpetaImagenes.listFiles((dir, name) -> name.startsWith(nombreEjercicio));
  	    if (archivos != null && archivos.length > 0) {
  	        for (File archivo : archivos) {
  	            if (archivo.delete()) {
  	                JOptionPane.showMessageDialog(
  	                    null,
  	                    "La imagen del ejercicio '" + nombreEjercicio + "' se ha eliminado correctamente.",
  	                    "Información",
  	                    JOptionPane.INFORMATION_MESSAGE
  	                );
  	            } else {
  	                JOptionPane.showMessageDialog(
  	                    null,
  	                    JOptionPane.ERROR_MESSAGE
  	                );
  	            }
  	        }
  	    } else {
  	        JOptionPane.showMessageDialog(
  	            null,
  	            JOptionPane.WARNING_MESSAGE
  	        );
  	    }
  	}

    private List<String> cargarMusculosDesdeBD() {
        List<String> musculos = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
            String sql = "SELECT Nombre FROM Musculo";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                musculos.add(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musculos;
    }

}
