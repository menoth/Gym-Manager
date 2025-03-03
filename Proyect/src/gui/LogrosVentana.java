package gui;
import javax.swing.*;

import domain.Usuario;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogrosVentana extends JFrame {

    private static final long serialVersionUID = 1L;

    LogrosVentana(Usuario usuario) {

        // Crear ventana
        setUndecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(1200, 900);
        setLayout(new BorderLayout());
        setTitle("Selecciona tus logros");
        setLocationRelativeTo(null);

        // Establecer el color de fondo de la ventana
        getContentPane().setBackground(new Color(70, 130, 180));

        JLabel info = new JLabel("Selecciona hasta 3 logros:", JLabel.CENTER);
        info.setFont(new Font("Arial", Font.BOLD, 16));
        add(info, BorderLayout.NORTH);

        // Panel para los logros
        JPanel logrosPanel = new JPanel(new GridLayout(0, 5, 0, 0));
        logrosPanel.setBackground(new Color(70, 130, 180)); // Cambiar fondo del panel de logros

        // Nombres de las imágenes
        String[] logrosImagenes = {
            "40kgs en press de banca.png",
            "70kgs en press de banca.png",
            "100kgs en press de banca.png",
            "140kgs en press de banca.png",
            "180kgs en press de banca.png",
            "60kgs en sentadilla.png",
            "80kgs en sentadilla.png",
            "120kgs en sentadilla.png",
            "170kgs en sentadilla.png",
            "220kgs en sentadilla.png",
            "60kgs en peso muerto.png",
            "100kgs en peso muerto.png",
            "150kgs en peso muerto.png",
            "200kgs en peso muerto.png",
            "250kgs en peso muerto.png"
        };

        // Nombres de los textos
        String[] logrosTexto = {
            "40kgs en press de banca",
            "70kgs en press de banca",
            "100kgs en press de banca",
            "140kgs en press de banca",
            "180kgs en press de banca",
            "60kgs en sentadilla",
            "80kgs en sentadilla",
            "120kgs en sentadilla",
            "170kgs en sentadilla",
            "220kgs en sentadilla",
            "60kgs en peso muerto",
            "100kgs en peso muerto",
            "150kgs en peso muerto",
            "200kgs en peso muerto",
            "250kgs en peso muerto"
        };

        // Cargar en la BD los nombres de los logros
        CargarEnBD(logrosTexto);

        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
        ArrayList<ButtonGroup> gruposPorFila = new ArrayList<>();

        for (int i = 0; i < logrosImagenes.length; i++) {
            JPanel logroPanel = new JPanel();
            logroPanel.setLayout(new BorderLayout());
            logroPanel.setBackground(new Color(70, 130, 180)); // Cambiar fondo de cada logro

            // Cargar imágenes
            ImageIcon icon = null;
            try {
                icon = new ImageIcon(getClass().getResource("/imagenes/" + logrosImagenes[i]));
                Image EscalaImagen = icon.getImage().getScaledInstance(200, 270, Image.SCALE_SMOOTH);
                icon = new ImageIcon(EscalaImagen);
            } catch (Exception e) {
                System.err.println("No se encontró la imagen: " + logrosImagenes[i]);
            }

            JLabel imagenLabel = new JLabel(icon);
            imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logroPanel.add(imagenLabel, BorderLayout.CENTER);

            // Texto debajo de la imagen
            JLabel textoLabel = new JLabel(logrosTexto[i], JLabel.CENTER);
            textoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            textoLabel.setForeground(Color.WHITE); // Asegurar que el texto sea visible
            logroPanel.add(textoLabel, BorderLayout.SOUTH);

            // RadioButton para la imagen
            JRadioButton radioButton = new JRadioButton();
            radioButton.setBackground(new Color(70, 130, 180)); // Cambiar fondo del botón
            radioButtons.add(radioButton);
            logroPanel.add(radioButton, BorderLayout.WEST);

            // Agrupar por filas (5 por fila)
            int fila = i / 5;
            while (gruposPorFila.size() <= fila) {
                gruposPorFila.add(new ButtonGroup());
            }
            gruposPorFila.get(fila).add(radioButton);

            logrosPanel.add(logroPanel);
        }

        // Agregar logros a un JScrollPanel por si hay muchos
        JScrollPane scrollPane = new JScrollPane(logrosPanel);
        scrollPane.getViewport().setBackground(new Color(70, 130, 180)); // Fondo del viewport del scrollpane
        add(scrollPane, BorderLayout.CENTER);

        // Solo se pueden seleccionar 3 logros en total
        ArrayList<JRadioButton> seleccionados = new ArrayList<>();

        for (JRadioButton radioButton : radioButtons) {
            radioButton.addItemListener(e -> {
                if (radioButton.isSelected()) {                 
                    seleccionados.add(radioButton);                    
                } else {
                    seleccionados.remove(radioButton);
                }
            });
        }

        // Botón para cancelar
        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(new Font("Arial", Font.BOLD, 14));
        cancelar.addActionListener(e -> {
            dispose();
            new EditarPerfil(usuario);
        });

        // Botón para confirmar selección
        JButton confirmar = new JButton("Confirmar");
        confirmar.setFont(new Font("Arial", Font.BOLD, 14));
        confirmar.addActionListener(e -> {
            if (seleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null,
                    "No has seleccionado ningún logro.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                String url = "jdbc:sqlite:Sources/bd/baseDeDatos.db";

                try (Connection conn = DriverManager.getConnection(url)) {
                    // Eliminar los logros actuales del usuario
                    String deleteQuery = "DELETE FROM ConsigueLogro WHERE Usuario = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                        deleteStmt.setString(1, usuario.getUsuario());
                        deleteStmt.executeUpdate();
                    }

                    // Insertar los logros seleccionados
                    String insertQuery = "INSERT INTO ConsigueLogro (Usuario, Logro) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        for (JRadioButton radioButton : seleccionados) {
                            int index = radioButtons.indexOf(radioButton);
                            String logroNombre = logrosTexto[index];

                            // Obtener el ID del logro correspondiente
                            String selectLogroQuery = "SELECT ID_Logro FROM Logro WHERE Nombre = ?";
                            try (PreparedStatement selectStmt = conn.prepareStatement(selectLogroQuery)) {
                                selectStmt.setString(1, logroNombre);
                                var rs = selectStmt.executeQuery();
                                if (rs.next()) {
                                    int logroID = rs.getInt("ID_Logro");

                                    // Insertar logro y usuario en la tabla ConsigueLogro
                                    insertStmt.setString(1, usuario.getUsuario());
                                    insertStmt.setInt(2, logroID);
                                    insertStmt.executeUpdate();
                                }
                            }
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Error al cargar los logros y usuarios correspondientes");
                }
            }
            dispose();
            new PerfilUsuario(usuario.getUsuario());
        });

        // Añadir botones a un GridLayout
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        panel.add(cancelar);
        panel.add(confirmar);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void CargarEnBD(String[] logrosTexto) {
        String url = "jdbc:sqlite:Sources/bd/baseDeDatos.db";

        String insertarSQL = "INSERT OR IGNORE INTO Logro (Nombre) VALUES (?)";
        String eliminarSQL = "DELETE FROM Logro WHERE Nombre NOT IN (%s)";

        try {
            Connection conn = DriverManager.getConnection(url);

            // Insertar logros
            try (PreparedStatement stmtInsertar = conn.prepareStatement(insertarSQL)) {
                for (String logro : logrosTexto) {
                    if (logro != null && !logro.isEmpty()) {
                        stmtInsertar.setString(1, logro);
                        stmtInsertar.executeUpdate();
                    }
                }
            }

            // Preparar la consulta para eliminar logros no incluidos en la lista
            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < logrosTexto.length; i++) {
                placeholders.append("?");
                if (i < logrosTexto.length - 1) {
                    placeholders.append(",");
                }
            }
            String query = String.format(eliminarSQL, placeholders);

            try (PreparedStatement stmtEliminar = conn.prepareStatement(query)) {
                for (int i = 0; i < logrosTexto.length; i++) {
                    stmtEliminar.setString(i + 1, logrosTexto[i]);
                }
                stmtEliminar.executeUpdate();
                stmtEliminar.close();
            }

            conn.close();

        } catch (SQLException ex) {
            System.err.println("Error al actualizar la base de datos");
            ex.printStackTrace();
        }
    }
}
