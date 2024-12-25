package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import domain.Rutina;

public class EditarRutina extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditarRutina(String usuario, int idRutina, Rutina rutina) {
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
		
		JTextField nombreRutina = new JTextField(rutina.getNombre());
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
		JTextField desc = new JTextField(rutina.getDescripcionRutina());

		// Detalles del JTextArea
		desc.setFont(new Font("Arial", Font.PLAIN, 18));
		desc.setPreferredSize(new Dimension(1000, 40));
		
		panelNorteSur.add(desc);
		
		panelNorte.add(panelNorteNorte);
		panelNorte.add(panelNorteSur);
		
		//Action listener para el boton guardar
		guardarCambios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nombreRutina.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(EditarRutina.this, "El campo no puede estar vacío o contener solo espacios.");
					nombreRutina.setText("");
				}
				else if (desc.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(EditarRutina.this, "El campo no puede estar vacío o contener solo espacios.");
					desc.setText("");
				}
				
				else if ((nombreRutina.getText().length()>30) || (desc.getText().length()> 70)) {
							JOptionPane.showMessageDialog(EditarRutina.this, "La longitud maxima del nombre es de 30 caracteres y la de la descripción de 70");
						}
						else {
							actualizarNombreDesc(nombreRutina.getText(), desc.getText(), idRutina);
						}
					
			}
		});
		
		//Action listener boton volver
		botonVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				new PerfilUsuario(usuario);
				dispose();
				
			}
		});
		
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
	
	protected void actualizarNombreDesc(String nombre, String desc, int id) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			String sql = "UPDATE Rutina SET Nombre = ?, Descripción = ? WHERE ID_Rutina = ?";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			queryStmt.setString(1, nombre);
			queryStmt.setString(2, desc);
			queryStmt.setInt(3, id);

			queryStmt.executeUpdate();
			
			queryStmt.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
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
}
	


