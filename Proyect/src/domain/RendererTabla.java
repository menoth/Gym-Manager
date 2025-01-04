package domain;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class RendererTabla extends JPanel implements TableCellRenderer {

    private static final long serialVersionUID = 1L;

    public RendererTabla(String usuario, String nombreRutina) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        
    	// Limpia los componentes existentes en la celda
    	removeAll(); 

        // Caso especial: Fila 0 (Botones "Añadir ejercicio")
        if (row == 0) {
            setLayout(new BorderLayout()); // Usamos BorderLayout para escalar el botón
            
            
            //Porseacaso si no se le asigna ningun valor
            if (value == null) {
                JButton boton = new JButton("Añadir ejercicio"); // Si el valor es nulo, usa "Añadir ejercicio"
                boton.setFocusPainted(false); // Evita el borde de enfoque
                boton.setMargin(new Insets(0, 0, 0, 0)); // Sin márgenes internos
                add(boton, BorderLayout.CENTER);
                return this;
            } else {
                JButton boton = new JButton(value.toString()); // Si no es nulo, convierte el valor a texto
                boton.setFocusPainted(false); // Evita el borde de enfoque
                boton.setMargin(new Insets(0, 0, 0, 0)); // Sin márgenes internos
                add(boton, BorderLayout.CENTER);
                return this;
            }

            
        }

        // Verificar si el valor es un mapa
        if (value instanceof Map<?, ?> datos) {
        	
        	//Establecer el Layout
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
            JPanel panelInterno = new JPanel();
            panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
            panelInterno.setOpaque(false); // Mantiene el fondo transparente

            //Editar el aspecto del nombre del ejercicio
            String ejercicio = (String) datos.get("ejercicio");
            JLabel etiquetaEjercicio = new JLabel(ejercicio);
            etiquetaEjercicio.setFont(new Font(etiquetaEjercicio.getFont().getName(), Font.BOLD, 13));
            etiquetaEjercicio.setAlignmentX(CENTER_ALIGNMENT);
            etiquetaEjercicio.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Márgenes internos
            panelInterno.add(etiquetaEjercicio);

            // Editar el aspecto de las series y los pesos 
            @SuppressWarnings("unchecked")
            List<Map<String, Integer>> series = (List<Map<String, Integer>>) datos.get("series");

            // Bucle para recorrer las series
            for (int i = 0; i < series.size(); i++) {
                Map<String, Integer> serie = series.get(i);
                
                // Obtener esfuerzo como texto
                String esfuerzo;
                switch (serie.get("esfuerzo")) {
                    case 1: esfuerzo = "W"; break;
                    case 2: esfuerzo = "E"; break;
                    case 3: esfuerzo = "T"; break;
                    default: esfuerzo = "W"; break;
                }
                
                // Etiqueta que muestra repeticiones, peso y esfuerzo
                JLabel etiquetaSerie = new JLabel("Serie " + (i + 1) + ": " +
                        serie.get("repeticiones") + " reps, Peso: " + serie.get("peso") + " kg, Esfuerzo: " + esfuerzo);
                
                etiquetaSerie.setFont(new Font(etiquetaSerie.getFont().getName(), Font.PLAIN, 13));
                etiquetaSerie.setAlignmentX(CENTER_ALIGNMENT);
                panelInterno.add(etiquetaSerie);
                etiquetaSerie.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Márgenes internos
            }


            add(panelInterno); 
        }

        //Avisa al JPanel que los componentes han cambiado
        revalidate();
        
        //Fuerza al JPanel a reorganizar sus componentes dependiendo el Layout (que en nuestro caso es un BoxLayout)
        doLayout();

        // Configuración de fondo y bordes
        if (isSelected) {
            setBackground(table.getSelectionBackground()); // Si la celda está seleccionada, usa el color de selección
        } else {
            setBackground(table.getBackground()); // Si no está seleccionada, usa el color normal de fondo
        }

        
        return this;
    }

}
