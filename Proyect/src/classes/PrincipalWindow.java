package classes;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;



import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.DefaultListModel;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;


import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import gui.main.MainProyecto;

import javax.swing.JPopupMenu;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;


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
		
		
		// Ahora el cierre pasa por un menú antes de cerrarse
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// Creamos el panel general 
		JPanel general = new JPanel();
		general.setLayout(new BorderLayout());
		
		// Panel donde va a ir el perfil, el buscador y el menu desplegable
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.black);
		
		// Editar el tamaño vertical del panel de arriba
		pNorth.setPreferredSize(new Dimension(0, 150));
		
		//--------------------------BOTÓN DE PERFÍL------------------------------------------------------------------------
		
		// Creamos el panel donde va a ir el PERFIL
		JPanel profile = new JPanel();
		pNorth.setLayout(new BorderLayout());
		pNorth.add(profile, BorderLayout.WEST);
		
		//Button para cambiar a la ventana "Perfil de usuario"
		JButton profileButton = new JButton("PROFILE");
		
		// Editar el tamaño horizontal del panel del perfil
		profile.setPreferredSize(new Dimension(200, 0));
		profile.setBackground(Color.DARK_GRAY);
		
		// Añadimos el button al panel del perfil
		profile.add(Box.createVerticalStrut(150));
        profile.add(profileButton, BorderLayout.CENTER);
        
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
		
        //-------------------------MENU DESPLEGABLE--------------------------------------------------------------------------
        
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
        
        //??
        opcion6.addActionListener(e -> confirmarSalidaSesion());
        
        // Editar el menu desplegable
        menuDesplegable.setBackground(Color.GRAY);
        menuDesplegable.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
        
        // Obtener el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Añadimos un mouseListener para cuando se haga click aparezca el menú
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	// Ajustar el tamaño del popup para que ocupe toda la parte derecha de la pantalla
                menuDesplegable.setPopupSize(screenSize.width / 6, screenSize.height);

                // Mostrar el menú en la esquina superior derecha de la ventana
                menuDesplegable.show(menuButton, menuButton.getWidth() / 2, 0);
            }
        });
        
        // Editar el tamaño horizontal del panel de la derecha del menú
		otherThings.setPreferredSize(new Dimension(200, 0));
		otherThings.setBackground(Color.DARK_GRAY);
		
        // Añadimos al panel el button del menú desplegable
        otherThings.add(menuButton, BorderLayout.CENTER);
        
        // Editamos donde va a estar el menú desplegable dentro del panel
        otherThings.add(Box.createVerticalStrut(150));
		pNorth.add(otherThings, BorderLayout.EAST);
		
		// Añadimos todo a la ventana
		general.add(pNorth, BorderLayout.NORTH);
		
		//----------------------------PANEL CON EL BOTÓN DE AÑADIR ENTRENAMIENTIOS------------------------------------------------------------------------
		
		// Creamos el panel donde irá el botón para añadir entrenamientos
		JPanel panelAñadirEntrenamientos = new JPanel();
		panelAñadirEntrenamientos.setBackground(Color.LIGHT_GRAY);
		panelAñadirEntrenamientos.setPreferredSize(new Dimension(0, 200));
		
		// Creamos el botón que va a contener el panel añadirEntrenamientos y lo configuramos
		JButton añadirEntreno = new JButton("AÑADIR ENTRENAMIENTO");
		añadirEntreno.setFont(new Font("Serif", Font.PLAIN, 24));
		añadirEntreno.setPreferredSize(new Dimension(400, 70));
		panelAñadirEntrenamientos.add(añadirEntreno, BorderLayout.CENTER);
		
		// AÑADIR ACTION LISTENER (***TAREA***)
		/**
		añadirEntreno.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
 
            }
		});
		**/
		
		// Añadimos el panel que va a contener el botón de añadir entrenamientos
		general.add(panelAñadirEntrenamientos, BorderLayout.SOUTH);
		
		//--------------------TABLA DONDE VAN LOS ENTRENAMIENTOS-----------------------------------------------
		
		// Creamos el panel donde van a ir los entrenamientos que hemos creado
		JPanel entrenamientos = new JPanel();
		entrenamientos.setBackground(Color.LIGHT_GRAY);
		
		// El jsp que va a tener los entrenamientos
		JScrollPane jsp = new JScrollPane();
		jsp.setPreferredSize(new Dimension(600, 500));
		
		// Nos aseguramos de que lo de bajar aparezca cuando hayan demasiadas cosas dentro: CHATGPT
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		entrenamientos.add(jsp, BorderLayout.CENTER);
		
		// Añadimos el panel donde estarán los entrenamientos
		general.add(entrenamientos, BorderLayout.CENTER);
		
		//------------------------BUSCADOR----------------------------------------------------------------------------
		
		campo_busqueda = new JTextField(20);
		boton_buscar = new JButton("Buscar");
		lista = new DefaultListModel<>();
		lista_resultados = new JList<>(lista);
		
		 JPanel panelBusqueda = new JPanel();
		 panelBusqueda.add(Box.createVerticalStrut(150));
	     
	     panelBusqueda.add(campo_busqueda);
	     panelBusqueda.add(boton_buscar);
	     panelBusqueda.setBackground(Color.BLACK);
	     pNorth.add(panelBusqueda, BorderLayout.CENTER);
	     /**
	     if(buscar()) {
	    	 general.add(new JScrollPane(lista_resultados), BorderLayout.CENTER);
	     }
		 **/
		    
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
         
         //Añadir todo y hacerlo visible
         add(general);
 		 setVisible(true);
     }
	
	//------------------------------MÉTODOS-------------------------------------------------------------------

	//Dialogo para salir mediante el botón x
	private void confirmarSalida() {
		int respuesta = JOptionPane.showConfirmDialog(
				this,
				"¿Desea salir? Si lo hace se cerrará su sesión.",
				"Confirmar salida",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if(respuesta == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	//Dialogo para cerrar sesión mediante el botón de cerrar sesión
	private void confirmarSalidaSesion() {
		int respuesta = JOptionPane.showConfirmDialog(
				this,
				"¿Desea cerrar sesión?",
				"Cerrar sesión",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if(respuesta == JOptionPane.YES_OPTION) {
			dispose();
			openMainProyecto();
		}
	}
	
     private static void openMainProyecto() {
    	 InicioSesion inicioSesion = new InicioSesion();
    	 inicioSesion.setVisible(true);
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
