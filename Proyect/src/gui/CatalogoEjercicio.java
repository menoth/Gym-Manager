package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CatalogoEjercicio extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField campo_busqueda;
    private JButton boton_buscar;
    private JPanel gridPrincipal; // Panel dinámico para botones
    private List<String> listaEjercicios; // Lista dinámica para filtrar

    public CatalogoEjercicio(String usuario, String nombreRutina) {
        // Detalles de la ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        // Panel Superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(Color.DARK_GRAY);
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBorder(new EmptyBorder(45, 40, 45, 40));

        // Botón Volver
        JButton botonVolver = new JButton("Atrás");
        botonVolver.setPreferredSize(new Dimension(140, 10));
        botonVolver.addActionListener(e -> {
            dispose();
            new InterfazRutina(usuario, nombreRutina);
        });
        panelSuperior.add(botonVolver, BorderLayout.WEST);

        // Botón Administrador
        JButton botonAdmin = new JButton("Administrador");
        botonAdmin.setPreferredSize(new Dimension(140, 10));
        if (usuario.equals("admin")) {
            botonAdmin.addActionListener(e -> new formularioAdmin());
        }
        panelSuperior.add(botonAdmin, BorderLayout.EAST);

        // Buscador
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(Color.DARK_GRAY);
        campo_busqueda = new JTextField();
        campo_busqueda.setPreferredSize(new Dimension(500, 30));
        boton_buscar = new JButton("Buscar");
        boton_buscar.setPreferredSize(new Dimension(140, 40));
        boton_buscar.addActionListener(e -> filtrarEjercicios());
        panelBusqueda.add(campo_busqueda);
        panelBusqueda.add(boton_buscar);
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);

        // Añadir panel superior al principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Catálogo dinámico
        gridPrincipal = new JPanel();
        gridPrincipal.setBackground(Color.LIGHT_GRAY);
        gridPrincipal.setLayout(new GridLayout(0, 4, 20, 20)); // 4 botones por fila con espacio entre ellos
        gridPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Inicializar lista de ejercicios
        listaEjercicios = cargarEjerciciosDesdeBD();

        // Mostrar botones dinámicos
        actualizarCatalogo(listaEjercicios);

        // Scroll vertical
        JScrollPane scrollPane = new JScrollPane(gridPrincipal, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); //DESPLAZAMIENTO
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }

    // Cargar ejercicios desde la base de datos
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
            System.out.println("Error al cargar los ejercicios desde la base de datos.");
        }
        return ejercicios;
    }

    // Actualizar el catálogo de ejercicios
    private void actualizarCatalogo(List<String> ejercicios) {
        gridPrincipal.removeAll(); // Limpiar el panel
        for (String nombreEjercicio : ejercicios) {
            JButton boton = new JButton("+" + nombreEjercicio);
            boton.addActionListener(e -> mostrarDialogoEjercicio(nombreEjercicio));
            gridPrincipal.add(boton);
        }
        gridPrincipal.revalidate();
        gridPrincipal.repaint();
    }

    // Filtrar ejercicios por el buscador
    private void filtrarEjercicios() {
        String filtro = campo_busqueda.getText().trim().toLowerCase();
        List<String> ejerciciosFiltrados = new ArrayList<>();
        for (String ejercicio : listaEjercicios) {
            if (ejercicio.toLowerCase().contains(filtro)) {
                ejerciciosFiltrados.add(ejercicio);
            }
        }
        actualizarCatalogo(ejerciciosFiltrados);
    }

    // Mostrar un cuadro de diálogo para el ejercicio seleccionado
    private void mostrarDialogoEjercicio(String nombreEjercicio) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);
        dialog.setLocation(620, 350);

        JLabel label = new JLabel("¿Qué deseas hacer con el ejercicio " + nombreEjercicio + "?");
        dialog.add(label, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(event -> dialog.dispose());
        dialog.add(btnCerrar, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
