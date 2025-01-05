package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import domain.Usuario;

public class InicioSesion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton botonLogin, botonCancel, botonRegistrarse;
    private static final Color COLOR_PRINCIPAL = new Color(70, 130, 180); // Color principal

    public InicioSesion() {
        // Configuración inicial de la ventana
        setTitle("GYM Routine Manager - Inicio de Sesión");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Superior: Título con diseño atractivo
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(COLOR_PRINCIPAL);
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        JLabel labelTitulo = new JLabel("GYM Routine Manager");
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Serif", Font.BOLD, 32));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel labelSubtitulo = new JLabel("¡Organiza tus rutinas de forma fácil!");
        labelSubtitulo.setForeground(Color.WHITE);
        labelSubtitulo.setFont(new Font("Serif", Font.PLAIN, 18));
        labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitulo.add(Box.createVerticalStrut(20));
        panelTitulo.add(labelTitulo);
        panelTitulo.add(labelSubtitulo);
        panelTitulo.add(Box.createVerticalStrut(20));
        add(panelTitulo, BorderLayout.NORTH);

        // Panel Central: Formulario de inicio de sesión
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setFont(new Font("Serif", Font.BOLD, 18));
        userLabel.setForeground(COLOR_PRINCIPAL); // Cambia el color al principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(userLabel, gbc);

        Dimension textFieldDimension = new Dimension(300, 40); // Ancho: 300, Alto: 40

        userTextField = new JTextField();
        userTextField.setFont(new Font("Serif", Font.PLAIN, 18));
        userTextField.setPreferredSize(textFieldDimension);
        gbc.gridx = 1;
        panelFormulario.add(userTextField, gbc);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 18));
        passwordLabel.setForeground(COLOR_PRINCIPAL); // Cambia el color al principal
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Serif", Font.PLAIN, 18));
        passwordField.setPreferredSize(textFieldDimension);
        gbc.gridx = 1;
        panelFormulario.add(passwordField, gbc);

        botonCancel = new JButton("Cancelar");
        botonCancel.setFont(new Font("Serif", Font.BOLD, 18));
        botonCancel.setBackground(new Color(244, 67, 54));
        botonCancel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(botonCancel, gbc);

        botonLogin = new JButton("Iniciar Sesión");
        botonLogin.setFont(new Font("Serif", Font.BOLD, 18));
        botonLogin.setBackground(COLOR_PRINCIPAL);
        botonLogin.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelFormulario.add(botonLogin, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Panel Inferior: Botón de registro
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.WHITE);
        botonRegistrarse = new JButton("¿No tienes cuenta? ¡Regístrate aquí!");
        botonRegistrarse.setFont(new Font("S", Font.PLAIN, 14));
        botonRegistrarse.setForeground(COLOR_PRINCIPAL);
        botonRegistrarse.setBorderPainted(false);
        botonRegistrarse.setContentAreaFilled(false);
        botonRegistrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelInferior.add(botonRegistrarse);

        add(panelInferior, BorderLayout.SOUTH);

        // Listeners para los botones
        botonLogin.addActionListener(e -> iniciarSesion());
        botonCancel.addActionListener(e -> limpiarCampos());
        botonRegistrarse.addActionListener(e -> abrirRegistrarse());

        // Listener para activar el botón de inicio de sesión con Enter
        KeyListener enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    botonLogin.doClick();
                }
            }
        };
        userTextField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }

    private void iniciarSesion() {
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());
        if (validarLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
            dispose();
            PrincipalWindow pw = new PrincipalWindow(username);
            pw.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }

    private boolean validarLogin(String username, String password) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ConectarBaseDeDatos.ConectarBaseDeDatos(usuarios);

        for (Usuario u : usuarios) {
            if ((username.equalsIgnoreCase(u.getUsuario()) || username.equalsIgnoreCase(u.getCorreoElectronico())) 
                    && password.equals(u.getContraseña())) {
                return true;
            }
        }
        return false;
    }

    private void limpiarCampos() {
        userTextField.setText("");
        passwordField.setText("");
    }

    private void abrirRegistrarse() {
        dispose();
        Registro registro = new Registro();
        registro.setVisible(true);
    }
}
