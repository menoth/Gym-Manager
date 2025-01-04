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
                new InicioSesion().setVisible(true);
            }
        });
		
		ScheduledExecutorService ventanaAnuncio = Executors.newScheduledThreadPool(1);
		Runnable abrirVentanaAnuncio = () -> {
			new AnuncioVentana();
		};
		ventanaAnuncio.scheduleAtFixedRate(abrirVentanaAnuncio, 1, 1, TimeUnit.MINUTES);
	}
}
