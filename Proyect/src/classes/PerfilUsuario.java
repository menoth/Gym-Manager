package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PerfilUsuario extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PerfilUsuario() {
		
		//Detalles ventana
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Perfil");
		
		Usuario ejemploUsuario = new Usuario
				("Nombre", "Apellido 1 Apellido 2", "admin12", "ejemplo@gmail.com", "1234");
		
		//Primer layout
		setLayout(new BorderLayout());
		
		//Añadimos el panel en el norte y lo dividimos en 1 fila y 6 columnas
		JPanel panelNorte = new JPanel();
		panelNorte.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
		
		//Margenes para el panelNorte
		panelNorte.setBorder(new EmptyBorder(60, 20, 10, 10)); 		
		
		//Botón para volver a la ventana principal
		JButton botonPrincipal = new JButton("VOLVER");
		botonPrincipal.setPreferredSize(new Dimension(100, 40));
		panelNorte.add(botonPrincipal);
		
		//Label que simula la foto de perfil
		JLabel fotoPerfil = new JLabel("");
		fotoPerfil.setPreferredSize(new Dimension(130, 130));
		fotoPerfil.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelNorte.add(fotoPerfil);
		
		//Label del nombre y apellidos hecho con HTML para poder hacerlo en dos lineas
		String texto = "<html><b>" + ejemploUsuario.getNombre() + "</b><br>"
                + ejemploUsuario.getApellidos() + "</b><br>"
                + "@<b>" + ejemploUsuario.getUsuario() + "</b></html>";
			 
		JLabel nombreApellidos = new JLabel(texto);
		
		//Cambiamos el tamaño de la fuente
		nombreApellidos.setFont(new Font("Arial",Font.PLAIN ,18));
		panelNorte.add(nombreApellidos);
		
		//Añadimos el panelNorte
		add(panelNorte, BorderLayout.NORTH);
		
		//Listener para volver a la ventana principal cuando se presiona el
		//botón volver
		botonPrincipal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                PrincipalWindow principal = new PrincipalWindow();
                principal.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
				
		//Detalles ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
	}
	
}
