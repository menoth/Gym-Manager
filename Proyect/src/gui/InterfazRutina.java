package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import domain.EditorBoton;
import domain.ModeloJTable;
import domain.RendererTabla;



public class InterfazRutina extends JFrame {
	
    private static final long serialVersionUID = 1L;
    
    private DayOfWeek dias;
    

    InterfazRutina(String usuario, String nombreRutina) {
    	
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	
    	
    	JPanel panelGeneral = new JPanel();
    	panelGeneral.setLayout(new BorderLayout());
    	
    	JTable rutina = new JTable(new ModeloJTable(null, null, null));
    	rutina.setDefaultRenderer(Object.class, new RendererTabla(usuario, nombreRutina));
    	
    	for (int i = 0; i < rutina.getColumnCount(); i++) {
            rutina.getColumnModel().getColumn(i).setCellEditor(new EditorBoton(InterfazRutina.this, usuario, nombreRutina));
        }
    	
    	JScrollPane jsp = new JScrollPane(rutina);
    	jsp.setBorder(new TitledBorder(nombreRutina));
    	
    	JPanel panelAbajo = new JPanel();
    	
    	JPanel panelAbajoIzquierda = new JPanel();
    	panelAbajoIzquierda.setLayout(new GridLayout(1, 3, 5, 5));
    	
    	JButton cancelarBtn = new JButton("CANCELAR");
    	cancelarBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PrincipalWindow(usuario);
			}
    		
    	});
    	panelAbajoIzquierda.add(cancelarBtn);
    	panelAbajoIzquierda.add(new JButton("ELIMINAR EJERCICIO"));
    	panelAbajoIzquierda.add(new JButton("GUARDAR"));
    		
    	panelAbajo.add(panelAbajoIzquierda);
    	
    	panelGeneral.add(jsp, BorderLayout.CENTER);
    	panelGeneral.add(panelAbajo, BorderLayout.SOUTH);
    	
    	add(panelGeneral);
    	
    	this.setTitle("Nueva rutina");
    	setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setVisible(true);
    }
    
    
    
}
