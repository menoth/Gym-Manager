package domain;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import gui.CatalogoEjercicio;
import gui.InterfazRutina;

public class EditorBoton implements TableCellEditor{
	
	private final JButton boton;
	private final String usuario; 
	private final String nombreRutina; 
	
	public EditorBoton(InterfazRutina ir, String usuario, String nombreRutina) {
		this.usuario = usuario;
		this.nombreRutina = nombreRutina;

		
		
		boton = new JButton();
		boton.addActionListener(e -> {
			ir.dispose();
		    System.out.println("Abriendo CatalogoEjercicio con usuario: " + usuario + " y nombreRutina: " + nombreRutina);
		    new CatalogoEjercicio(usuario, nombreRutina);
		});
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return boton.getText();
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		
		//Para que se pueda hacer click
		return true;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		
		//Devuelve el boton para que se pueda ver en la celda
		return boton;
	}
	

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		// TODO Auto-generated method stub
		
	}

	

}
