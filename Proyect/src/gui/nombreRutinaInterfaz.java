package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class nombreRutinaInterfaz extends JFrame {

    private static final long serialVersionUID = 1L;

    public nombreRutinaInterfaz(JFrame mainWindow, String usuario) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el diálogo
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Nueva rutina");

        // Crear el panel principal del diálogo
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelContenido.setBackground(new Color(70, 130, 180)); // Fondo azul principal

        // Crear los componentes
        JLabel labelNombre = new JLabel("Inserte el nombre de la rutina:");
        labelNombre.setFont(new Font("Serif", Font.BOLD, 20));
        labelNombre.setForeground(Color.WHITE); // Texto blanco

        JTextField nombreRutinaJ = new JTextField(10);
        nombreRutinaJ.setFont(new Font("Serif", Font.PLAIN, 20));

        JLabel labelDescripcion = new JLabel("Inserte la descripción de la rutina:");
        labelDescripcion.setFont(new Font("Serif", Font.BOLD, 20));
        labelDescripcion.setForeground(Color.WHITE); // Texto blanco

        JTextField descripcionRutinaJ = new JTextField(10);
        descripcionRutinaJ.setFont(new Font("Serif", Font.PLAIN, 20));

        // Botones de acción
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(70, 130, 180)); // Fondo azul principal
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        // Estilo de los botones
        btnAceptar.setFont(new Font("Arial", Font.BOLD, 20));
        btnAceptar.setBackground(Color.WHITE); // Fondo blanco
        btnAceptar.setForeground(new Color(70, 130, 180)); // Texto azul

        btnCancelar.setFont(new Font("Arial", Font.BOLD, 20));
        btnCancelar.setBackground(Color.WHITE); // Fondo blanco
        btnCancelar.setForeground(new Color(70, 130, 180)); // Texto azul

        // Acción del botón Aceptar
        btnAceptar.addActionListener(e -> {
            String nombreRutina = nombreRutinaJ.getText().trim();
            String descripcionRutina = descripcionRutinaJ.getText().trim();
            if (!nombreRutina.isEmpty()) {
                if (mainWindow != null) {
                    mainWindow.dispose();
                }
                if (descripcionRutina.isEmpty()) {
                    descripcionRutina = null;
                }
                dialog.dispose();
                new InterfazRutina(nombreRutina, descripcionRutina, usuario);
            } else {
                JOptionPane.showMessageDialog(dialog, "El nombre de la rutina no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Acción del botón Cancelar
        btnCancelar.addActionListener(e -> dialog.dispose());

        // Añadir componentes al panel principal
        panelContenido.add(labelNombre);
        panelContenido.add(Box.createVerticalStrut(10));
        panelContenido.add(nombreRutinaJ);
        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(labelDescripcion);
        panelContenido.add(Box.createVerticalStrut(10));
        panelContenido.add(descripcionRutinaJ);

        // Añadir botones al panel de botones
        panelBotones.add(btnCancelar);
        panelBotones.add(btnAceptar);
        
        
        // Añadir todo al diálogo
        dialog.add(panelContenido, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar el diálogo
        dialog.setVisible(true);
    }
}
