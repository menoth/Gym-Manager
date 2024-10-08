package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PrincipalWindow extends JFrame{
	
	public PrincipalWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel pNorth = new JPanel();
		pNorth.setBackground(Color.black);
		pNorth.setPreferredSize(new Dimension(0, 150));
		add(pNorth, BorderLayout.NORTH);
		
		JPanel profile = new JPanel();
		pNorth.setLayout(new BorderLayout());
		pNorth.add(profile, BorderLayout.WEST);
		profile.setPreferredSize(new Dimension(200, 0));
		profile.setBackground(Color.DARK_GRAY);
		
		JPanel otherThings = new JPanel();
		pNorth.add(otherThings, BorderLayout.EAST);
		otherThings.setPreferredSize(new Dimension(200, 0));
		otherThings.setBackground(Color.DARK_GRAY);
		
		JPanel pBody = new JPanel();
		pBody.setBackground(Color.white);
		add(pBody);
		
		
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		PrincipalWindow pw = new PrincipalWindow();
		pw.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	

}
