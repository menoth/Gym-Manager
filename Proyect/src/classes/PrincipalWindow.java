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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PrincipalWindow extends JFrame{
	
	private JTextField campo_busqueda;
    private JButton boton_buscar;
    private JList<String> lista_resultados;
    private DefaultListModel<String> lista;

    // Lista con los datos a buscar
    private List<String> datos = new ArrayList<>();
	
	public PrincipalWindow() {
		
		setSize(640, 480);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.black);
		pNorth.setPreferredSize(new Dimension(0, 150));
		pNorth.setLayout(new BorderLayout());
		add(pNorth, BorderLayout.NORTH);
		
		JPanel profile = new JPanel();
		pNorth.setLayout(new BorderLayout());
		pNorth.add(profile, BorderLayout.WEST);
		profile.setPreferredSize(new Dimension(200, 0));
		profile.setBackground(Color.DARK_GRAY);
		
		JPanel otherThings = new JPanel();
		pNorth.add(otherThings, BorderLayout.EAST);
		otherThings.setPreferredSize(new Dimension(200, 0));
		otherThings.setBackground(Color.DARK_GRAY);
		
		JPanel pBody = new JPanel();
		pBody.setBackground(Color.white);
		add(pBody);
		
		//Buscador
		
		
		
		campo_busqueda = new JTextField(20);
		boton_buscar = new JButton("Buscar");
		lista = new DefaultListModel<>();
		lista_resultados = new JList<>(lista);
		
		 JPanel panelBusqueda = new JPanel(); // Añadimos un panel extra con FlowLayout para que no se expanda
	     
	     pNorth.add(panelBusqueda, BorderLayout.CENTER);
	     panelBusqueda.add(campo_busqueda);
	     panelBusqueda.add(boton_buscar);
	     panelBusqueda.setBackground(Color.BLACK);

		
		//JPanel panel_busqueda = new JPanel();
		
		//add(panel_busqueda, BorderLayout.NORTH);
	     add(new JScrollPane(lista_resultados), BorderLayout.CENTER);
	     
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
