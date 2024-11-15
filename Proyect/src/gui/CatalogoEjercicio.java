package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import domain.Ejercicio;
import domain.Entrenamiento;
import domain.Usuario;



public class CatalogoEjercicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField campo_busqueda;
    private JButton boton_buscar;
    private DefaultListModel<String> lista;
    private JPopupMenu popUpMenu;

    // Lista con los datos a buscar

	
		List<String> datos = List.of(
        	    "Stair Climbing", "Elliptical Trainer", "Rowing Machine", "Stairmaster", "Stationary Bicycle",
        	    "Treadmill", "Cycling", "Inline Skating", "Jogging", "Nordic Walking", "Squat", "Leg Press",
        	    "Lunge", "Calf Raise", "Leg Extension", "Deadlift", "Leg Curl", "Bench Press", "Fly", "Push Up",
        	    "Bent-over Row", "Chin-up", "Pulldown", "Pullup", "Shoulder Shrug", "Front Raise", 
        	    "Handstand Pushup", "Lateral Raise", "Military Press", "Shoulder Press", "Upright Row", 
        	    "Rear Delt Raise", "Biceps Curl", "Dip", "Pushdown", "Triceps Extension", "Crunch", "Sit-up", 
        	    "Leg Raise", "Back Extension", "Hyperextension", "Basketball", "Baseball", "Football", "Hockey", 
        	    "Golf", "Water Polo", "Swimming", "Skiing", "Skateboarding", "Hang Gliding", "Snowboarding", 
        	    "Fishing", "Hunting", "Paintball", "Kayaking", "Hiking", "Tennis", "Canoeing", "Kitesurfing", 
        	    "Gymnastics", "Bowling", "Boxing", "Auto racing", "Skating", "Squash", "Rodeo", "Volleyball", 
        	    "Soccer", "Darts", "Table Tennis", "Cricket", "Skydiving", "Aquafit", "Belly Dancing", 
        	    "Bootcamp Conditioning", "Cardio Jam", "Cycle Zone", "Hip Hop", "Kickbox Cardio", "Club Boxing", 
        	    "Mat Pilates", "Senior Fit", "Step plus Abs", "Step II plus Abs", "Step Circuit", "Step Sculpt", 
        	    "Striding", "Yoga", "Yoga Basics"
        	);
   
	public CatalogoEjercicio() {
		
		
		
		//Detalles de la ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        
        //Panel Superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        // Margenes para el panelNorte
     	panelSuperior.setBorder(new EmptyBorder(45, 40, 45, 40)); 		
        
        //Boton volver en la parte oeste del panelSuperior
        JButton botonVolver = new JButton("Atrás");
        botonVolver.setPreferredSize(new Dimension(140, 10));
        panelSuperior.add(botonVolver, BorderLayout.WEST);
        
        //
        botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new InterfazRutina();
				
			}
		});
        
//-------------------------------------Boton de administrador----------------------------------------
        Usuario usuario = new Usuario("admin", "admin", "admin", "admin", "admin", "a", "a");
        
        //Detalles del botón
        JButton botonAdmin = new JButton("Administrador");
        botonAdmin.setPreferredSize(new Dimension(140, 10));
        panelSuperior.add(botonAdmin, BorderLayout.EAST);
        
        //Action listener del boton
        if(usuario.getUsuario().equals("admin")) {
        	botonAdmin.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new formularioAdmin(datos);
					
				}
			});
        }
        
        
      //------------------------BUSCADOR----------------------------------------------------------------------------
        
      	campo_busqueda = new JTextField();
      	campo_busqueda.setPreferredSize(new Dimension(500, 30));
      	boton_buscar = new JButton("Buscar");
      	boton_buscar.setPreferredSize(new Dimension(140, 40));
      	lista = new DefaultListModel<>();
      	new JList<>(lista);
      		
      	JPanel panelBusqueda = new JPanel();
    	panelSuperior.add(Box.createVerticalStrut(150));
      	popUpMenu = new JPopupMenu();
      	     
      	panelBusqueda.add(campo_busqueda);
        panelBusqueda.add(boton_buscar);
       
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);
        
        //Datos de prueba de entrenamientos
  
      	   	 
      	//Listeners para el popUpMenu
      	campo_busqueda.getDocument().addDocumentListener(new DocumentListener() {
      	   		 
	      	//Listener para que cada vez que se escriba se llame al método
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	//actualizarResultados();
	        	}
	                   
	        //Listener para que cada vez que se borre llame al método
	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        		//actualizarResultados();
	        	}	
	        
	        //Listener que omitimos pero es necesario para completar el método DocumentListener
	        @Override
	        public void changedUpdate(DocumentEvent e) {
	        		//actualizarResultados();
	            }
        });
      	
     // Agregar ActionListener al botón "Buscar" para llamar a actualizarResultados
      	boton_buscar.addActionListener(new ActionListener() {
      	    @Override
      	    public void actionPerformed(ActionEvent e) {
      	        // Llamamos a actualizarResultados al hacer clic en el botón Buscar
      	        actualizarResultados(datos);
      	    }
      	});
        
//----------------------"CATÁLOGO"-----------------------------------------------------------------
     	//Panel contenedor que se extiende hacia abajo paraaplicar el JScrollPane 
      	JPanel contenedorCatalogo = new JPanel();
      	contenedorCatalogo.setLayout(new BoxLayout(contenedorCatalogo, BoxLayout.Y_AXIS));
      	contenedorCatalogo.setBorder(new EmptyBorder(10,10,10,10));
      	
      	//Nuevo gridLayout
      	JPanel gridPrincipal = new JPanel();
      	gridPrincipal.setLayout(new GridLayout(0, 5, 20, 100));
      	
      	for (String string : datos) {
			gridPrincipal.add(new JButton("+ "+string));
		}
      	
      	//Añadimos el gridPrincipal al panel contenedor
      	contenedorCatalogo.add(gridPrincipal);
      	
      //ScrollPane vertical
      	JScrollPane scrollPane = new JScrollPane(contenedorCatalogo);
      	scrollPane.setBorder(new EmptyBorder(0,10,0,2));
      	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      	
      	
        //Añadimos el panel superior en la parte norte del panel
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        
        //Añadimos el gridPrincipal(catalogo) en la parte central del Panel
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        //Añadimos el frame a la ventana
        add(panelPrincipal);
        
        setVisible(true);

	}

		//Método para el buscador
		protected void actualizarResultados(List<String> datos) {
			
			
			
			//Recoge lo que se ha escrito en campo_busqueda 
			//El trim se utiliza para eliminar espacios al principio y al final
			String textoBusqueda = campo_busqueda.getText().trim();
					
			//Si se llama a actualizarResultados(), siginifica que se ha hecho una modificación
			//en el campo_busqueda por lo que antes de mostrar nuevos resultados hay que eliminar 
			//el popUp anterior
			popUpMenu.removeAll();
					
			//Primero metemos en la lista resultados los entrenamientos que coincidan
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

	public static void main(String[] args) {
		new CatalogoEjercicio();
	}
}
