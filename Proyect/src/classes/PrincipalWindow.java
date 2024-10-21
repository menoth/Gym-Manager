package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class PrincipalWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrincipalWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.black);
		
		// Editar el tamaño vertical del panel de arriba
		pNorth.setPreferredSize(new Dimension(0, 150));
		add(pNorth, BorderLayout.NORTH);
		
		JPanel profile = new JPanel();
		pNorth.setLayout(new BorderLayout());
		pNorth.add(profile, BorderLayout.WEST);
		
		// Editar el tamaño horizontal del panel del perfil
		profile.setPreferredSize(new Dimension(200, 0));
		profile.setBackground(Color.DARK_GRAY);
		
		// Panel que va a tener el menu desplegable
		JPanel otherThings = new JPanel();
		
		// Button que al clickear te saldran las cosas
		JButton menuButton = new JButton("EXTRA");
		
		// Ajustamos el tamaño del button 
		menuButton.setPreferredSize(new Dimension(75, 55));
			
		 // Crear el menú desplegable (JPopupMenu)
        JPopupMenu menuDesplegable = new JPopupMenu();
        
        // Crear las opciones del menú
        JMenuItem opcion1 = new JMenuItem("ACCEDE COMO ADMINISTRADOR");
        opcion1.setBackground(Color.LIGHT_GRAY);
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
        
        // Editamos donde va a estar el button dentro del panel
        otherThings.add(Box.createVerticalStrut(150));
		pNorth.add(otherThings, BorderLayout.EAST);
		
		// Editar el tamaño horizontal del panel de la derecha del menú
		otherThings.setPreferredSize(new Dimension(200, 0));
		otherThings.setBackground(Color.DARK_GRAY);
		
		JPanel pBody = new JPanel();
		pBody.setBackground(Color.white);
		add(pBody);
		
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		PrincipalWindow pw = new PrincipalWindow();
		pw.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	

}
