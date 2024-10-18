package classes;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.SwingUtilities;

public class PrincipalWindow extends JFrame{
	
	public PrincipalWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.black);
		pNorth.setPreferredSize(new Dimension(0, 150));
		add(pNorth, BorderLayout.NORTH);
		
		JPanel profile = new JPanel();
		pNorth.setLayout(new BorderLayout());
		pNorth.add(profile, BorderLayout.WEST);
		profile.setPreferredSize(new Dimension(200, 0));
		profile.setBackground(Color.DARK_GRAY);
		
		//Panel que va a tener el menu desplegable
		JPanel otherThings = new JPanel();
		
		//Button que al clickear te saldran las cosas
		JButton menuButton = new JButton("EXTRA");
			
		 // Crear el menú desplegable (JPopupMenu)
        JPopupMenu menuDesplegable = new JPopupMenu();
        
        // Crear las opciones del menú
        JMenuItem opcion1 = new JMenuItem("Opción 1");
        JMenuItem opcion2 = new JMenuItem("Opción 2");
        JMenuItem opcion3 = new JMenuItem("Opción 3");

        // Añadir las opciones al menú
        menuDesplegable.add(opcion1);
        menuDesplegable.add(opcion2);
        menuDesplegable.add(opcion3);
        
        //Obtener el tamaño de la pantalla
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
        
        //Añadimos al panel el button
        otherThings.add(Box.createVerticalStrut(150));
        otherThings.add(menuButton, BorderLayout.CENTER);
        
        
		pNorth.add(otherThings, BorderLayout.EAST);
		otherThings.setPreferredSize(new Dimension(200, 0));
		otherThings.setBackground(Color.DARK_GRAY);
		
		JPanel pBody = new JPanel();
		pBody.setBackground(Color.white);
		add(pBody);
		
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InicioSesion().setVisible(true);
            }
        });
	}
	

}
