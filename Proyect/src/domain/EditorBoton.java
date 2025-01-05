package domain;

import gui.CatalogoEjercicio;
import gui.InterfazRutina;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

public class EditorBoton implements TableCellEditor {

    private final JButton boton;
    private int editingColumn;
    
    @SuppressWarnings("unused")
	private final JTable tabla;

    public EditorBoton(String usuario, String nombreRutina, JTable tabla, InterfazRutina interfazRutina) {
        this.tabla = tabla;
        boton = new JButton("Añadir ejercicio");
        boton.addActionListener(e -> {
            new CatalogoEjercicio(usuario, nombreRutina, (nombreEjercicio, seriesData) -> {
            	
                if (editingColumn >= 0) {
                	
                	//Metemos en el mapa los datos del nombre del ejercicio y las series/kg
                    Map<String, Object> datos = new HashMap<>();
                    datos.put("ejercicio", nombreEjercicio);
                    datos.put("series", seriesData); 
                    ((ModeloJTable) tabla.getModel()).setValueAt(datos, 1, editingColumn);
                    
                    //Actualizamos la tabla
                    tabla.revalidate();
                    tabla.repaint();
                }
            }, interfazRutina);
            
        });
    }

    @Override
    public Object getCellEditorValue() {
    	//Devuelve el texto actual del botón
        return boton.getText();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
    	//Cualquier celda editable puede abrir el botón al hacer click
        return true;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.editingColumn = column;
        if (value != null) {
            boton.setText(value.toString()); // Si el valor no es nulo, usa su representación como texto
        } else {
            boton.setText("Añadir ejercicio"); // Si el valor es nulo, establece "Añadir ejercicio"
        }
        
        boton.setBackground(new Color(70, 130, 180)); // Fondo principal
        boton.setForeground(Color.WHITE); // Texto blanco
        boton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente del texto
        
        return boton;
    }

    @Override
    public boolean stopCellEditing() {
    	//Permite que es guarde el valor y se termide la edición
        return true;
    }

    @Override
    public void cancelCellEditing() { }

    @Override
    public void addCellEditorListener(javax.swing.event.CellEditorListener l) { }

    @Override
    public void removeCellEditorListener(javax.swing.event.CellEditorListener l) { }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
    	//Para evitar que la celda se quede seleccionada después de la edición
        return false;
    }
}
