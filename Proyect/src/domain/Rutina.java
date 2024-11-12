	package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Rutina extends Entrenamiento{
	
	private int id;
	private String nombre;
	private ArrayList<Entrenamiento> entrenamientos;
	private String descripcionRutina;
	
	public Rutina(int reps, float peso, Esfuerzo esfuerzo, String nombre, int id, String musc_principal,
			String musc_secundario, ArrayList<Serie> series, String nombre2, String descripcionEntrenamiento,
			DayOfWeek día, ArrayList<Ejercicio> ejercicios, int id2, String nombre3,
			ArrayList<Entrenamiento> entrenamientos, String descripcionRutina) {
		super(reps, peso, esfuerzo, nombre, id, musc_principal, musc_secundario, series, nombre2,
				descripcionEntrenamiento, día, ejercicios);
		id = id2;
		nombre = nombre3;
		this.entrenamientos = entrenamientos;
		this.descripcionRutina = descripcionRutina;
	}

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

	public ArrayList<Entrenamiento> getEntrenamientos() {
		return entrenamientos;
	}

	public void setEntrenamientos(ArrayList<Entrenamiento> entrenamientos) {
		this.entrenamientos = entrenamientos;
	}

	public String getDescripcionRutina() {
		return descripcionRutina;
	}

	public void setDescripcionRutina(String descripcionRutina) {
		this.descripcionRutina = descripcionRutina;
	}

	@Override
	public String toString() {
		return "Rutina [id=" + id + ", nombre=" + nombre + ", entrenamientos=" + entrenamientos + ", descripcionRutina="
				+ descripcionRutina + ", getDescripcionEntrenamiento()=" + getDescripcionEntrenamiento() + ", getDía()="
				+ getDía() + ", getEjercicios()=" + getEjercicios() + ", toString()=" + super.toString()
				+ ", getMusc_principal()=" + getMusc_principal() + ", getMusc_secundario()=" + getMusc_secundario()
				+ ", getSeries()=" + getSeries() + ", getReps()=" + getReps() + ", getPeso()=" + getPeso()
				+ ", getEsfuerzo()=" + getEsfuerzo() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
		
}
	