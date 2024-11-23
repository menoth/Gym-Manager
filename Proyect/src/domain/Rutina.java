	package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Rutina{
	
	private int id;
	private String nombre;
	private String descripcionRutina;
	private ArrayList<Entrenamiento> entrenamientos;
	
	
	//CONSTRUCTOR
	public Rutina(int id, String nombre, String descripcionRutina, ArrayList<Entrenamiento> entrenamientos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcionRutina = descripcionRutina;
		this.entrenamientos = entrenamientos;
	}

	
	//GETTERS Y SETTERS
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcionRutina() {
		return descripcionRutina;
	}
	public void setDescripcionRutina(String descripcionRutina) {
		this.descripcionRutina = descripcionRutina;
	}
	public ArrayList<Entrenamiento> getEntrenamientos() {
		return entrenamientos;
	}
	public void setEntrenamientos(ArrayList<Entrenamiento> entrenamientos) {
		this.entrenamientos = entrenamientos;
	}
	

	
	
	//TOSTRING
	@Override
	public String toString() {
		return "Rutina [id=" + id + ", nombre=" + nombre + ", descripcionRutina=" + descripcionRutina
				+ ", entrenamientos=" + entrenamientos + "]";
	}
	
	
	
	//HASHCODE Y EQUALS
	@Override
	public int hashCode() {
		return Objects.hash(descripcionRutina, entrenamientos, id, nombre);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rutina other = (Rutina) obj;
		return Objects.equals(descripcionRutina, other.descripcionRutina)
				&& Objects.equals(entrenamientos, other.entrenamientos) && id == other.id
				&& Objects.equals(nombre, other.nombre);
	}
	
		
}
	