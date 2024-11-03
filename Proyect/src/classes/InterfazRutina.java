package classes;

import javax.swing.*;
import java.awt.*;

public class InterfazRutina extends JFrame {
    private static final long serialVersionUID = 1L;

    InterfazRutina() {
    	
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        setVisible(true);
    }

    public static void main(String[] args) {
        new InterfazRutina();
    }
}
