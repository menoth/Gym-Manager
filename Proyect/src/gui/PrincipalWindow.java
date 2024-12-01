	package gui;

import java.awt.BorderLayout;

import java.awt.Color;


import java.awt.Dimension;
import java.awt.Font;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


import javax.swing.BorderFactory;
import javax.swing.Box;


import javax.swing.DefaultListModel;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JList;


import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


import domain.Usuario;
import javax.swing.JPopupMenu;

import javax.swing.SwingConstants;


public class PrincipalWindow extends JFrame {
	
	private JTextField campo_busqueda;
    private JButton boton_buscar;
    private DefaultListModel<String> lista;
    private JPopupMenu popUpMenu;

    // Lista con los datos a buscar
    private List<String> datos = new ArrayList<>();
	private static final long serialVersionUID = 1L;

	public PrincipalWindow(String usuario) {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		// Ahora el cierre pasa por un menú antes de cerrarse
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// Creamos el panel general 
		JPanel general = new JPanel();
		general.setLayout(new BorderLayout());
		
		// Panel donde va a ir el perfil, el buscador y el menu desplegable
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.GRAY);
		
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
		profile.setBackground(Color.GRAY);
		
		// Añadimos el button al panel del perfil
		profile.add(Box.createVerticalStrut(150));
        profile.add(profileButton, BorderLayout.CENTER);
        
		// Hacer que el botón "PROFILE" salte a la ventana perfil de usuario
        profileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Hacemos que la ventana principal no se vea
				setVisible(false);
				PerfilUsuario perfilUsuario = new PerfilUsuario(usuario);
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
        opcion1.setBackground(new Color(176,224,230));
        opcion1.setHorizontalTextPosition(SwingConstants.CENTER);
        JMenuItem opcion2 = new JMenuItem("RUTINAS GUARDADAS");
        opcion2.setBackground(new Color(176,224,230));
        JMenuItem opcion3 = new JMenuItem("SEGUIMIENTO PROPIO");
        opcion3.setBackground(new Color(176,224,230));
        JMenuItem opcion4 = new JMenuItem("MIS AMIGOS");
        opcion4.setBackground(new Color(176,224,230));
        JMenuItem opcion5 = new JMenuItem("AJUSTES");
        opcion5.setBackground(new Color(176,224,230));
        JMenuItem opcion6 = new JMenuItem("CERRAR SESIÓN");
        opcion6.setBackground(new Color(176,224,230));
        
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
		otherThings.setBackground(Color.GRAY);
		
        // Añadimos al panel el button del menú desplegable
        otherThings.add(menuButton, BorderLayout.CENTER);
        
        // Editamos donde va a estar el menú desplegable dentro del panel
        otherThings.add(Box.createVerticalStrut(150));
		pNorth.add(otherThings, BorderLayout.EAST);
		
		// Añadimos todo a la ventana
		general.add(pNorth, BorderLayout.NORTH);
		
		//----------------------------PANEL CON EL BOTÓN DE AÑADIR RUTINA------------------------------------------------------------------------
		
		// Creamos el panel donde irá el botón para añadir entrenamientos
		JPanel panelAñadirEntrenamientos = new JPanel();
		panelAñadirEntrenamientos.setBackground(Color.LIGHT_GRAY);
		panelAñadirEntrenamientos.setPreferredSize(new Dimension(0, 200));
		
		// Creamos el botón que va a contener el panel añadirEntrenamientos y lo configuramos
		JButton añadirEntreno = new JButton("AÑADIR RUTINA");
		añadirEntreno.setFont(new Font("Serif", Font.PLAIN, 24));
		añadirEntreno.setPreferredSize(new Dimension(400, 70));
		panelAñadirEntrenamientos.add(añadirEntreno, BorderLayout.CENTER);
		
		
		//Interfaz para pedir nombre y descripción de la rutina
		añadirEntreno.addActionListener(e -> new nombreRutinaInterfaz(PrincipalWindow.this, usuario));

		
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
		new JList<>(lista);
		
		 JPanel panelBusqueda = new JPanel();
		 panelBusqueda.add(Box.createVerticalStrut(150));
		 popUpMenu = new JPopupMenu();
	     
	     panelBusqueda.add(campo_busqueda);
	     panelBusqueda.add(boton_buscar);
	     panelBusqueda.setBackground(Color.BLACK);
	     pNorth.add(panelBusqueda, BorderLayout.CENTER);
	     
		    
		 //Ponemos en el buscador todos los nombres de usuario que haya en la base de datos
	     datosUsuario((ArrayList<String>) datos);
	   	 
	   	 //Listeners para el popUpMenu
	   	 campo_busqueda.getDocument().addDocumentListener(new DocumentListener() {
	   		 
	   		 //Listener para que cada vez que se escriba se llame al método
             @Override
             public void insertUpdate(DocumentEvent e) {
                 actualizarResultados();
             }
             
             //Listener para que cada vez que se borre llame al método
             @Override
             public void removeUpdate(DocumentEvent e) {
                 actualizarResultados();
             }
             
             //Listener que omitimos pero es necesario para completar el método DocumentListener
             @Override
             public void changedUpdate(DocumentEvent e) {
                 actualizarResultados();
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
	
	
	//Método para el buscador
	protected void actualizarResultados() {
		
		//Recoge lo que se ha escrito en campo_busqueda 
		//El trim se utiliza para eliminar espacios al principio y al final
		String textoBusqueda = campo_busqueda.getText().trim();
		
		//Si se llama a actualizarResultados(), siginifica que se ha hecho una modificación
		//en el campo_busqueda por lo que antes de mostrar nuevos resultados hay que eliminar 
		//el popUp anterior
		popUpMenu.removeAll();
		
		//Primero metemos en la lista resultados los perfiles que coincidan
		if(!textoBusqueda.isEmpty()) {
			 List<String> resultados = new ArrayList<>();
	            for (String item : datos) {
	                if (item.toLowerCase().contains(textoBusqueda.toLowerCase())) {
	                    resultados.add(item);
	                }
	            }
	     
		//
		if(!textoBusqueda.isEmpty()) {
			for (String resultado : resultados) {
				
				//Por cada resultado se crea un JMenuItem
                JMenuItem menuItem = new JMenuItem(resultado);
                
                //Este listener lo que hace es cerrar el menú cuando se hace click
                //en un perfil del popUp. Esto hay que conectarlo con llevarle a la pagina de ese perfil.
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
        
                        popUpMenu.setVisible(false); 
                    }
                });
                
                //Añadir cada resultado al menú
                popUpMenu.add(menuItem);
		}
			 //Mostrar el popup justo debajo del campo de búsqueda
            popUpMenu.show(campo_busqueda, 0, campo_busqueda.getHeight());
            
            //Sin esto cada vez que se escribe/borra una letra hay que volver a hacer 
            //click (seleccionar) el JTextField para seguir escribiendo
            campo_busqueda.requestFocusInWindow();
        } 
		// Ocultar si no hay resultados
		else {
            popUpMenu.setVisible(false); 
        }
    }
		//Ocultar si no hay texto de búsqueda
		else {
        popUpMenu.setVisible(false); 
		}
	}
	

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


     //Metodo para añadir todos los usuarios a el PopUpMenu
     private void datosUsuario(ArrayList<String> datos) {
    	 File f = new File("baseDeDatos.csv");
    	 try (Scanner sc = new Scanner (f)) {
    		 while(sc.hasNextLine()) {
    			 String linea = sc.nextLine();
    			 String[] campos = linea.split(";");
    			 String usuario = campos[2];
    			 
    			 datos.add(usuario);
    		 }
		} catch (Exception e) {
			// TODO: handle exception
		}
	 }

     public static void main(String[] args) {
    	 Usuario user = new Usuario("Prueba", "Prueba", "Prueba", "Prueba", "Prueba", "Prueba", "Prueba");
    	
         PrincipalWindow pw = new PrincipalWindow(user.getUsuario());
         pw.setExtendedState(JFrame.MAXIMIZED_BOTH);
     }
     
}
