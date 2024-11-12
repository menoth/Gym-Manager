package domain;

public class Usuario {
	protected String nombre;
	protected String apellidos;
	protected String usuario;
	protected String correoElectronico;
	protected String contraseña;
	
	public Usuario(String nombre, String apellidos, String usuario, String correoElectronico, String contraseña) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.usuario = usuario;
		this.correoElectronico = correoElectronico;
		this.contraseña = contraseña;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellidos=" + apellidos + ", usuario=" + usuario
				+ ", correoElectronico=" + correoElectronico + ", contraseña=" + contraseña + "]";
	}
	
	
	
}
