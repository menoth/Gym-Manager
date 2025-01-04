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
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterfazRutina extends JFrame {

    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
	private List<String> listaRutina;
    @SuppressWarnings("unused")
	private List<String> listaEntrenamientos;
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
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
            conn.setAutoCommit(false); // Iniciar transacción
            System.out.println("Se ha conectado a la base de datos.");

            // Obtener el próximo ID para la Rutina
            int idRutina = cargarRutinasDesdeBD().size() + 1;

            // GUARDAR EN LA BD LA RUTINA
            String sqlRutina = "INSERT INTO Rutina (ID_Rutina, Nombre, Descripción, Usuario) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmtRutina = conn.prepareStatement(sqlRutina)) {
                pstmtRutina.setInt(1, idRutina);
                pstmtRutina.setString(2, nombreRutina);
                pstmtRutina.setString(3, descripcionRutina);
                pstmtRutina.setString(4, usuario);
                pstmtRutina.executeUpdate();
            }

            // Preparar los IDs
            int idEjercicioEnEntrenamiento = obtenerMaxIdEjercicioEnEntrenamiento(conn) + 1;

            // GUARDAR EJERCICIOS Y SERIES EN LA BASE DE DATOS
            ModeloJTable modelo = (ModeloJTable) tabla.getModel();
            for (int columnIndex = 0; columnIndex < modelo.getColumnCount(); columnIndex++) {
                DayOfWeek dia = DayOfWeek.of(columnIndex + 1); // Día asociado a la columna
                String diaEntrenamiento = dia.toString(); // Día en texto

                // Obtener el próximo ID para el Entrenamiento
                int idEntrenamiento = obtenerMaxIdEntrenamiento(conn) + 1;

                // INSERTAR ENTRENAMIENTO PARA ESTE DÍA
                String sqlEntrenamiento = "INSERT INTO Entrenamiento (ID_Entrenamiento, Nombre, Descripción, ID_Rutina, Dia) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmtEntrenamiento = conn.prepareStatement(sqlEntrenamiento)) {
                    pstmtEntrenamiento.setInt(1, idEntrenamiento);
                    pstmtEntrenamiento.setString(2, nombreRutina + " - " + diaEntrenamiento);
                    pstmtEntrenamiento.setString(3, descripcionRutina);
                    pstmtEntrenamiento.setInt(4, idRutina);
                    pstmtEntrenamiento.setString(5, diaEntrenamiento);
                    pstmtEntrenamiento.executeUpdate();
                }

                // OBTENER LOS EJERCICIOS DE LA COLUMNA (DÍA)
                List<Map<String, Object>> ejercicios = modelo.getDatosColumnas().get(dia);
                if (ejercicios != null) {
                    for (int ordenEnEntrenamiento = 0; ordenEnEntrenamiento < ejercicios.size(); ordenEnEntrenamiento++) {
                        Map<String, Object> ejercicioData = ejercicios.get(ordenEnEntrenamiento);
                        String nombreEjercicio = (String) ejercicioData.get("ejercicio");

                        int idEjercicio = obtenerIdEjercicio(conn, nombreEjercicio); // Obtener ID del ejercicio
                        int ordenEjercicio = ordenEnEntrenamiento + 1; // Incrementar orden del ejercicio

                        // INSERTAR EN EJERCICIOENENTRENAMIENTO
                        String sqlEjercicioEnEntrenamiento = "INSERT INTO EjercicioEnEntrenamiento "
                                + "(ID_EjercicioEnEntrenamiento, ID_Entrenamiento, ID_Ejercicio, OrdenEnEntrenamiento) "
                                + "VALUES (?, ?, ?, ?)";
                        try (PreparedStatement pstmtEjercicio = conn.prepareStatement(sqlEjercicioEnEntrenamiento)) {
                            pstmtEjercicio.setInt(1, idEjercicioEnEntrenamiento);
                            pstmtEjercicio.setInt(2, idEntrenamiento);
                            pstmtEjercicio.setInt(3, idEjercicio);
                            pstmtEjercicio.setInt(4, ordenEjercicio);
                            pstmtEjercicio.executeUpdate();
                        }

                        // OBTENER SERIES DEL EJERCICIO
                        @SuppressWarnings("unchecked")
                        List<Map<String, Integer>> series = (List<Map<String, Integer>>) ejercicioData.get("series");
                        for (int serieIndex = 0; serieIndex < series.size(); serieIndex++) {
                            Map<String, Integer> serieData = series.get(serieIndex);

                            // INSERTAR EN SERIE
                            String sqlSerie = "INSERT INTO Serie (ID_EjercicioEnEntrenamiento, ID_RPE, Repeticiones, Peso, OrdenEnEjercicio) "
                                    + "VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement pstmtSerie = conn.prepareStatement(sqlSerie)) {
                                pstmtSerie.setInt(1, idEjercicioEnEntrenamiento); // Asociar con EjercicioEnEntrenamiento
                                pstmtSerie.setInt(2, serieData.get("esfuerzo")); // ID de RPE
                                pstmtSerie.setInt(3, serieData.get("repeticiones")); // Repeticiones
                                pstmtSerie.setDouble(4, serieData.get("peso")); // Peso
                                pstmtSerie.setInt(5, serieIndex + 1); // Orden en ejercicio
                                pstmtSerie.executeUpdate();
                            }
                        }

                        // Incrementar ID para el próximo EjercicioEnEntrenamiento
                        idEjercicioEnEntrenamiento++;
                    }
                }
            }

            
            conn.commit();
            JOptionPane.showMessageDialog(this, "Rutina y entrenamiento guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Datos guardados correctamente en la base de datos.");

            // Cerrar la ventana actual y abrir PrincipalWindow
            dispose(); // Cierra la ventana actual
            new PrincipalWindow(usuario); // Abre la ventana principal

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    @SuppressWarnings("unused")
	private List<String> cargarEntrenamientosDesdeBD() {
        List<String> ejercicios = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
            String sql = "SELECT Nombre FROM Entrenamiento";
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
    
    private int obtenerIdEjercicio(Connection conn, String nombreEjercicio) throws SQLException {
        String sql = "SELECT ID_Ejercicio FROM Ejercicio WHERE Nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreEjercicio);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_Ejercicio");
            }
        }
        throw new SQLException("No se encontró el ejercicio: " + nombreEjercicio);
    }
    
    private int obtenerMaxIdEntrenamiento(Connection conn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(ID_Entrenamiento), 0) AS MaxID FROM Entrenamiento";
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("MaxID");
            }
        }
        return 0; // Si no hay registros, empezar desde 0
    }
    
    private int obtenerMaxIdEjercicioEnEntrenamiento(Connection conn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(ID_EjercicioEnEntrenamiento), 0) AS MaxID FROM EjercicioEnEntrenamiento";
        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("MaxID");
            }
        }
        return 0; // Si no hay registros, empezar desde 0
    }



}
