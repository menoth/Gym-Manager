package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

class Ruleta extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int angulo = 0; // Ángulo actual de rotación

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Suavizado de bordes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar fondo
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Dibujar ruleta
        int diametro = Math.min(getWidth(), getHeight()) - 20;
        int x = (getWidth() - diametro) / 2;
        int y = (getHeight() - diametro) / 2;

        // Colores de las secciones
        Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        int numSecciones = colores.length;

        for (int i = 0; i < numSecciones; i++) {
            g2d.setColor(colores[i]);
            g2d.fillArc(x, y, diametro, diametro, angulo + i * (360 / numSecciones), 360 / numSecciones);
        }

        // Dibujar borde de la ruleta
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, diametro, diametro);
    }

    public void incrementarAngulo() {
        angulo = (angulo + 5) % 360; // Incremento del ángulo
    }
}
