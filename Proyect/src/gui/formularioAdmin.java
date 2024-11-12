package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
public class formularioAdmin extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public formularioAdmin(List<String> datos){
		
		
		
		//Detalles de la ventana
		setSize(new Dimension(600, 300));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Panel principal con gridLayout(1,1) para dividir la ventana en 2
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(1,1));
        
        //panelAnadir es el panel del Oeste
        JPanel panelAnadir = new JPanel();
        panelAnadir.setLayout(new BorderLayout());
        panelAnadir.setBorder(new EmptyBorder(20,20,20,20));
        panelAnadir.setBackground(Color.GRAY);
        
        //panelEliminar es el panel del Este
        JPanel panelEliminar = new JPanel();
        panelEliminar.setLayout(new BorderLayout());
        panelEliminar.setBorder(new EmptyBorder(20,20,20,20));
        panelEliminar.setBackground(Color.GRAY);
        
//---------------------------------LABELS SUPERIORES (AÑADIR Y ELIMINAR)--------------------------  
        //anadirSuperior panel que contiene el Label y esta en el norte de panelAnadir
        JPanel anadirSuperior = new JPanel();
        anadirSuperior.setLayout(new FlowLayout());
        anadirSuperior.setBackground(Color.green);
        
      //eliminarSuperior panel que contiene el Label y esta en el norte de panelEliminar
        JPanel eliminarSuperior = new JPanel();
        eliminarSuperior.setLayout(new FlowLayout());
        eliminarSuperior.setBackground(Color.red);
        
        JLabel añadir = new JLabel("AÑADIR");
        anadirSuperior.add(añadir);
           
        JLabel eliminar = new JLabel("ELIMINAR");
        eliminarSuperior.add(eliminar);

//-------------------------CAMPO ESCRIBIR NUEVO EJERCICIO----------------------------------
        //Nuevo panel que va en el centro de panelAnadir
        JPanel panelElementosAñadir = new JPanel();
        panelElementosAñadir.setLayout(new GridLayout(3,1, 0, 30));
        panelElementosAñadir.setBorder(new EmptyBorder(10,10,10,10));
        
        JLabel introduceNombre = new JLabel("Introduce el nombre del ejericio");
        JTextField campoNombre = new JTextField(10);
        JButton botonAñadir = new JButton("Confirmar");

        
        
        //Action listener para el botonAceptar
        botonAñadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Si esta en la lista, no se añade
				String texto = campoNombre.getText();
				if(!texto.isEmpty()) {
					if(datos.contains(texto)) {
						JOptionPane.showMessageDialog
						(formularioAdmin.this, "Este ejercicio ya se encuentra registrado");
					}else {
						//
					}
				}
				}
		});
        
        //Añadimos al panel
        panelElementosAñadir.add(introduceNombre);
        panelElementosAñadir.add(campoNombre);
        panelElementosAñadir.add(botonAñadir);
 
        panelAnadir.add(panelElementosAñadir, BorderLayout.CENTER);
       
//-------------------------CAMPO ELIMINAR EJERCICIO----------------------------------        
        JPanel panelElementosEliminar = new JPanel();
        panelElementosEliminar.setLayout(new GridLayout(3,1, 0, 30));
        panelElementosEliminar.setBorder(new EmptyBorder(10,10,10,10));

        JLabel introduceNombreEliminar = new JLabel("Introduce el nombre del ejericio");
        JTextField campoNombreEliminar = new JTextField(10);
        JButton botonEliminar = new JButton("Confirmar");

        
        panelElementosEliminar.add(introduceNombreEliminar);
        panelElementosEliminar.add(campoNombreEliminar);
        panelElementosEliminar.add(botonEliminar);
        
        panelEliminar.add(panelElementosEliminar, BorderLayout.CENTER);
        
        //Añadimos las partes superiores a los paneles
        panelAnadir.add(anadirSuperior, BorderLayout.NORTH);
        panelEliminar.add(eliminarSuperior, BorderLayout.NORTH);
        
        //Añadimos los dos paneles al principal
        panelPrincipal.add(panelAnadir);
        panelPrincipal.add(panelEliminar);
        //Detalles finales de la ventana
        add(panelPrincipal);
        setVisible(true);
		
	}
}
