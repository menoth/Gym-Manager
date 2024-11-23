package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Rutina;

public class nombreRutinaInterfaz extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	nombreRutinaInterfaz(JFrame mainWindow, String usuario){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	    
	    // Crear el diálogo
	    JDialog dialog = new JDialog();
	    dialog.setLayout(new BorderLayout());
	    dialog.setSize(300, 200);
	    dialog.setLocation(620, 350);
	    dialog.setTitle("Nueva rutina");
		
	    // Crear el panel principal del diálogo
	    JPanel panelContenido = new JPanel();
	    panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
	    panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    // Crear los componentes
	    JLabel labelNombre = new JLabel("Inserte el nombre de la rutina:");
	    JTextField nombreRutina = new JTextField(10);
	    JLabel labelDescripcion= new JLabel("Inserte la descripción de la rutina");
	    JTextField descripcionRutina = new JTextField(10);

	    // Botones de acción
	    JPanel panelBotones = new JPanel();
	    JButton btnAceptar = new JButton("Aceptar");
	    JButton btnCancelar = new JButton("Cancelar");
	    
	    
	    // Añadir action listener para abrir la ventana de creacion de rutinas
	    btnAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				mainWindow.dispose();
                new InterfazRutina(usuario, nombreRutina.getText());
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
	    panelContenido.add(nombreRutina);
	    panelContenido.add(labelDescripcion);
	    panelContenido.add(descripcionRutina);

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
