package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domain.Ejercicio;
import domain.Musculo;
import domain.Musculo.TamanoMusculo;
import domain.Rutina;

public class CatalogoEjerciciosEditarRutina extends JFrame {
	private static final long serialVersionUID = 1L;

    private JTextField campoBusqueda;
    private JButton botonBuscar;
    private JPanel gridPrincipal; // Panel dinámico para botones
    private List<Ejercicio> listaEjercicios; // Lista dinámica para filtrar
    
    public CatalogoEjerciciosEditarRutina(String usuario, int idRutina, Rutina rutina, int idEntrenamiento) {
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
            new EditarRutina(usuario, idRutina, rutina);
        });
        panelSuperior.add(botonVolver, BorderLayout.WEST);

        // Buscador
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(Color.DARK_GRAY);
        campoBusqueda = new JTextField();
        campoBusqueda.setPreferredSize(new Dimension(500, 30));
        botonBuscar = new JButton("Buscar");
        botonBuscar.setPreferredSize(new Dimension(140, 40));
        botonBuscar.addActionListener(e -> filtrarEjercicios(idRutina, idEntrenamiento, usuario));
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(botonBuscar);
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);
        
        //-----------------------------------------------------------------------------------------------------------
        
        // Añadir panel superior al principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        //-----------------CATÁLOGO----------------------------------------------------------------------------------
        gridPrincipal = new JPanel();
        gridPrincipal.setBackground(Color.LIGHT_GRAY);
        gridPrincipal.setLayout(new GridLayout(0, 4, 20, 20)); // 4 botones por fila con espacio entre ellos
        gridPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Inicializar lista de ejercicios
        listaEjercicios = cargarEjerciciosDesdeBD();

        // Mostrar botones dinámicos
        actualizarCatalogo(listaEjercicios, idRutina, idEntrenamiento, usuario);

        // Scroll vertical
        JScrollPane scrollPane = new JScrollPane(gridPrincipal, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); //DESPLAZAMIENTO
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        
        //-------------------------------------------------------------------------------------------------------------
        
        add(panelPrincipal);
        setVisible(true);
    }
    
    private Musculo cargarMusculoDesdeBD(int idMusculo) {
        Musculo musculo = null;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
            String sql = "SELECT * FROM Musculo WHERE ID_Musculo = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idMusculo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                String tamaño = rs.getString("TamanoMusculo");
                musculo = new Musculo(nombre, TamanoMusculo.valueOf(tamaño));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cargar el músculo desde la base de datos.");
        }
        return musculo;
    }


    // Cargar ejercicios desde la base de datos
    private List<Ejercicio> cargarEjerciciosDesdeBD() {
        List<Ejercicio> ejercicios = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
            String sql = "SELECT * FROM Ejercicio";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int idEjercicio = rs.getInt("ID_Ejercicio");
                String nombre = rs.getString("Nombre");
                int musculoPrincipal = rs.getInt("Musculo_Principal");
                int musculoSecundario = rs.getInt("Musculo_Secundario");
                
                Ejercicio ejercicio = new Ejercicio(idEjercicio, nombre, cargarMusculoDesdeBD(musculoPrincipal),cargarMusculoDesdeBD(musculoSecundario));
                ejercicios.add(ejercicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los ejercicios desde la base de datos.");
        }
        return ejercicios;
    }

    // Actualizar el catálogo de ejercicios
    private void actualizarCatalogo(List<Ejercicio> ejercicios2, int idRutina, int idEntrenamiento, String usuario) {
        gridPrincipal.removeAll(); // Limpiar el panel
        for (Ejercicio ejercicio : ejercicios2) {
            JButton boton = new JButton("+" + ejercicio.getNombre());
            
            gridPrincipal.add(boton);
            boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					cargarEjercicioEnBaseDeDatos(idRutina,idEntrenamiento, ejercicio.getId());
					new PerfilUsuario(usuario);
		            dispose();
					
				}
			});
            
        }
        gridPrincipal.revalidate();
        gridPrincipal.repaint();
    }

    // Filtrar ejercicios por el buscador
    private void filtrarEjercicios(int idRutina, int idEntrenamiento, String usuario) {
        String filtro = campoBusqueda.getText().trim().toLowerCase();
        List<Ejercicio> ejerciciosFiltrados = new ArrayList<>();
        for (Ejercicio ejercicio : listaEjercicios) {
            if (ejercicio.getNombre().toLowerCase().contains(filtro)) {
                ejerciciosFiltrados.add(ejercicio);
            }
        }
        actualizarCatalogo(ejerciciosFiltrados, idRutina, idEntrenamiento, usuario);
    }

    // Mostrar un cuadro de diálogo para el ejercicio seleccionado
    private void cargarEjercicioEnBaseDeDatos(int idRutina, int idEntrenamiento, int idEjercicio) {
    	 try {
    	        // Cargar el driver JDBC de SQLite
    	        Class.forName("org.sqlite.JDBC");
    	    } catch (ClassNotFoundException e) {
    	        System.out.println("No se ha podido cargar el driver de la BD");
    	        e.printStackTrace();
    	    }

    	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:Sources/bd/baseDeDatos.db")) {
    	        // Crear la sentencia SQL para insertar un nuevo registro
    	        String sql = "INSERT INTO EjercicioEnEntrenamiento (ID_Entrenamiento, ID_Ejercicio, OrdenEnEntrenamiento) VALUES (?, ?, ?)";

    	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
    	            // Establecer los valores para la consulta
    	            stmt.setInt(1, idEntrenamiento);
    	            stmt.setInt(2, idEjercicio);
    	            stmt.setInt(3, 1);

    	            // Ejecutar la inserción
    	            int filasInsertadas = stmt.executeUpdate();
    	            if (filasInsertadas > 0) {
    	                System.out.println("Ejercicio añadido correctamente a EjercicioEnEntrenamiento.");
    	            } else {
    	                System.out.println("No se pudo añadir el ejercicio.");
    	            }
    	        }
    	    } catch (SQLException e) {
    	        System.out.println("Error al añadir el ejercicio a EjercicioEnEntrenamiento.");
    	        e.printStackTrace();
    	    }
    }
}
