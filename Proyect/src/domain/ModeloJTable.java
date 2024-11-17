package domain;

import java.time.DayOfWeek;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class ModeloJTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DayOfWeek dias;
	private Map<DayOfWeek, Entrenamiento> mapaRutina;
	
	public ModeloJTable(DayOfWeek dias) {
		this.dias = dias;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	

	@Override
	public int getColumnCount() {
		return DayOfWeek.values().length;
	}
	
	@Override
	public String getColumnName(int column) {
		return DayOfWeek.of(column + 1).toString();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if (rowIndex == 0) {
			return "Añadir ejercicio";	
		}
		return "";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (rowIndex == 0) {return true;}
		return false;
	}
  
}
