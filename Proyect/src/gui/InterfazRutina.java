package gui;

import domain.EditorBoton;

import domain.ModeloJTable;
import domain.RendererTabla;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterfazRutina extends JFrame {

    private static final long serialVersionUID = 1L;
    private List<String> listaRutina;
    private String descripcionRutina;
    private JTable tabla;

    public InterfazRutina(String nombreRutina, String descripcionRutina, String usuario) {
    	this.descripcionRutina = descripcionRutina;
    	
        setTitle("Rutina - " + nombreRutina);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        ModeloJTable modelo = new ModeloJTable();

        tabla = new JTable(modelo);
        tabla.setRowHeight(40);

        RendererTabla rendererTabla = new RendererTabla(usuario, nombreRutina);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(rendererTabla);
            tabla.getColumnModel().getColumn(i).setCellEditor(new EditorBoton(usuario, nombreRutina, tabla, this));
        }

        modelo.addTableModelListener(e -> SwingUtilities.invokeLater(() -> {
            for (int row = 0; row < tabla.getRowCount(); row++) {
                for (int column = 0; column < tabla.getColumnCount(); column++) {
                    Component comp = tabla.prepareRenderer(tabla.getCellRenderer(row, column), row, column);
                    int preferredHeight = comp.getPreferredSize().height;
                    tabla.setRowHeight(row, Math.max(tabla.getRowHeight(row), preferredHeight));
                }
            }
        }));

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Panel Inferior para los botones y el nombre de la rutina
        JPanel panelInferior = new JPanel(new BorderLayout());

        // Sub-panel para los botones (centrados)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> guardarRutinaEnBD(nombreRutina, descripcionRutina, usuario));

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(e -> confirmarSalida(usuario, nombreRutina));

        panelBotones.add(botonCancelar);
        panelBotones.add(botonGuardar);

        panelInferior.add(panelBotones, BorderLayout.CENTER);

        // Sub-panel para el nombre de la rutina (a la izquierda)
        JPanel panelNombreRutina = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelRutina = new JLabel("Nombre de la rutina: " + nombreRutina);
        panelNombreRutina.add(labelRutina);

        panelInferior.add(panelNombreRutina, BorderLayout.WEST);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    public String getDescripcionRutina() {
        return descripcionRutina;
    }
    
    public void actualizarDatosRutina(Map<String, Object> datos) {
        ModeloJTable modelo = (ModeloJTable) tabla.getModel();
        modelo.agregarDatos(datos);
        tabla.revalidate();
        tabla.repaint();
    }


    private void confirmarSalida(String usuario, String nombreRutina) {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar " + nombreRutina + "? Se perderá todo lo hecho hasta ahora...",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
            new PrincipalWindow(usuario);
        }
    }
    
    
    private void guardarRutinaEnBD(String nombreRutina, String descripcionRutina, String usuario) {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Se ha cargado el driver correctamente.");
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha podido cargar el driver de la BD");
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");
            System.out.println("Se ha conectado a la base de datos.");
            String sql = "INSERT INTO Rutina (ID_Rutina, Nombre, Descripción, Usuario) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Asignar un nuevo ID basado en el tamaño de la lista de rutinas
            listaRutina = cargarRutinasDesdeBD();
            int idRutina = listaRutina.size() + 1;

            pstmt.setInt(1, idRutina);
            pstmt.setString(2, nombreRutina);
            pstmt.setString(3, descripcionRutina);
            pstmt.setString(4, usuario);

            // Ejecutar la consulta
            pstmt.executeUpdate();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Rutina '" + nombreRutina + "' guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            System.out.println(nombreRutina + " insertado correctamente. Verifica la BD.");
            pstmt.close();
            conn.close();
            this.dispose();
            new PrincipalWindow(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la rutina. Por favor, verifica los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("No se ha podido guardar la rutina.");
        }
    }

    
    private List<String> cargarRutinasDesdeBD() {
        List<String> ejercicios = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
            String sql = "SELECT Nombre FROM Rutina";
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
}
