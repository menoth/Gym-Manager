package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;


public class PrincipalWindow extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame previous;
	


	public PrincipalWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel general = new JPanel();
		general.setLayout(new BorderLayout());
		
		//Panel donde va a ir el perfil, el buscador y el menu desplegable
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.black);
		
		// Editar el tamaño vertical del panel de arriba
		pNorth.setPreferredSize(new Dimension(0, 150));
		
		//Añadimos todo a la ventana
		general.add(pNorth, BorderLayout.NORTH);
		
		//Creamos el panel donde va a ir el PERFIL
		JPanel profile = new JPanel();
		pNorth.setLayout(new BorderLayout());
		pNorth.add(profile, BorderLayout.WEST);
		
		//Button para cambiar a la ventana "Perfil de usuario"
		JButton profileButton = new JButton("PROFILE");
		
		// Hacer que el botón "PROFILE" salte a la ventana perfil de usuario
        profileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Hacemos que la ventana principal no se vea
				setVisible(false);
				PerfilUsuario perfilUsuario = new PerfilUsuario();
				// Cuando se abra la interfaz ocupará toda la pantalla
				perfilUsuario.setExtendedState(JFrame.MAXIMIZED_BOTH);
				//Hacemos que la ventana del perfil se vea
				perfilUsuario.setVisible(true);
			}
        });
		
		// Editar el tamaño horizontal del panel del perfil
		profile.setPreferredSize(new Dimension(200, 0));
		profile.setBackground(Color.DARK_GRAY);
		
		// Panel que va a tener el menu desplegable
		JPanel otherThings = new JPanel();
		
		// Button que al clickear te saldran las cosas
		JButton menuButton = new JButton("EXTRA");

		// Ajustamos el tamaño del boton del menu desplegable
		menuButton.setPreferredSize(new Dimension(75, 55));
			
		 // Crear el menú desplegable (JPopupMenu)
        JPopupMenu menuDesplegable = new JPopupMenu();
        
        // Crear las opciones del menú
        JMenuItem opcion1 = new JMenuItem("ACCEDE COMO ADMINISTRADOR");
        opcion1.setBackground(Color.LIGHT_GRAY);
        opcion1.setHorizontalTextPosition(SwingConstants.CENTER);
        JMenuItem opcion2 = new JMenuItem("RUTINAS GUARDADAS");
        opcion2.setBackground(Color.LIGHT_GRAY);
        JMenuItem opcion3 = new JMenuItem("SEGUIMIENTO PROPIO");
        opcion3.setBackground(Color.LIGHT_GRAY);
        JMenuItem opcion4 = new JMenuItem("MIS AMIGOS");
        opcion4.setBackground(Color.LIGHT_GRAY);
        JMenuItem opcion5 = new JMenuItem("AJUSTES");
        opcion5.setBackground(Color.LIGHT_GRAY);
        JMenuItem opcion6 = new JMenuItem("CERRAR SESIÓN");
        opcion6.setBackground(Color.LIGHT_GRAY);
        
        // Añadir las opciones al menú
        menuDesplegable.add(opcion1);
        menuDesplegable.add(opcion2);
        menuDesplegable.add(opcion3);
        menuDesplegable.add(opcion4);
        menuDesplegable.add(opcion5);
        menuDesplegable.addSeparator();
        menuDesplegable.add(opcion6);
        
        // Editar el menu desplegable
        menuDesplegable.setBackground(Color.GRAY);
        menuDesplegable.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
        
        // Obtener el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	// Ajustar el tamaño del popup para que ocupe toda la parte derecha de la pantalla
                menuDesplegable.setPopupSize(screenSize.width / 6, screenSize.height);

                // Mostrar el menú en la esquina superior derecha de la ventana
                menuDesplegable.show(menuButton, menuButton.getWidth() / 2, 0);
            }
        });
        
        // Añadimos al panel el button
        otherThings.add(menuButton, BorderLayout.CENTER);
        profile.add(Box.createVerticalStrut(150));
        profile.add(profileButton, BorderLayout.CENTER);
        
        // Editamos donde va a estar el button dentro del panel
        otherThings.add(Box.createVerticalStrut(150));
		pNorth.add(otherThings, BorderLayout.EAST);
		
		// Editar el tamaño horizontal del panel de la derecha del menú
		otherThings.setPreferredSize(new Dimension(200, 0));
		otherThings.setBackground(Color.DARK_GRAY);
		
		// Creamos el panel donde irá el botón para añadir entrenamientos
		JPanel panelAñadirEntrenamientos = new JPanel();
		panelAñadirEntrenamientos.setBackground(Color.LIGHT_GRAY);
		panelAñadirEntrenamientos.setPreferredSize(new Dimension(0, 200));
		general.add(panelAñadirEntrenamientos, BorderLayout.SOUTH);
		
		// Creamos el botón que va a contener el panel añadirEntrenamientos y lo configuramos
		JButton añadirEntreno = new JButton("AÑADIR ENTRENAMIENTO");
		añadirEntreno.setFont(new Font("Serif", Font.PLAIN, 24));
		añadirEntreno.setPreferredSize(new Dimension(400, 70));
		panelAñadirEntrenamientos.add(añadirEntreno, BorderLayout.CENTER);
		
		// ActionListener al boton para añadir entrenamientos
		añadirEntreno.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
 
            }
		});
		
		// Creamos el panel donde van a ir los entrenamientos que hemos creado
		JPanel entrenamientos = new JPanel();
		entrenamientos.setBackground(Color.LIGHT_GRAY);
		general.add(entrenamientos, BorderLayout.CENTER);
		
		// El jsp que va a tener los entrenamientos
		JScrollPane jsp = new JScrollPane();
		jsp.setPreferredSize(new Dimension(600, 500));
		
		// Nos aseguramos de que lo de bajar aparezca cuando hayan demasiadas cosas dentro: CHATGPT
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		entrenamientos.add(jsp, BorderLayout.CENTER);	
		
		
		
		add(general);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new PrincipalWindow();
	}


}
