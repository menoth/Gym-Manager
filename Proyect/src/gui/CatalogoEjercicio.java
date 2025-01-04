package gui;

import java.awt.*;
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
        panelSuperior.setBackground(Color.DARK_GRAY);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(45, 40, 45, 40));

        JButton botonVolver = new JButton("Volver");
        botonVolver.setPreferredSize(new Dimension(200, 10));
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
        botonAñadirEjercicio.setPreferredSize(new Dimension(200, 10));
        botonAñadirEjercicio.addActionListener(e -> {mostrarDialogoAñadir_EliminarEjercicio(usuario);});
        panelSuperior.add(botonAñadirEjercicio, BorderLayout.EAST);

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(Color.DARK_GRAY);
        campoBusqueda = new JTextField();
        campoBusqueda.setPreferredSize(new Dimension(500, 30));
        
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setPreferredSize(new Dimension(140, 40));
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        gridPrincipal = new JPanel(new GridLayout(0, 4, 20, 20));
        gridPrincipal.setBackground(Color.LIGHT_GRAY);
        gridPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        listaEjercicios = cargarEjerciciosDesdeBD();
        actualizarCatalogo(listaEjercicios);

        JScrollPane scrollPane = new JScrollPane(gridPrincipal);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
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

    private void actualizarCatalogo(List<String> ejercicios) {
        gridPrincipal.removeAll();
        for (String nombreEjercicio : ejercicios) {
            JButton boton = new JButton(nombreEjercicio);
            boton.addActionListener(e -> mostrarDialogoSeries(nombreEjercicio));
            gridPrincipal.add(boton);
        }
        gridPrincipal.revalidate();
        gridPrincipal.repaint();
    }
    
    private void mostrarDialogoAñadir_EliminarEjercicio(String usuario) {
        if (usuario.equals("admin")) {
            JDialog dialogoAñadir_Eliminar = new JDialog(this, "Gestión de Ejercicios", true);
            dialogoAñadir_Eliminar.setLayout(new GridLayout(1, 2, 10, 10));
            dialogoAñadir_Eliminar.setSize(600, 300);
            dialogoAñadir_Eliminar.setLocationRelativeTo(null);

            // Panel Añadir Ejercicio
            JPanel añadirEjercicio = new JPanel();
            añadirEjercicio.setLayout(new BoxLayout(añadirEjercicio, BoxLayout.Y_AXIS));
            añadirEjercicio.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "AÑADIR EJERCICIO",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ));

            JLabel nombreEjercicio = new JLabel("Nombre:");
            nombreEjercicio.setAlignmentX(Component.LEFT_ALIGNMENT);
            JTextField añadirNombre = new JTextField();
            añadirNombre.setMaximumSize(new Dimension(600, 30));
            añadirNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel nombreMusculo1 = new JLabel("Músculo Principal:");
            nombreMusculo1.setAlignmentX(Component.LEFT_ALIGNMENT);
            JComboBox<String> comboMusculo1 = new JComboBox<>();
            comboMusculo1.setAlignmentX(Component.LEFT_ALIGNMENT);
            List<String> listaMusculos = cargarMusculosDesdeBD();
            for (String musculo : listaMusculos) {
                comboMusculo1.addItem(musculo);
            }
            comboMusculo1.setMaximumSize(new Dimension(200, 30)); // Reducir tamaño del JComboBox

            JLabel nombreMusculo2 = new JLabel("Músculo Secundario:");
            nombreMusculo2.setAlignmentX(Component.LEFT_ALIGNMENT);
            JComboBox<String> comboMusculo2 = new JComboBox<>();
            for (String musculo : listaMusculos) {
                comboMusculo2.addItem(musculo);
            }
            comboMusculo2.setMaximumSize(new Dimension(200, 30));
            comboMusculo2.setAlignmentX(Component.LEFT_ALIGNMENT);// Reducir tamaño del JComboBox

            JButton botonAñadir = new JButton("AÑADIR EJERCICIO");
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
                        InsertarDatosBD.insertarEjercicio(nuevoId, nombre, musculo1, musculo2);

                        listaEjercicios = cargarEjerciciosDesdeBD();
                        actualizarCatalogo(listaEjercicios);

                        JOptionPane.showMessageDialog(
                            CatalogoEjercicio.this,
                            "Ejercicio añadido correctamente.",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE
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
            añadirEjercicio.add(Box.createVerticalStrut(10)); // Espaciado
            añadirEjercicio.add(nombreMusculo1);
            añadirEjercicio.add(comboMusculo1);
            añadirEjercicio.add(Box.createVerticalStrut(10)); // Espaciado
            añadirEjercicio.add(nombreMusculo2);
            añadirEjercicio.add(comboMusculo2);
            añadirEjercicio.add(Box.createVerticalStrut(10)); // Espaciado
            añadirEjercicio.add(botonAñadir);

            dialogoAñadir_Eliminar.add(añadirEjercicio);

            // Panel Eliminar Ejercicio
            JPanel eliminarEjercicio = new JPanel();
            eliminarEjercicio.setLayout(new BoxLayout(eliminarEjercicio, BoxLayout.Y_AXIS));
            eliminarEjercicio.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "ELIMINAR EJERCICIO",
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION
            ));

            JLabel nombreEjercicioEliminar = new JLabel("Nombre:");
            JTextField añadirEjercicioEliminar = new JTextField();
            añadirEjercicioEliminar.setMaximumSize(new Dimension(600, 30)); // Tamaño igual al de añadir

            JButton botonEliminar = new JButton("ELIMINAR EJERCICIO");
            botonEliminar.addActionListener(e -> {
                String nombre = añadirEjercicioEliminar.getText();
                if (!nombre.isEmpty()) {
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
            eliminarEjercicio.add(Box.createVerticalStrut(10)); // Espaciado
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
        dialogoSeries.setLayout(new GridLayout(2, 2, 10, 10));
        dialogoSeries.setSize(300, 150);
        dialogoSeries.setLocationRelativeTo(this);

        JLabel labelSeries = new JLabel("Número de series:");
        JSpinner spinnerSeries = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        bloquearEdicionSpinner(spinnerSeries);

        JButton botonAceptar = new JButton("Aceptar");
        JButton botonCancelar = new JButton("Cancelar");

        botonAceptar.addActionListener(e -> {
            int numSeries = (int) spinnerSeries.getValue();
            dialogoSeries.dispose();
            mostrarDialogoRepeticiones(numSeries, ejercicio);
        });

        botonCancelar.addActionListener(e -> dialogoSeries.dispose());

        dialogoSeries.add(labelSeries);
        dialogoSeries.add(spinnerSeries);
        dialogoSeries.add(botonAceptar);
        dialogoSeries.add(botonCancelar);

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
            gbc.gridx = 0; gbc.gridy = i;
            dialogoRepeticiones.add(etiquetasSeries[i], gbc);

            // Spinner Repeticiones
            spinnersRepeticiones[i] = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            bloquearEdicionSpinner(spinnersRepeticiones[i]);
            gbc.gridx = 1;
            dialogoRepeticiones.add(spinnersRepeticiones[i], gbc);

            // Etiqueta Peso
            etiquetasPeso[i] = new JLabel("Peso:");
            gbc.gridx = 2;
            dialogoRepeticiones.add(etiquetasPeso[i], gbc);

            // Spinner Peso
            spinnersPeso[i] = new JSpinner(new SpinnerNumberModel(0, 0, 500, 1));
            bloquearEdicionSpinner(spinnersPeso[i]);
            gbc.gridx = 3;
            dialogoRepeticiones.add(spinnersPeso[i], gbc);

            // Etiqueta Esfuerzo
            etiquetasEsfuerzo[i] = new JLabel("Esfuerzo:");
            gbc.gridx = 4;
            dialogoRepeticiones.add(etiquetasEsfuerzo[i], gbc);

            // Botones de Esfuerzo
            gruposEsfuerzo[i] = new ButtonGroup();
            JPanel panelEsfuerzo = new JPanel(new GridLayout(1, 3));
            botonesEsfuerzo[i][0] = new JRadioButton("Aprox.(W)");
            botonesEsfuerzo[i][1] = new JRadioButton("Estándar(E)");
            botonesEsfuerzo[i][2] = new JRadioButton("Topset(T)");

            for (JRadioButton boton : botonesEsfuerzo[i]) {
                gruposEsfuerzo[i].add(boton);
                panelEsfuerzo.add(boton);
            }
            botonesEsfuerzo[i][1].setSelected(true); // Selecciona "Medio" por defecto
            gbc.gridx = 5;
            dialogoRepeticiones.add(panelEsfuerzo, gbc);
        }

        // Botón Aceptar
        JButton botonAceptar = new JButton("Aceptar");
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

        gbc.gridx = 0; gbc.gridy = numSeries; gbc.gridwidth = 6;
        dialogoRepeticiones.add(botonAceptar, gbc);

        dialogoRepeticiones.setVisible(true);
    }




    private void bloquearEdicionSpinner(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor spinnerEditor) {
            spinnerEditor.getTextField().setEditable(false);
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
