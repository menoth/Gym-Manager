package gui;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import domain.ModeloJTable;

public class ButtonEditor extends DefaultCellEditor {
    private static final long serialVersionUID = 1L;
    private JButton button;
    private JTable table;
    private ModeloJTable modelo;
    private int row, column;

    // Chatgpt
    public ButtonEditor(JCheckBox checkBox, JTable table, ModeloJTable modelo) {
        super(checkBox);
        this.table = table;
        this.modelo = modelo;
        
        // Añadimos el actionListener al botón para que nos abra el dialogo de añadir-entrenamiento
        button = new JButton("Añadir ejercicio");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CatalogoEjercicio();
            }
        });
    }
        
    
    // Para que el botón seleccionado funcione
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        this.column = column;
        return button;
    }

}