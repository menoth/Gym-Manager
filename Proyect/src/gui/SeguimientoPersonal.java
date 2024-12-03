package gui;

import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;
import java.time.DayOfWeek;

public class SeguimientoPersonal extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panelPrincipal;
    private int añoElegido;


    public SeguimientoPersonal(String usuario) {
        

        setTitle("Calendario Anual - Seguimiento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Mostrar la ventana en pantalla completa
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Selector de Año
        JPanel panelElegirMes = new JPanel();
        panelElegirMes.setLayout(new FlowLayout());

        JLabel labelAño = new JLabel("Selecciona el Año:");
        JComboBox<Integer> selectorAño = new JComboBox<>();
        
        //Añadimos todas las opciones
        for (int year = 2024; year <= 2100; year++) {
            selectorAño.addItem(year);
        }
        
        añoElegido = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        
        selectorAño.setSelectedItem(añoElegido);
        selectorAño.addActionListener(e -> {
            añoElegido = (int) selectorAño.getSelectedItem();
            actualizarSeguimiento();
        });

        panelElegirMes.add(labelAño);
        panelElegirMes.add(selectorAño);
        add(panelElegirMes, BorderLayout.NORTH);

        // Panel Principal para los meses
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(3, 4, 7, 7)); // 3 filas, 4 columnas
        add(panelPrincipal, BorderLayout.CENTER);

        actualizarSeguimiento();
    }

    private void actualizarSeguimiento() {
    	
    	//Quitar todo de la interfaz
        panelPrincipal.removeAll();

        // Crear paneles para cada mes
        for (int mes = 1; mes <= 12; mes++) {
            JPanel panelMes = new JPanel();
            
            //Ponemos el borde del nombre de cada mes y el año elegido en el ComboBox
            panelMes.setBorder(BorderFactory.createTitledBorder(YearMonth.of(añoElegido, mes).getMonth().toString()));

            JPanel panelDias = new JPanel();
            panelDias.setLayout(new GridLayout(0, 7)); // 7 columnas para los días de la semana

            // Añadir nombres de los días de la semana
            for (DayOfWeek dia : DayOfWeek.values()) {
                JLabel labelDia = new JLabel(dia.toString().substring(0, 3), SwingConstants.CENTER);
                labelDia.setFont(new Font("Arial", Font.BOLD, 12));
                panelDias.add(labelDia);
            }

            // Obtener los días del mes
            YearMonth yearMonth = YearMonth.of(añoElegido, mes);
            int totalDias = yearMonth.lengthOfMonth();
            int primerDia = yearMonth.atDay(1).getDayOfWeek().getValue();

            // Rellenar espacios en blanco antes del primer día del mes
            for (int i = 1; i < primerDia; i++) {
                panelDias.add(new JLabel(""));
            }

            // Añadir días del mes como botones con sus números
            for (int dia = 1; dia <= totalDias; dia++) {
                JButton botonDia = new JButton();
                botonDia.setText(String.valueOf(dia)); // Establece el texto como número del día
                botonDia.setPreferredSize(new Dimension(40, 40));    // Tamaño del botón
                botonDia.setFont(new Font("Arial", Font.PLAIN, 10)); // Fuente del número
                botonDia.setFocusPainted(false);                     // Sin bordes al hacer clic
                panelDias.add(botonDia);                             // Añadir botón al panel de días
            }

            panelMes.add(panelDias, BorderLayout.CENTER);
            panelPrincipal.add(panelMes);
        }

        panelPrincipal.revalidate();
        panelPrincipal.repaint();
        setVisible(true);
    }

}
