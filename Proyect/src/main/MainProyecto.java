package main;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import gui.AnuncioVentana;
import gui.InicioSesion;

public class MainProyecto {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear y mostrar la ventana de inicio de sesión
                InicioSesion inicioSesion = new InicioSesion();
                inicioSesion.setVisible(true);
                
                // Crear el ScheduledExecutorService para los anuncios
                ScheduledExecutorService ventanaAnuncio = Executors.newScheduledThreadPool(1);
                
                // Crear el Runnable para abrir la ventana de anuncio
                Runnable abrirVentanaAnuncio = () -> {
                    new AnuncioVentana();
                };

                // Programar el anuncio para que se ejecute 60 segundos después del inicio,
                // y luego se repita cada 120 segundos
                ventanaAnuncio.scheduleAtFixedRate(abrirVentanaAnuncio, 60, 120, TimeUnit.SECONDS);
                
                
            }
        });
    }
}