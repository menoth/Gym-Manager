package gui;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GenerarRutinaAleatoria extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GenerarRutinaAleatoria(String usuario) {
		// TODO Auto-generated constructor stub
		JDialog dialog = new JDialog();
	    dialog.setLayout(new BorderLayout());
	    dialog.setSize(500, 500);
	    dialog.setLocationRelativeTo(null);
	    dialog.setTitle("Creacion de rutina aleatoria");
		
	    // Crear el panel principal del diálogo
	    JPanel panelContenido = new JPanel();
	    panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
	    panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
	    JLabel labelNombre = new JLabel("Nombre del entrenamiento:");
        JTextArea textNombre = new JTextArea(1, 20);
        textNombre.setBorder(BorderFactory.createEtchedBorder()); // Borde para distinguir el campo

        // Campo para la descripción del entrenamiento
        JLabel labelDescripcion = new JLabel("Descripción del entrenamiento:");
        JTextArea textDescripcion = new JTextArea(3, 20);
        textDescripcion.setLineWrap(true);
        textDescripcion.setWrapStyleWord(true);
        textDescripcion.setBorder(BorderFactory.createEtchedBorder()); // Borde para distinguir el campo

	    // Crear los componentes
	    JLabel labelDias = new JLabel("Cuantos dias quieres entrenar:");
	    JComboBox<Integer> diasDeSemana = new JComboBox<>();
		for (int j = 0; j < 7; j++) {
			diasDeSemana.addItem(j+1);
		}
	    JLabel labelMusculos= new JLabel("Cuantos musculos quieres priorizar");
	    JComboBox<Integer> musculosEntrenados = new JComboBox<>();
		for (int j = 0; j < 15; j++) {
			musculosEntrenados.addItem(j+1);
		}

	    // Botones de acción
	    JPanel panelBotones = new JPanel();
	    JButton btnAceptar = new JButton("Aceptar");
	    JButton btnCancelar = new JButton("Cancelar");
	    
	    // Añadir action listener para abrir la ventana de creacion de rutinas
	    btnAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textNombre.getText().trim().isEmpty() || textDescripcion.getText().trim().isEmpty()) {
                    System.out.println("Por favor, completa todos los campos antes de continuar.");
                    return;
                }
				dialog.dispose();
                new SeleccionMusculosYDias(usuario, textNombre.getText() , textDescripcion.getText() , (int) diasDeSemana.getSelectedItem(), (int) musculosEntrenados.getSelectedItem());
			}
	    	
	    });
		
	    btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				
			}
	    	
	    });

	    // Añadir componentes al panel principal
	    panelContenido.add(labelNombre);
        panelContenido.add(textNombre);
        panelContenido.add(labelDescripcion);
        panelContenido.add(textDescripcion);
	    panelContenido.add(labelDias);
	    panelContenido.add(diasDeSemana);
	    panelContenido.add(labelMusculos);
	    panelContenido.add(musculosEntrenados);

	    // Añadir botones al panel de botones
	    panelBotones.add(btnAceptar);
	    panelBotones.add(btnCancelar);

	    // Añadir todo al diálogo
	    dialog.add(panelContenido, BorderLayout.CENTER);
	    dialog.add(panelBotones, BorderLayout.SOUTH);

	    // Mostrar el diálogo
	    dialog.setVisible(true);
	    dialog.pack();
		
		
	}
	
}
