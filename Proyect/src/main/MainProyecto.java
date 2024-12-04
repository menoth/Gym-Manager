package main;

import javax.swing.SwingUtilities;

import gui.InicioSesion;


public class MainProyecto {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InicioSesion().setVisible(true);
            }
        });
	}
	

}
