package gui;

import javax.security.auth.login.FailedLoginException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

public class RetoDiario extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 LinkedList<Color> listaColores = new LinkedList<>();
	 ArrayList<JLabel> listaLabels = new ArrayList<>();
	 ArrayList<String> listaRetos = new ArrayList<>();


    public RetoDiario() {
        setTitle("Reto diario");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        // Lista de retos
        listaRetos.add("Correr 5KM");
        listaRetos.add("Hacer 50 flexiones");
        listaRetos.add("Realizar 30 minutos de yoga");
        listaRetos.add("Hacer 100 abdominales");
        listaRetos.add("Montar en bicicleta 20KM");
         
        
       
        this.setLayout(new BorderLayout());
        
//--------------------------------------LADO SUPERIOR----------------------------------
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        panelSuperior.setBackground(Color.blue);
        
        JTextArea txtExplicacion = new JTextArea("¡Apúntate al reto diario para esos días en los que te sientes con motivación extra!");
        txtExplicacion.setFont(new Font("Arial", Font.BOLD, 18));
        txtExplicacion.setBackground(this.getBackground());
        txtExplicacion.setPreferredSize(new Dimension(800, 70));
        
        txtExplicacion.setLineWrap(true);          
        txtExplicacion.setWrapStyleWord(true); 
        
        //Para que no se pueda editar
        txtExplicacion.setEditable(false);
        
        //Para que no esté el cursor sobre el TxtArea
        txtExplicacion.setEnabled(false);
        
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        txtExplicacion.setDisabledTextColor(Color.black);
        panelSuperior.add(txtExplicacion);

        this.add(panelSuperior, BorderLayout.NORTH);

//-----------------------PARTE CENTRAL------------------------------------
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(1, 2));
        panelCentral.setBackground(Color.yellow);
        panelCentral.setBorder(new EmptyBorder(20,20,20,20));
        
        //----------LADO IZQUIERDO CENTRAL
        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new GridLayout(3, 1));
        panelIzquierda.setBorder(new EmptyBorder(0,0,0,10));
        panelIzquierda.setBackground(Color.red);
        
        //1 DE 3 partes LADO IZQUIERDO CENTRAL
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 70));
        JButton boton = new JButton("OBTEN RETO");
        boton.setPreferredSize(new Dimension(120,60));
        
        
        panel1.add(boton);
        panelIzquierda.add(panel1);
        
        // 2 DE 3 partes para LADO IZQUIERDO CENTRAL
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
        JTextArea txtResultado = new JTextArea("Presiona el botón para obtener un reto");
        txtResultado.setFont(new Font("Arial", Font.BOLD, 18));
        txtResultado.setBackground(this.getBackground());
        txtResultado.setPreferredSize(new Dimension(345, 100));
        
        txtResultado.setLineWrap(true);          
        txtResultado.setWrapStyleWord(true); 
        
        //Para que no se pueda editar
        txtResultado.setEditable(false);
        
        //Para que no esté el cursor sobre el TxtArea
        txtResultado.setEnabled(false);
        
        //Como lo tenemos que desactivar para que no esté el cursor, tenemos que cambiarle
        //el color para cuando esta desactivado
        txtResultado.setDisabledTextColor(Color.black);
        
        panel2.add(txtResultado);
        panelIzquierda.add(panel2);
        
        // 3 DE 3 partes para LADO IZQUIERDO CENTRAL
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        JPanel panelContenedor3 = new JPanel();
        
        panelContenedor3.setLayout(new GridLayout(1, 10));
        panelContenedor3.setPreferredSize(new Dimension(0, 50));
        
        listaColores.push(new Color(255,0,0));
        listaColores.push(new Color(255,165,0));
        listaColores.push(new Color(255,200,0));
        listaColores.push(new Color(255,255,0));
        listaColores.push(new Color(200,255,0));
        listaColores.push(new Color(0,255,0));
        listaColores.push(new Color(0,255,200));
        listaColores.push(new Color(0,0,255));
        listaColores.push(new Color(75,0,130));
        listaColores.push(new Color(238,130,238));
        
        for (int i = 0; i < 10; i++) {
			JLabel color = new JLabel();
			color.setBackground(Color.white);
			color.setOpaque(true);
			panelContenedor3.add(color);
			listaLabels.add(color);
		}
        panel3.add(panelContenedor3, BorderLayout.SOUTH);
        panelIzquierda.add(panel3);
        
        panelCentral.add(panelIzquierda);
        
        //----------LADO IZQUIERDO CENTRAL
        JPanel panelDerecha = new JPanel();
        panelDerecha.setBorder(new EmptyBorder(0,10,0,0));
        panelDerecha.setBackground(Color.red);
        
        
        
        panelCentral.add(panelDerecha);
        this.add(panelCentral, BorderLayout.SOUTH);
        
        
        this.add(panelCentral, BorderLayout.CENTER);
        

        
      //-----------------Panel para la parte inferior------------------
        //Detalles de la ventana
        
        
        boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Iniciar el hilo
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						long inicio = System.currentTimeMillis();
						int variableTiempo = 10;
						while(System.currentTimeMillis()- inicio < 3000) {
							Color primerElemento = listaColores.removeFirst(); // Elimina el primer elemento
				            listaColores.addLast(primerElemento);
							for (int i = 0; i < 10; i++) {
								listaLabels.get(i).setBackground(listaColores.get(i));
							}try {
								Thread.sleep(variableTiempo);
								variableTiempo += 5;
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
						 // Después de 5 segundos, seleccionar un reto aleatorio
                        String retoSeleccionado = listaRetos.get((int) (Math.random() * listaRetos.size()));
                        txtResultado.setText("Tu reto diario es: " + retoSeleccionado);
					}
				}).start();
				
			}
		});
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
		new RetoDiario();
	}
}


