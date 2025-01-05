package gui;

import domain.Rutina;
import domain.Serie;
import domain.Entrenamiento;
import domain.EjercicioEnEntrenamiento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InterfazRutinaExpandida extends JFrame {
    private static final long serialVersionUID = 1L;

    public InterfazRutinaExpandida(Rutina rutina, String usuario) {
        setTitle("Detalle de Rutina - " + rutina.getNombre());
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Establecer color de fondo de la ventana principal
        getContentPane().setBackground(new Color(70, 130, 180));
        setLayout(new BorderLayout()); // Usar BorderLayout para manejar mejor la posición del botón

        // Botón "Cerrar" en la esquina superior izquierda
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Alinear a la izquierda
        topPanel.setBackground(new Color(70, 130, 180)); // Fondo igual al resto de la interfaz

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBackground(new Color(220, 20, 60)); // Rojo para destacar
        btnCerrar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Márgenes pequeños
        btnCerrar.setFocusPainted(false);

        // Acción para cerrar la ventana
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        topPanel.add(btnCerrar);
        add(topPanel, BorderLayout.NORTH); // Agregar el panel superior al borde norte

        // Panel principal con diseño horizontal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, rutina.getEntrenamientos().size(), 10, 10));
        mainPanel.setBackground(new Color(70, 130, 180));

        // Configuración de fuentes personalizadas
        Font titleFont = new Font("Arial", Font.BOLD, 16);
        Font bodyFont = new Font("Arial", Font.PLAIN, 14);

        for (Entrenamiento entrenamiento : rutina.getEntrenamientos()) {
            // Panel por día
            JPanel panelDia = new JPanel();
            panelDia.setLayout(new BoxLayout(panelDia, BoxLayout.Y_AXIS));
            panelDia.setBackground(new Color(70, 130, 180));
            panelDia.setBorder(new EmptyBorder(15, 15, 15, 15));

            // Título del día
            JLabel lblDia = new JLabel("Día: " + entrenamiento.getDía());
            lblDia.setFont(titleFont);
            lblDia.setForeground(Color.WHITE);
            panelDia.add(lblDia);

            // Título del entrenamiento
            JLabel lblNombreEntrenamiento = new JLabel("Entrenamiento: " + entrenamiento.getNombre());
            lblNombreEntrenamiento.setFont(bodyFont);
            lblNombreEntrenamiento.setForeground(Color.WHITE);
            panelDia.add(lblNombreEntrenamiento);

            panelDia.add(Box.createRigidArea(new Dimension(0, 10)));

            // Listar ejercicios y sus series
            for (EjercicioEnEntrenamiento ejercicio : entrenamiento.getEjercicios()) {
                // Ejercicio
                JLabel lblNombreEjercicio = new JLabel("• " + nombreEjercicio(ejercicio.getID_Ejercicio()));
                lblNombreEjercicio.setFont(bodyFont);
                lblNombreEjercicio.setForeground(Color.WHITE);
                panelDia.add(lblNombreEjercicio);

                // Detalles de las series
                JPanel seriesPanel = new JPanel();
                seriesPanel.setLayout(new BoxLayout(seriesPanel, BoxLayout.Y_AXIS));
                seriesPanel.setBackground(new Color(100, 149, 237));
                seriesPanel.setBorder(new EmptyBorder(5, 15, 5, 15));

                for (Serie serie : ejercicio.getSeries()) {
                    JLabel lblSerieDetalle = new JLabel("   - Repeticiones: " + serie.getRepeticiones() +
                                                        ", Peso: " + serie.getPeso() + " kg" +
                                                        ", RPE: " + serie.getEsfuerzo());
                    lblSerieDetalle.setFont(bodyFont);
                    lblSerieDetalle.setForeground(Color.WHITE);
                    seriesPanel.add(lblSerieDetalle);
                }

                panelDia.add(seriesPanel);
                panelDia.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            mainPanel.add(panelDia);
        }

        // Agregar desplazamiento horizontal y vertical
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER); // Agregar el panel principal al centro
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
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");
            String sql = "SELECT Nombre FROM Ejercicio WHERE ID_Ejercicio = ?";
            PreparedStatement queryStmt = conn.prepareStatement(sql);
            queryStmt.setInt(1, id);
            ResultSet resultado = queryStmt.executeQuery();
            nombre = resultado.getString("Nombre");
            queryStmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }
}
