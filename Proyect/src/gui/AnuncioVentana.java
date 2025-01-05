package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class AnuncioVentana extends JDialog {

    private static final long serialVersionUID = 1L;

    private JButton cerrarButton;
    private JProgressBar barraProgreso;
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
        Image imagen = iconoOriginal.getImage(); // Obtener la imagen
        Image imagenRedimensionada = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH); // Redimensionar la imagen
        ImageIcon imagenAjustada = new ImageIcon(imagenRedimensionada);

        // Crear un JPanel para contener la imagen y el botón
        JPanel panelImagen = new JPanel(null); // Diseño nulo para posición personalizada
        panelImagen.setBounds(0, 0, getWidth(), getHeight());

        // Agregar la imagen al JLabel
        JLabel imagenLabel = new JLabel(imagenAjustada);
        imagenLabel.setBounds(0, 0, getWidth(), getHeight());
        panelImagen.add(imagenLabel);

        // Crear el botón de cerrar y colocarlo en la esquina superior derecha
        ImageIcon iconoCerrar = new ImageIcon("Sources/imagenes/cruzroja.png"); 
        Image imagenCerrar = iconoCerrar.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); 
        cerrarButton = new JButton();
        cerrarButton.setIcon(new ImageIcon(imagenCerrar)); 
        cerrarButton.setEnabled(false);
        cerrarButton.setBounds(getWidth() - 80, 10, 50, 40); 
        cerrarButton.addActionListener(e -> dispose()); 
        panelImagen.add(cerrarButton);

        add(panelImagen, BorderLayout.CENTER);

        // Crear el contador
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel anuncio = new JLabel("Anuncio Publicitario");
        anuncio.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(anuncio);
        add(panelTitulo, BorderLayout.NORTH);

        // Crear la barra de progreso
        barraProgreso = new JProgressBar(0, contador);
        barraProgreso.setValue(contador);
        add(barraProgreso, BorderLayout.SOUTH);

        // Iniciar el hilo del contador
        iniciarContador();

        // Configurar el temporizador para cerrar automáticamente
        iniciarCierreAutomatico();

        // Mostrar la ventana
        setVisible(true);
    }

    private void iniciarContador() {
        // Crear un hilo para el contador
        Thread hiloContador = new Thread(() -> {
            try {
                while (contador > 0) {
                    Thread.sleep(100);
                    contador--;
                    SwingUtilities.invokeLater(() -> {
                        barraProgreso.setValue(barraProgreso.getMaximum() - contador);
                    });
                }
                // Habilitar el botón al finalizar el contador
                SwingUtilities.invokeLater(() -> cerrarButton.setEnabled(true));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        hiloContador.start();
    }

    private void iniciarCierreAutomatico() {
        Thread hiloCierre = new Thread(() -> {
            try {
                Thread.sleep(20000); // Esperar 20 segundos
                if (isVisible()) {
                    SwingUtilities.invokeLater(this::dispose);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        hiloCierre.start();
    }

}
