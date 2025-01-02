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
    
    @SuppressWarnings("unused")
	private final InterfazRutina interfazRutina;

    public CatalogoEjercicio(String usuario, String nombreRutina, Callback callback, InterfazRutina interfazRutina) {
        this.callback = callback;
        this.interfazRutina = interfazRutina;

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.DARK_GRAY);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(45, 40, 45, 40));

        JButton botonVolver = new JButton("Volver");
        botonVolver.setPreferredSize(new Dimension(140, 10));
        botonVolver.addActionListener(e -> {
            dispose();
            interfazRutina.setVisible(true);
        });
        panelSuperior.add(botonVolver, BorderLayout.WEST);

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
        JDialog dialogoRepeticiones = new JDialog(this, "Repeticiones y Peso - " + ejercicio, true);
        dialogoRepeticiones.setLayout(new GridLayout(numSeries + 1, 3, 10, 10));
        dialogoRepeticiones.setSize(400, 50 + (numSeries * 50));
        dialogoRepeticiones.setLocationRelativeTo(this);
        
        //Los tamaños de los arrays de las etiquetas tienen la misma cantidad que el num de Series
        JLabel[] etiquetasSeries = new JLabel[numSeries];
        JSpinner[] spinnersRepeticiones = new JSpinner[numSeries];
        JLabel[] etiquetasSeries2 = new JLabel[numSeries];
        JSpinner[] spinnersPeso = new JSpinner[numSeries];
        
        
        for (int i = 0; i < numSeries; i++) {
            etiquetasSeries[i] = new JLabel("Serie " + (i + 1) + ":");
            spinnersRepeticiones[i] = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            etiquetasSeries2[i] = new JLabel("Peso " + (i + 1) + ":");
            spinnersPeso[i] = new JSpinner(new SpinnerNumberModel(0, 0, 500, 1)); // Peso inicial 0
            bloquearEdicionSpinner(spinnersRepeticiones[i]);
            bloquearEdicionSpinner(spinnersPeso[i]);

            dialogoRepeticiones.add(etiquetasSeries[i]);
            dialogoRepeticiones.add(spinnersRepeticiones[i]);
            dialogoRepeticiones.add(etiquetasSeries2[i]);
            dialogoRepeticiones.add(spinnersPeso[i]);
        }
        
        //Cuando se le da a aceptar se meten los datos en el mapa 
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(e -> {
        	
        	//Se guardara los datos de las series 
            List<Map<String, Integer>> seriesData = new ArrayList<>();
            
            //Recorre las series
            for (int i = 0; i < numSeries; i++) {
            	
            	//Creo mapa para que se almacenen cada serie 
                Map<String, Integer> datosSerie = new HashMap<>();
                
                //Obtiene el valor ingresado por el usuario en el spinner de repeticiones para la serie i
                datosSerie.put("repeticiones", (Integer) spinnersRepeticiones[i].getValue());
                
                //Obtiene el peso ingresado por el usuario para la serie i
                datosSerie.put("peso", (Integer) spinnersPeso[i].getValue());
                
                seriesData.add(datosSerie);
            }
            if (callback != null) {
                callback.onEjercicioSeleccionado(ejercicio, seriesData);
            }
            
            dialogoRepeticiones.dispose();
            this.dispose();
        });

        dialogoRepeticiones.add(new JLabel());
        dialogoRepeticiones.add(botonAceptar);

        dialogoRepeticiones.setVisible(true);
    }

    private void bloquearEdicionSpinner(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor spinnerEditor) {
            spinnerEditor.getTextField().setEditable(false);
        }
    }
}
