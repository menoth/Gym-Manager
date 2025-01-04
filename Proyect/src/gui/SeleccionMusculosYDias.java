package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;

import domain.Musculo;
// He hecho los comentarios con chatgpt y tambien le he pedido ayuda para la seleccion de items
public class SeleccionMusculosYDias extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeleccionMusculosYDias(String usuario, String nombreRutina, String descripcionRutina, int DiasEntrenados, int MusculosPriorizados) {
        // Crear el diálogo
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 800);
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("Creación de rutina aleatoria");

        // Crear el panel principal del diálogo
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Listas para almacenar los JComboBox
        ArrayList<JComboBox<DayOfWeek>> diasCombos = new ArrayList<>();
        ArrayList<JComboBox<String>> musculosCombos = new ArrayList<>();

        // Selección de días
        JLabel labelDias = new JLabel("Seleccione qué días quiere entrenar:");
        panelContenido.add(labelDias);
        for (int j = 0; j < DiasEntrenados; j++) {
            JComboBox<DayOfWeek> diasDeSemana = new JComboBox<>(DayOfWeek.values());
            diasCombos.add(diasDeSemana);
            panelContenido.add(diasDeSemana);
        }

        // Selección de músculos
        JLabel labelMusculos = new JLabel("Seleccione los músculos a priorizar:");
        panelContenido.add(labelMusculos);
        for (int j = 0; j < MusculosPriorizados; j++) {
            JComboBox<String> seleccionMusculos = new JComboBox<>();
            ArrayList<Musculo> todosLosMusculos = new ArrayList<>();
            ConectarBaseDeDatos.ConectarBaseDeDatosMusculos(todosLosMusculos);
            for (Musculo musculo : todosLosMusculos) {
                seleccionMusculos.addItem(musculo.getNombre());
            }
            musculosCombos.add(seleccionMusculos);
            panelContenido.add(seleccionMusculos);
        }

        // Botones de acción
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        // Acción del botón "Aceptar"
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<DayOfWeek> diasSeleccionados = new ArrayList<>();
                ArrayList<String> musculosSeleccionados = new ArrayList<>();

                // Obtener selecciones de los JComboBox de días
                for (JComboBox<DayOfWeek> combo : diasCombos) {
                    diasSeleccionados.add((DayOfWeek) combo.getSelectedItem());
                }

                // Obtener selecciones de los JComboBox de músculos
                for (JComboBox<String> combo : musculosCombos) {
                    musculosSeleccionados.add((String) combo.getSelectedItem());
                }
                
                // Verificar duplicados en los días
                if (tieneDuplicados2(diasSeleccionados)) {
                    JOptionPane.showMessageDialog(dialog, "No se pueden seleccionar el mismo día más de una vez.", "Error de selección", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar duplicados en los músculos
                if (tieneDuplicados1(musculosSeleccionados)) {
                    JOptionPane.showMessageDialog(dialog, "No se pueden seleccionar el mismo músculo más de una vez.", "Error de selección", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cerrar el diálogo y abrir la ventana de creación de rutina
                dialog.dispose();
                new CreacionDeRutinaAleatoria(usuario, nombreRutina, descripcionRutina,  diasSeleccionados, musculosSeleccionados);
                JOptionPane.showMessageDialog(SeleccionMusculosYDias.this, "Creacion de rutina aleatoria exitosa");
            }
        });

        // Acción del botón "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
                new GenerarRutinaAleatoria(usuario);
            }
        });

        // Añadir botones al panel de botones
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        // Añadir todo al diálogo
        dialog.add(panelContenido, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        // Mostrar el diálogo
        dialog.setVisible(true);
        dialog.pack(); // Ajusta automáticamente el tamaño del diálogo
    }
	
	//Verificaciones de duplicados
	private boolean tieneDuplicados1(ArrayList<String> lista) {
        HashSet<String> elementosUnicos = new HashSet<>(lista);
        return elementosUnicos.size() < lista.size();
    }
	
	private boolean tieneDuplicados2(ArrayList<DayOfWeek> lista) {
        HashSet<DayOfWeek> elementosUnicos = new HashSet<>(lista);
        return elementosUnicos.size() < lista.size();
    }
}
