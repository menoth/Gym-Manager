package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import domain.Ejercicio;
import domain.Serie;
import domain.Usuario;




public class CatalogoEjercicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField campo_busqueda;
    private JButton boton_buscar;
    private DefaultListModel<String> lista;

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
   
	public CatalogoEjercicio(String usuario, String nombreRutina) {
		
		
		
		//Detalles de la ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        
        //Panel Superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(Color.DARK_GRAY);
        panelSuperior.setLayout(new BorderLayout());
        
        // Margenes para el panelNorte
     	panelSuperior.setBorder(new EmptyBorder(45, 40, 45, 40)); 		
        
        //Boton volver en la parte oeste del panelSuperior
        JButton botonVolver = new JButton("Atrás");
        botonVolver.setPreferredSize(new Dimension(140, 10));
        
        //Se añade al panel
        panelSuperior.add(botonVolver, BorderLayout.WEST);
        
        //Al hacer click, vuelves a la interfaz rutina
        botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new InterfazRutina(usuario, nombreRutina);
				
			}
		});
        
//-------------------------------------Boton de administrador----------------------------------------

        
        //Detalles del botón
        JButton botonAdmin = new JButton("Administrador");
        botonAdmin.setPreferredSize(new Dimension(140, 10));
        
        //Se añade al panel
        panelSuperior.add(botonAdmin, BorderLayout.EAST);
        
        //Action listener del boton
        if(usuario.equals("admin")) {
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
      	panelBusqueda.setBackground(Color.DARK_GRAY);
      	     
      	panelBusqueda.add(campo_busqueda);
        panelBusqueda.add(boton_buscar);
       
        panelSuperior.add(panelBusqueda);
        
 
      	
     
        
//----------------------"CATÁLOGO"-----------------------------------------------------------------
     	
      	//Panel contenedor que se extiende hacia abajo paraaplicar el JScrollPane 
      	JPanel contenedorCatalogo = new JPanel();
      	contenedorCatalogo.setBackground(Color.LIGHT_GRAY);
      	contenedorCatalogo.setLayout(new BoxLayout(contenedorCatalogo, BoxLayout.Y_AXIS));
      	contenedorCatalogo.setBorder(new EmptyBorder(10,10,10,10));
      	
      	//Panel donde van a ir los ejercicios
      	JPanel gridPrincipal = new JPanel();
      	gridPrincipal.setBackground(Color.LIGHT_GRAY);
      	
      	//GridLayout al panel => filas, columnas, espacio entre columnas, espacio entre filas
      	gridPrincipal.setLayout(new GridLayout(0, 5, 20, 100));
      	
      	
      	//Añadimos botones por cada dato de la lista
      	for (String string : datos) {
      		
      		JButton b = new JButton("+" + string);
      		
      		b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Crear el diálogo
				    JDialog dialog = new JDialog();
				    dialog.setLayout(new BorderLayout());
				    dialog.setSize(300, 200);
				    dialog.setLocation(620, 350);
				    

				    // Crear el panel principal del diálogo
				    JPanel panelContenido = new JPanel();
				    panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
				    panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

				    // Crear los componentes
				    JLabel label = new JLabel("Ingrese cuantas repeticiones:");
				    JTextField textField = new JTextField(10);
				    JLabel label2 = new JLabel("Ingrese el esfuerzo que se va a realizar:");
				    
				    JRadioButton radioButton1 = new JRadioButton("Aprox.");
			        JRadioButton radioButton2 = new JRadioButton("Estandar");
			        JRadioButton radioButton3 = new JRadioButton("Topset");

			        ButtonGroup radioButtonGroup = new ButtonGroup();
			        radioButtonGroup.add(radioButton1);
			        radioButtonGroup.add(radioButton2);
			        radioButtonGroup.add(radioButton3);
			        
			        JPanel radioButtonPanel = new JPanel();
			        radioButtonPanel.add(radioButton1);
			        radioButtonPanel.add(radioButton2);
			        radioButtonPanel.add(radioButton3);

				    // Botones de acción
				    JPanel panelBotones = new JPanel();
				    JButton btnAceptar = new JButton("Aceptar");
				    JButton btnCancelar = new JButton("Cancelar");
				    
				    btnAceptar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							dialog.dispose();
							
						}
				    	
				    });
					
				    btnCancelar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							dialog.dispose();
							
						}
				    	
				    });
				    
				    label.setAlignmentX(Component.CENTER_ALIGNMENT);
				    textField.setAlignmentX(Component.CENTER_ALIGNMENT);
				    label2.setAlignmentX(Component.CENTER_ALIGNMENT);
				    radioButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


				    // Añadir componentes al panel principal
				    panelContenido.add(label);
				    panelContenido.add(textField);
				    panelContenido.add(label2);
				    panelContenido.add(radioButtonPanel);

				    // Añadir botones al panel de botones
				    panelBotones.add(btnAceptar);
				    panelBotones.add(btnCancelar);

				    // Añadir todo al diálogo
				    dialog.add(panelContenido, BorderLayout.CENTER);
				    dialog.add(panelBotones, BorderLayout.SOUTH);

				    // Mostrar el diálogo
				    dialog.setVisible(true);
				}
      			
      		});
			gridPrincipal.add(b);
		}
      	
      	//Añadimos el gridPrincipal al panel contenedor
      	contenedorCatalogo.add(gridPrincipal);
      	
        //ScrollPane vertical
      	JScrollPane scrollPane = new JScrollPane(contenedorCatalogo);
      	
      	//Separar un poco la barra de la parte de la izquierda
      	scrollPane.setBorder(new EmptyBorder(0,0,0,2));
      	      	      	      	
        //Añadimos el panel superior en la parte norte del panel
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        
        //Añadimos el gridPrincipal(catalogo) en la parte central del Panel
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        //Añadimos el frame a la ventana
        add(panelPrincipal);
        
        setVisible(true);

	}
	
	//-----------------------------------MÉTODOS-------------------------------------------------------------------------------------------
}
