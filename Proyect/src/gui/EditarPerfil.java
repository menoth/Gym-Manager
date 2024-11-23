package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EditarPerfil extends JFrame {
	
	public EditarPerfil() {
		
		setSize(new Dimension(500, 650));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(11, 1, 0, 10));
        panelPrincipal.setBorder(new EmptyBorder(0, 40, 0, 40));
        
        //Nombre
        JLabel nombre = new JLabel("Nombre");
        JTextField txtNombre = new JTextField();
        panelPrincipal.add(nombre);
        panelPrincipal.add(txtNombre);
        
        //Apellidos
        JLabel apellidos = new JLabel("Apellidos");
        JTextField txtApellidos = new JTextField();
        panelPrincipal.add(apellidos);
        panelPrincipal.add(txtApellidos);
        
        //Descripcion
        JLabel descripcion = new JLabel("Descripción");
        JTextField txtDescripcion = new JTextField();
        panelPrincipal.add(descripcion);
        panelPrincipal.add(txtDescripcion);
        
        //Guardar
        JButton guardar = new JButton("Guardar cambios");
        panelPrincipal.add(guardar);
        
        //Guardar
        JLabel otros = new JLabel("Otros cambios");
        panelPrincipal.add(otros);
        
        //Fotoperfil
        JButton fotoPerfil = new JButton("Foto de perfil");
        panelPrincipal.add(fotoPerfil);
        
        //Vitrina
        JButton vitrina = new JButton("Vitrina");
        panelPrincipal.add(vitrina);
        
        guardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreString = txtNombre.getText();
				String apellidosString = txtApellidos.getText();
				String descipcionString = txtDescripcion.getText();
				
				if(nombreString.length()>20) {
					JOptionPane.showMessageDialog
					(EditarPerfil.this, "El nombre no puede tener más de 20 caracteres");
				}
				else if(apellidosString.length()>25) {
					JOptionPane.showMessageDialog
					(EditarPerfil.this, "Los apellidos no puede tener más de 25 caracteres");
				}
				else if (descipcionString.length()>200){
					JOptionPane.showMessageDialog
					(EditarPerfil.this, "La descripción no puede tener más de 200 caracteres");
				}
				
				
			}
				
		});
		
	
        add(panelPrincipal);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new EditarPerfil();
	}
}
 