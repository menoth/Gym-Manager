package domain;

import java.util.Objects;

public class Usuario {
	protected String nombre;
	protected String apellidos;
	protected String usuario;
	protected String correoElectronico;
	protected String contraseña;
	protected String descripcion;
	protected String fotoPerfil;
	
	
	//CONSTRUCTOR

	public Usuario(String nombre, String apellidos, String usuario, String correoElectronico, String contraseña,
			String descripcion, String fotoPerfil) {

		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.usuario = usuario;
		this.correoElectronico = correoElectronico;
		this.contraseña = contraseña;
		this.descripcion = descripcion;
		this.fotoPerfil = fotoPerfil;
	}
	
	
	//GETTERS Y SETTERS
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


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	
	//TOSTRING
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellidos=" + apellidos + ", usuario=" + usuario
				+ ", correoElectronico=" + correoElectronico + ", contraseña=" + contraseña + ", descripcion="
				+ descripcion + ", fotoPerfil=" + fotoPerfil + "]";
	}

	
	
	//HASHCODE Y EQUALS
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, contraseña, correoElectronico, nombre, usuario);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(contraseña, other.contraseña)
				&& Objects.equals(correoElectronico, other.correoElectronico) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(usuario, other.usuario);
	}
	

	
}
