package gui;

import domain.EditorBoton;
import domain.ModeloJTable;
import domain.RendererTabla;

import javax.swing.*;
import java.awt.*;

public class InterfazRutina extends JFrame {

    private static final long serialVersionUID = 1L;

    public InterfazRutina(String usuario, String nombreRutina) {
        setTitle("Rutina - " + nombreRutina);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
       

        ModeloJTable modelo = new ModeloJTable();

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(40); 

        RendererTabla rendererTabla = new RendererTabla(usuario, nombreRutina);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(rendererTabla);
            tabla.getColumnModel().getColumn(i).setCellEditor(new EditorBoton(usuario, nombreRutina, tabla));
        }
        
        modelo.addTableModelListener(e -> SwingUtilities.invokeLater(() -> {
            for (int row = 0; row < tabla.getRowCount(); row++) {
                for (int column = 0; column < tabla.getColumnCount(); column++) {
                    Component comp = tabla.prepareRenderer(tabla.getCellRenderer(row, column), row, column);
                    int preferredHeight = comp.getPreferredSize().height;
                    tabla.setRowHeight(row, Math.max(tabla.getRowHeight(row), preferredHeight));
                }
            }
        }));


        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Rutina guardada con éxito"));

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(e -> confirmarSalida(usuario, nombreRutina));

        panelInferior.add(botonCancelar);
        panelInferior.add(botonGuardar);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void confirmarSalida(String usuario, String nombreRutina) {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar " + nombreRutina + "? Se perderá todo lo hecho hasta ahora...",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose();
            new PrincipalWindow(usuario);
        }
    }
}
