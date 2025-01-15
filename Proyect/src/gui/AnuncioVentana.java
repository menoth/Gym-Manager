package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class AnuncioVentana extends JDialog {

    private static final long serialVersionUID = 1L;

    private JProgressBar barraProgreso;
    private JLabel contadorLabel; // Etiqueta para el contador visual
    private int contador = 100; // Tiempo inicial del contador

    public AnuncioVentana() {
        setUndecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(800, 450);
        setTitle("Anuncio Publicitario");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);

        String[] rutasImagenes = {
                "Sources/imagenes/Anuncio Valorant.jpg",
                "Sources/imagenes/Anuncio LOL.jpg",
                "Sources/imagenes/Anuncio Myprotein.jpg",
                "Sources/imagenes/Anuncio Tarkov.jpg",
                "Sources/imagenes/Anuncio Opel Corsa.jpg"
        };

        // Seleccionar una ruta aleatoria
        Random random = new Random();
        String rutaImagen = rutasImagenes[random.nextInt(rutasImagenes.length)];

        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagen = iconoOriginal.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imagenAjustada = new ImageIcon(imagenRedimensionada);

        // Crear un JPanel para contener la imagen
        JPanel panelImagen = new JPanel(null);
        panelImagen.setBounds(0, 0, getWidth(), getHeight());

        JLabel imagenLabel = new JLabel(imagenAjustada);
        imagenLabel.setBounds(0, 0, getWidth(), getHeight());
        panelImagen.add(imagenLabel);

        add(panelImagen, BorderLayout.CENTER);

        // Crear el panel del título con el contador visual
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(70, 130, 180));

        JLabel anuncio = new JLabel("Anuncio Publicitario", JLabel.CENTER);
        anuncio.setFont(new Font("Arial", Font.BOLD, 16));
        anuncio.setForeground(Color.WHITE);
        panelTitulo.add(anuncio, BorderLayout.NORTH);

        // Contador visual
        contadorLabel = new JLabel("10 segundos restantes", JLabel.CENTER);
        contadorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contadorLabel.setForeground(Color.WHITE);
        panelTitulo.add(contadorLabel, BorderLayout.SOUTH);

        add(panelTitulo, BorderLayout.NORTH);

        // Crear la barra de progreso
        barraProgreso = new JProgressBar(0, contador);
        barraProgreso.setValue(contador);
        add(barraProgreso, BorderLayout.SOUTH);

        // Iniciar el contador
        iniciarContador();

        // Mostrar la ventana
        setVisible(true);
    }

    private void iniciarContador() {
        Thread hiloContador = new Thread(() -> {
            try {
                while (contador > 0) {
                    Thread.sleep(100);
                    contador--;
                    if(contador == 0) {
                    	SwingUtilities.invokeLater(this::dispose);
                    }
                    SwingUtilities.invokeLater(() -> {
                        barraProgreso.setValue(barraProgreso.getMaximum() - contador);
                        contadorLabel.setText((contador / 10) + " segundos restantes");
                    });
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        hiloContador.start();
    }

}
