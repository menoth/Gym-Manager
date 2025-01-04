package gui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
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
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Nueva rutina");

        // Crear el panel principal del diálogo
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear los componentes
        JLabel labelNombre = new JLabel("Inserte el nombre de la rutina:");
        JTextField nombreRutinaJ = new JTextField(10);
        JLabel labelDescripcion = new JLabel("Inserte la descripción de la rutina:");
        JTextField descripcionRutinaJ = new JTextField(10);

        // Botones de acción
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

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
        panelContenido.add(nombreRutinaJ);
        panelContenido.add(labelDescripcion);
        panelContenido.add(descripcionRutinaJ);

        // Añadir botones al panel de botones
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        // Añadir todo al diálogo
        dialog.add(panelContenido, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar el diálogo
        dialog.setVisible(true);
    }
}

