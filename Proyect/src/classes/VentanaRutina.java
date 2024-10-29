package classes;

import javax.swing.JFrame;
//Ventana de creacion de rutinas
public class VentanaRutina extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VentanaRutina() {
		setTitle("Ventana Rutina");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setVisible(true);
	}
}
