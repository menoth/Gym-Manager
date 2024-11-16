package gui;

import javax.swing.*;

import domain.ModeloJTable;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InterfazRutina extends JFrame {
	
    private static final long serialVersionUID = 1L;

    InterfazRutina(String usuario) {
    	

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        ModeloJTable modelo = new ModeloJTable();
        JTable table = new JTable(modelo);

        // Asignar renderer y editor de botón a todas las columnas
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new RendererInterfazRutina());
            
            // Con chatgpt
            table.getColumnModel().getColumn(i).setCellEditor(new ButtonEditor(new JCheckBox(), table, modelo));
        }
        
        // Añadimos todo a la interfaz
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        //WindowListener para cerrar aplicación
        addWindowListener(new WindowAdapter() { 
			 	@Override 
			 	public void windowClosing(WindowEvent e) { 
			 	 	confirmarSalida(usuario);
			 	} 
		 
			});

        setVisible(true);
    }
    
    //---------------------------MÉTODOS-----------------------------------------------------------------------------------------------------
    
    private void confirmarSalida(String usuario) {
		int respuesta = JOptionPane.showConfirmDialog(
				this,
				"¿Desea cancelar la rutina? Se borrará todo...",
				"Confirmar salida",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if(respuesta == JOptionPane.YES_OPTION) {
			dispose();
			new PrincipalWindow(usuario);
		}
	}
}
