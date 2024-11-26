package domain;

import java.time.DayOfWeek;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class ModeloJTable extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Entrenamiento> entrenamientos;
	private Map<DayOfWeek, Entrenamiento> mapaRutina;
	
	public ModeloJTable(DayOfWeek dias, Map<DayOfWeek, Entrenamiento> mapaRutina, List<Entrenamiento> entrenamientos) {
		this.mapaRutina = mapaRutina;
		//this.entrenamientos = entrenamientos;
		
		/**
		mapaRutina = new HashMap<DayOfWeek, Entrenamiento>();
		
		for (Entrenamiento e : entrenamientos) {
			if(!mapaRutina.containsKey(e.getDía())) {
				mapaRutina.put(e.getDía(), e);
			}
			
		}
		**/

		
	}

	@Override
	public int getRowCount() {
		
		if(this.mapaRutina != null) {
			return mapaRutina.keySet().size();
		}
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
