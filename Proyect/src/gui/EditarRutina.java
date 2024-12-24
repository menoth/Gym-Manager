package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

public class EditarRutina extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditarRutina() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Perfil");
		
		this.setLayout(new BorderLayout());
		
		//Panel norte donde va el nombre y descripcion de la rutina (editable) y boton de volver y guardar cambios
//----------------------------------------------PANEL NORTE------------------------------------------------		
		JPanel panelNorte = new JPanel();
		panelNorte.setBackground(Color.red);
		panelNorte.setLayout(new GridLayout(2, 1));
		
		JPanel panelNorteNorte = new JPanel();
		panelNorteNorte.setLayout(new FlowLayout(EditarRutina.WIDTH, 340, 30));
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setPreferredSize(new Dimension(120, 50));
		
		JTextField nombreRutina = new JTextField("Nombre rutina");
		nombreRutina.setPreferredSize(new Dimension(250,40));
		nombreRutina.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JButton guardarCambios = new JButton("Guardar");
		guardarCambios.setPreferredSize(new Dimension(120, 50));
		
		panelNorteNorte.add(botonVolver);
		panelNorteNorte.add(nombreRutina);
		panelNorteNorte.add(guardarCambios);
		
		
		JPanel panelNorteSur = new JPanel();
		panelNorteSur.setLayout(new FlowLayout());
		
		//Creamos un jTextArea que será la descripción de la rutina
		JTextField desc = new JTextField("aaaaaa adwada wd adw adw dawdawdaw dawdwdawdwa adwda awdwd");

				
		// Detalles del JTextArea

		desc.setFont(new Font("Arial", Font.PLAIN, 18));
		desc.setPreferredSize(new Dimension(1000, 40));
		
		panelNorteSur.add(desc);
		
		panelNorte.add(panelNorteNorte);
		panelNorte.add(panelNorteSur);
		
		this.add(panelNorte, BorderLayout.NORTH);
		
//-------------------------------------PANEL CENTRAL(RENDERER)-----------------------------------------------------		
		JPanel panelCentral = new JPanel();
		panelCentral.setBorder(new EmptyBorder(10,10,10,10));
		
		panelCentral.setBackground(Color.blue);
		panelCentral.setLayout(new BorderLayout());
		
		
		EditarRutinaModelo modelo = new EditarRutinaModelo();
		//modelo.cargarDatos(usuario)
		JTable table = new JTable(modelo);
		
		JScrollPane scrollPane = new JScrollPane(table);
		panelCentral.add(scrollPane, BorderLayout.CENTER);
		
		this.add(panelCentral, BorderLayout.CENTER);
		
		// Detalles ventana
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	class EditarRutinaModelo extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String[] nombreDatos = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
		private Object[][] data = new Object[0][0];
		
		
		
		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public int getColumnCount() {
			return nombreDatos.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}

		@Override
		public String getColumnName(int column) {
			return nombreDatos[column];
		}
	}
	
	public static void main(String[] args) {
		new EditarRutina();
	}
}
	


