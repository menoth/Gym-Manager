package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RetoDiario extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ruleta ruleta;
    private Thread hiloRuleta;
    private boolean girando = false;

    public RetoDiario() {
        setTitle("Ruleta Swing con Hilos");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel personalizado para la ruleta
        ruleta = new Ruleta();
        add(ruleta, BorderLayout.CENTER);

        // Botón para iniciar/detener la ruleta
        JButton botonGirar = new JButton("Girar Ruleta");
        botonGirar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!girando) {
                    girarRuleta();
                    botonGirar.setText("Girando");
                    
                } else {
                    detenerRuleta();
                    botonGirar.setText("Girar Ruleta");
                }
            }
        });
        add(botonGirar, BorderLayout.SOUTH);
    }

    private void girarRuleta() {
        girando = true;
        hiloRuleta = new Thread(() -> {
            while (girando) {
                ruleta.incrementarAngulo();
                ruleta.repaint();
                try {
                    Thread.sleep(50); // Velocidad de la animación
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        hiloRuleta.start();
    }

    private void detenerRuleta() {
        girando = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RetoDiario frame = new RetoDiario();
            frame.setVisible(true);
        });
    }
}


