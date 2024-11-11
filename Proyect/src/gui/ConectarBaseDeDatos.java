package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Usuario;

// Lo usaremos para conectar la base de datos
public class ConectarBaseDeDatos {
	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido cargar el driver de la BD");
		}
		try {
			Connection conn = DriverManager.getConnection
				("jdbc:sqlite:Sources/bd/baseDeDatos.db");
	
			Statement stmt = conn.createStatement();
			List<Usuario> usuarios = new ArrayList<>();
			String sql = "SELECT * FROM Usuario";
			PreparedStatement queryStmt = conn.prepareStatement(sql);
			ResultSet rs = queryStmt.executeQuery();
	
			while (rs.next()) {
				String nombre = rs.getString("Nombre");
				String apellidos = rs.getString("Apellidos");
				String usuario = rs.getString("Usuario");
				String correoElectronico = rs.getString("Correo");
				String contraseña = rs.getString("Contraseña");
				usuarios.add(new Usuario(nombre, apellidos, usuario, correoElectronico, contraseña));
			}
			stmt.close();
			conn.close();
			for(Usuario u : usuarios) {
				System.out.println(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}

