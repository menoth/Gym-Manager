package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class SeguimientoPersonal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panelPrincipal;
    private int añoElegido;
    private final Map<JButton, Integer> estadoBotones = new HashMap<>();
    private Connection connection;
    private final String usuario;

    public SeguimientoPersonal(String usuario) {
        this.usuario = usuario;

        setTitle("Calendario Anual - Seguimiento: " + usuario);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        inicializarConexion();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarConexion();
                dispose();
            }
        });

        JPanel panelElegirAño = new JPanel(new FlowLayout());
        panelElegirAño.setBackground(new Color(70, 130, 180));
        
        JLabel labelAño = new JLabel("Selecciona el Año:");
        labelAño.setForeground(new Color(255, 255, 255));
        JComboBox<Integer> selectorAño = new JComboBox<>();
        for (int year = 2024; year <= 2100; year++) {
            selectorAño.addItem(year);
        }
        añoElegido = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        selectorAño.setSelectedItem(añoElegido);
        selectorAño.addActionListener(e -> {
            añoElegido = (int) selectorAño.getSelectedItem();
            actualizarCalendario(usuario);
        });

        panelElegirAño.add(labelAño);
        panelElegirAño.add(selectorAño);
        add(panelElegirAño, BorderLayout.NORTH);

        panelPrincipal = new JPanel(new GridLayout(3, 4, 10, 10));

        add(panelPrincipal, BorderLayout.CENTER);

        JPanel panelLeyenda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelLeyenda.setBackground(new Color(70, 130, 180));
        
        JLabel labelCumplido = new JLabel("Cumplido:");
        labelCumplido.setForeground(Color.GREEN);
        JLabel labelNoCumplido = new JLabel("No Cumplido:");
        labelNoCumplido.setForeground(Color.RED);

        panelLeyenda.add(labelCumplido);
        panelLeyenda.add(new JLabel("✓"));
        panelLeyenda.add(Box.createHorizontalStrut(20));
        panelLeyenda.add(labelNoCumplido);
        panelLeyenda.add(new JLabel("✗"));

        add(panelLeyenda, BorderLayout.SOUTH);

        actualizarCalendario(usuario);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarConexion();
                dispose();
            }
        });
        setVisible(true);
    }

    private void inicializarConexion() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db");
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }

    private void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión a la base de datos:");
            e.printStackTrace();
        }
    }

    private void registrarEstado(String fecha, String estado) {
        try {
            // Inserta o actualiza el estado para el usuario y la fecha
            String insertarSQL = """
                INSERT INTO SeguimientoPersonal (fecha, estado, usuario)
                VALUES (?, ?, ?)
                ON CONFLICT(usuario, fecha) DO UPDATE SET estado = excluded.estado;
            """;

            try (PreparedStatement statement = connection.prepareStatement(insertarSQL)) {
                statement.setString(1, fecha);
                statement.setString(2, estado);
                statement.setString(3, usuario);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar el estado:");
            e.printStackTrace();
        }
    }

    private void eliminarEstado(String fecha) {
        try {
            // Elimina el estado para el usuario y la fecha especificada
            String eliminarSQL = "DELETE FROM SeguimientoPersonal WHERE usuario = ? AND fecha = ?";
            try (PreparedStatement statement = connection.prepareStatement(eliminarSQL)) {
                statement.setString(1, usuario);
                statement.setString(2, fecha);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el estado:");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
	private boolean estadoYaExiste(String fecha) {
        try {
            // Verifica si ya existe un registro para el usuario y la fecha
            String consultaSQL = "SELECT COUNT(*) FROM SeguimientoPersonal WHERE usuario = ? AND fecha = ?";
            try (PreparedStatement statement = connection.prepareStatement(consultaSQL)) {
                statement.setString(1, usuario);
                statement.setString(2, fecha);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0; // Si hay al menos un registro
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la existencia del estado:");
            e.printStackTrace();
        }
        return false;
    }

    private void cargarEstadosDesdeBD() {
        try {
            // Carga todos los estados para el usuario actual
            String consultaSQL = "SELECT fecha, estado FROM SeguimientoPersonal WHERE usuario = ?";
            try (PreparedStatement statement = connection.prepareStatement(consultaSQL)) {
                statement.setString(1, usuario);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        String fecha = rs.getString("fecha");
                        String estado = rs.getString("estado");

                        JButton boton = buscarBotonPorFecha(fecha);
                        if (boton != null) {
                            switch (estado) {
                                case "Verde" -> {
                                    boton.setBackground(Color.GREEN);
                                    estadoBotones.put(boton, 1);
                                }
                                case "Rojo" -> {
                                    boton.setBackground(Color.RED);
                                    estadoBotones.put(boton, 2);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar los estados desde la base de datos:");
            e.printStackTrace();
        }
    }


    private JButton buscarBotonPorFecha(String fecha) {
        for (JButton boton : estadoBotones.keySet()) {
            if (boton.getName().equals(fecha)) {
                return boton;
            }
        }
        return null;
    }

    private void actualizarCalendario(String usuario) {
        panelPrincipal.removeAll();
        estadoBotones.clear();

        for (int mes = 1; mes <= 12; mes++) {
            JPanel panelMes = new JPanel(new BorderLayout());
            panelMes.setBackground(new Color(70, 130, 180));
            panelMes.setBorder(BorderFactory.createTitledBorder(YearMonth.of(añoElegido, mes).getMonth().toString()));

            JPanel panelDias = new JPanel(new GridLayout(7, 7));
            panelDias.setBackground(new Color(70, 130, 180));
            panelMes.add(panelDias, BorderLayout.CENTER);

            for (DayOfWeek dia : DayOfWeek.values()) {
                JLabel labelDia = new JLabel(dia.toString().substring(0, 3), SwingConstants.CENTER);
                labelDia.setForeground(new Color(255, 255, 255));
                labelDia.setFont(new Font("Arial", Font.BOLD, 12));
                panelDias.add(labelDia);
            }

            YearMonth yearMonth = YearMonth.of(añoElegido, mes);
            int totalDias = yearMonth.lengthOfMonth();
            int primerDia = yearMonth.atDay(1).getDayOfWeek().getValue();

            for (int i = 1; i < primerDia; i++) {
                panelDias.add(new JLabel(""));
            }

            for (int dia = 1; dia <= totalDias; dia++) {
            	JButton botonDia = new JButton(String.valueOf(dia));
            	botonDia.setBackground(new Color(255, 255, 255));
        		botonDia.setForeground(new Color(70, 130, 180));
                botonDia.setName(LocalDate.of(añoElegido, mes, dia).toString());
                botonDia.setPreferredSize(new Dimension(40, 40));
                botonDia.setFont(new Font("Arial", Font.PLAIN, 10));
                botonDia.setFocusPainted(false);
                botonDia.addActionListener(e -> cambiarColorBoton(botonDia));
                estadoBotones.put(botonDia, 0);
                panelDias.add(botonDia);
            }

            int totalCeldas = 42;
            int celdasUsadas = primerDia - 1 + totalDias;
            for (int i = celdasUsadas; i < totalCeldas; i++) {
                panelDias.add(new JLabel(""));
            }

            panelPrincipal.add(panelMes);
        }

        panelPrincipal.revalidate();
        panelPrincipal.repaint();

        cargarEstadosDesdeBD();
    }

    private void cambiarColorBoton(JButton boton) {
        String fecha = boton.getName();
        int estadoActual = estadoBotones.getOrDefault(boton, 0);
        if (estadoActual == 0) {
            boton.setBackground(Color.GREEN);
            estadoBotones.put(boton, 1);
            registrarEstado(fecha, "Verde");
        } else if (estadoActual == 1) {
            boton.setBackground(Color.RED);
            estadoBotones.put(boton, 2);
            registrarEstado(fecha, "Rojo");
        } else {
        	boton.setBackground(new Color(255, 255, 255));
            estadoBotones.put(boton, 0);
            eliminarEstado(fecha);
        }
    }
}
