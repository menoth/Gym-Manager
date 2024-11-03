package domain;

import javax.swing.table.AbstractTableModel;

public class ModeloJTable extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    //Añadimos el nombre y cantidad de columnas de la tabla
    private String[] diasColumnas = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    private String[][] filas = {{"AÑADIR", "AÑADIR", "AÑADIR", "AÑADIR", "AÑADIR", "AÑADIR", "AÑADIR"}};

    //Número de filas que tiene la tabla
    @Override
    public int getRowCount() {
        return 1;
    }
    
    //Número de columnas que tiene la tabla
    @Override
    public int getColumnCount() {
        return diasColumnas.length;
    }

    //Información de una celda específica
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return filas[rowIndex][columnIndex];
    }

    //Nombre de la columna para que aparezcan los días
    @Override
    public String getColumnName(int column) {
        return diasColumnas[column];
    }

    //Para poder hacer que el botón funcione
    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }
}
