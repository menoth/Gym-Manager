package classes;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPopupMenu;


public class PrincipalWindow extends JFrame {
	
	private JTextField campo_busqueda;
    private JButton boton_buscar;
    private JList<String> lista_resultados;
    private DefaultListModel<String> lista;

    // Lista con los datos a buscar
    private List<String> datos = new ArrayList<>();
	private static final long serialVersionUID = 1L;

	private JFrame previous;
	
	public PrincipalWindow() {
		
		setSize(640, 480);
		setVisible(true);
		
		//Ahora el cierre pasa por un menú antes de cerrarse
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//Panel donde va a ir el perfil, el buscador y el menu desplegable
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.black);
		
		// Editar el tamaño vertical del panel de arriba
		pNorth.setPreferredSize(new Dimension(0, 150));
		pNorth.setLayout(new BorderLayout());
		add(pNorth, BorderLayout.NORTH);
		
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
        
		//El cuerpo donde va a ir lo de añadir entrenamientos
		JPanel pBody = new JPanel();
		pBody.setBackground(Color.white);
		add(pBody);
		
		//Buscador
		setVisible(true);
		campo_busqueda = new JTextField(20);
		boton_buscar = new JButton("Buscar");
		lista = new DefaultListModel<>();
		lista_resultados = new JList<>(lista);
		
		 JPanel panelBusqueda = new JPanel();
	     
	     pNorth.add(panelBusqueda, BorderLayout.CENTER);
	     panelBusqueda.add(campo_busqueda);
	     panelBusqueda.add(boton_buscar);
	     panelBusqueda.setBackground(Color.BLACK);
	     
		 add(new JScrollPane(lista_resultados), BorderLayout.CENTER);
		    
		 //Datos prueba para el buscador
		 datos.add("Perfil 1");
		 datos.add("Perfil 2");
		 datos.add("Perfil 3");
	   	 datos.add("Perfil 4");

   	// Acción del botón buscar
         boton_buscar.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 buscar();
             }
         });

         // Acción para buscar cuando se presiona Enter en el campo de texto
         campo_busqueda.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 buscar();
             }
         });
         
         //WindowListener para cerrar aplicación
         addWindowListener(new WindowAdapter() { 
			 	@Override 
			 	public void windowClosing(WindowEvent e) { 
			 	 	confirmarSalida();
			 	} 
		 
			}); 
     }

	//Dialogo para cerrar la aplicación
	private void confirmarSalida() {
		int respuesta = JOptionPane.showConfirmDialog(
				this,
				"¿Desea cerrar sesión?",
				"Confirmar salida",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if(respuesta == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
     // Método para realizar la búsqueda
     private void buscar() {
         String textoBusqueda = campo_busqueda.getText().toLowerCase();
         lista.clear(); // Limpiar los resultados previos

         for (String dato : datos) {
             if (dato.toLowerCase().contains(textoBusqueda)) {
                 lista.addElement(dato); // Agregar coincidencias a la lista
             }
         }

         // Si no hay resultados, mostrar un mensaje
         if (lista.isEmpty()) {
             lista.addElement("No se encontraron resultados");
         }
     }

     public static void main(String[] args) {
         PrincipalWindow pw = new PrincipalWindow();
         pw.setExtendedState(JFrame.MAXIMIZED_BOTH);
     }
}
