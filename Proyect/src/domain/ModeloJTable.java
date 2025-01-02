package domain;

import javax.swing.table.AbstractTableModel;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloJTable extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final Map<DayOfWeek, List<Map<String, Object>>> datosColumnas;

    public ModeloJTable() {
    	//Inicializo con una entrada vacía
        datosColumnas = new HashMap<>();
        for (DayOfWeek dia : DayOfWeek.values()) {
            datosColumnas.put(dia, new ArrayList<>());
        }
    }

    @Override
    public int getRowCount() {
    	//Cuantas filas tiene que tener la tabla
        int maxSize = 0;
        
        //Encuentra el número máximo de ejercicios en cualquier día de la semana 
        for (List<?> columna : datosColumnas.values()) {
            if (columna.size() > maxSize) {
                maxSize = columna.size();
            }
        }
        
        //Como la primera fila esta reservada para el botón, 0+1 = 1 (desde la primera fila)
        return maxSize + 1;
    }

    @Override
    public int getColumnCount() {
    	//Devuelve el número de columnas en la tabla
        return DayOfWeek.values().length;
    }

    @Override
    public String getColumnName(int column) {
    	//Devuelve el nombre para cada columna, DayOfWeek empieza en 0, por eso se le suma 1
        return DayOfWeek.of(column + 1).toString();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	//Devuelve el calor de una celda en la tabla
    	
        DayOfWeek dia = DayOfWeek.of(columnIndex + 1);
        
        //Accedo a la lista de ejercicios del dia seleccionado
        List<Map<String, Object>> ejercicios = datosColumnas.get(dia);

        if (rowIndex == 0) {
            return "Añadir ejercicio";
        }
        
        //Como rowIndex empieza en 1 para los ejercicios y el primer ejercicio de la lista está en la posición 0, se le resta 1
        int i = rowIndex - 1;
        
        //Si i es menor que el tamaño de la lista devuelve el ejercicio
        if (i < ejercicios.size()) {
            return ejercicios.get(i);
        }
        
        return "";
    }

    @SuppressWarnings("unchecked")
	@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	//Si la fila no es la 0 y el valor es un mapa de cualquier tipo
        if (rowIndex > 0 && aValue instanceof Map<?, ?> datos) {
        	
            DayOfWeek dia = DayOfWeek.of(columnIndex + 1);
            
            //Accedo a la lista del dia seleccionado
            List<Map<String, Object>> ejercicios = datosColumnas.get(dia);
            
            //Convierte el aValue en un mapa y añade el mapa a la lista de ejercicios
            ejercicios.add((Map<String, Object>) datos);
            System.out.println("Datos actualizados para " + dia + ": " + ejercicios);
            
            //Notifica que la tabla ha cambiado
            fireTableDataChanged();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	//Solo la primera fila es editable
        return rowIndex == 0;
    }
}
