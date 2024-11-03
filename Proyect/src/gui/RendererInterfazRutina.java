package gui;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RendererInterfazRutina extends JButton implements TableCellRenderer {
    private static final long serialVersionUID = 1L;

    // Asignamos nombre del botón
    public RendererInterfazRutina() {
        setText("Añadir ejercicio");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        // Con chatgpt
    	return this;
    }
}
