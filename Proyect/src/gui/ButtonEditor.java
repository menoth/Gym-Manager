package gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

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
                openAddDataDialog();
            }
        });
    }
    
    // Ejemplo dialogo que va a aparecer (NO ORIGINAL, HAY QUE CAMBIARLO)
    // Chatgpt
    private void openAddDataDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Ingresar Datos");
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2));
        dialog.setLocationRelativeTo(null);

        JTextField dataField = new JTextField();

        dialog.add(new JLabel("Ingrese el dato:"));
        dialog.add(dataField);
        
        // Botón para añadir los datos que hemos seleccionado	
        JButton addButton = new JButton("Aceptar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newData = dataField.getText();
                
                // Actualizar la celda específica donde se hizo clic
                modelo.setValueAt(newData, row, column);

                dialog.dispose(); // Cerrar el cuadro de diálogo
            }
        });

        dialog.add(addButton);
        dialog.setVisible(true);
    }
    
    // Para que el botón seleccionado funcione
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        this.column = column;
        return button;
    }

}