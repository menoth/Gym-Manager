package gui;

import domain.Rutina;
import domain.Serie;
import domain.Entrenamiento;
import domain.Ejercicio;
import domain.EjercicioEnEntrenamiento;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class InterfazRutinaExpandida extends JFrame {
    private static final long serialVersionUID = 1L;

    public InterfazRutinaExpandida(Rutina rutina, String usuario) {
        setTitle("Detalle de Rutina - " + rutina.getNombre());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 3)); // Configurar para tres paneles verticales
        setSize(1000, 600);

        // Panel 1: Día y nombre del entrenamiento
        JPanel panelEntrenamiento = new JPanel();
        panelEntrenamiento.setLayout(new BoxLayout(panelEntrenamiento, BoxLayout.Y_AXIS));
        panelEntrenamiento.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir borde

        for (Entrenamiento entrenamiento : rutina.getEntrenamientos()) {
            JLabel lblDia = new JLabel("Día: " + entrenamiento.getDía());
            JLabel lblNombreEntrenamiento = new JLabel("Entrenamiento: " + entrenamiento.getNombre());

            panelEntrenamiento.add(lblDia);
            panelEntrenamiento.add(lblNombreEntrenamiento);
            panelEntrenamiento.add(Box.createRigidArea(new Dimension(0, 20))); // Separador visual
        }

        JScrollPane scrollPanelEntrenamiento = new JScrollPane(panelEntrenamiento); // Añadir JScrollPane

        // Panel 2: Nombres de ejercicios
        JPanel panelEjercicios = new JPanel();
        panelEjercicios.setLayout(new BoxLayout(panelEjercicios, BoxLayout.Y_AXIS));
        panelEjercicios.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir borde

        for (Entrenamiento entrenamiento : rutina.getEntrenamientos()) {
            JLabel lblTituloEjercicios = new JLabel("Ejercicios para " + entrenamiento.getNombre() + ":");
            panelEjercicios.add(lblTituloEjercicios);

            for (EjercicioEnEntrenamiento ejercicio : entrenamiento.getEjercicios()) {
                JLabel lblNombreEjercicio = new JLabel(nombreEjercicio(ejercicio.getID_Ejercicio()));
                panelEjercicios.add(lblNombreEjercicio);
            }

            panelEjercicios.add(Box.createRigidArea(new Dimension(0, 20))); // Separador visual
        }

        JScrollPane scrollPanelEjercicios = new JScrollPane(panelEjercicios); // Añadir JScrollPane

        // Panel 3: Detalles de ejercicios
        JPanel panelDetalles = new JPanel();
        panelDetalles.setLayout(new BoxLayout(panelDetalles, BoxLayout.Y_AXIS));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir borde

        for (Entrenamiento entrenamiento : rutina.getEntrenamientos()) {
            for (EjercicioEnEntrenamiento ejercicio : entrenamiento.getEjercicios()) {
                JLabel lblDetalleEjercicio = new JLabel("Ejercicio: " + nombreEjercicio(ejercicio.getID_Ejercicio()));
                JLabel lblSeries = new JLabel("Series: " + ejercicio.getSeries().size());
                panelDetalles.add(lblDetalleEjercicio);
                panelDetalles.add(lblSeries);
                for (Serie serie : ejercicio.getSeries()) {
                    JLabel lblRepeticiones = new JLabel("Repeticiones: " + serie.getRepeticiones());
                    JLabel lblPeso = new JLabel("Peso: " + serie.getPeso() + " kg");
                    panelDetalles.add(lblRepeticiones);
                    panelDetalles.add(lblPeso);
                }

                panelDetalles.add(Box.createRigidArea(new Dimension(0, 20))); // Separador visual
            }
        }

        JScrollPane scrollPanelDetalles = new JScrollPane(panelDetalles); // Añadir JScrollPane

        // Agregar paneles al frame dentro de JScrollPane
        add(scrollPanelEntrenamiento);
        add(scrollPanelEjercicios);
        add(scrollPanelDetalles);

        // Configuración final
        setVisible(true);
    }

    protected String nombreEjercicio(int id) {
        String nombre = "";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha podido cargar el driver de la BD");
        }
        try {
            Connection conn = DriverManager.getConnection
                ("jdbc:sqlite:Sources/bd/baseDeDatos.db");

            Statement stmt = conn.createStatement();
            String sql = "SELECT Nombre FROM Ejercicio WHERE ID_Ejercicio = ?";
            PreparedStatement queryStmt = conn.prepareStatement(sql);
            queryStmt.setInt(1, id);

            ResultSet resultado = queryStmt.executeQuery();
            nombre = resultado.getString("Nombre");

            queryStmt.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }
}
