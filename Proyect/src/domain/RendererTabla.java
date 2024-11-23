package domain;

import java.awt.Component;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;

import javax.swing.table.TableCellRenderer;



public class RendererTabla extends JLabel implements TableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JButton boton = new JButton();
	private final String usuario; 
	private final String nombreRutina; 
	
	
	public RendererTabla(String usuario, String nombreRutina) {
		this.usuario = usuario;
		this.nombreRutina = nombreRutina;
		
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		if (row == 0) {
			//Añadir un boton a la fila 0
			boton.setText(value.toString());
           
            return boton;
			
		}
		
		
		setText(value.toString());
		return this;
	}

}
