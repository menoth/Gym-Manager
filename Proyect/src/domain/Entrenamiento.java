package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Entrenamiento extends Ejercicio {
		private String nombre;
		private String descripcionEntrenamiento;
		private DayOfWeek día;
		private ArrayList<Ejercicio> ejercicios;
		public Entrenamiento(int reps, float peso, Esfuerzo esfuerzo, String nombre, int id, String musc_principal,
				String musc_secundario, ArrayList<Serie> series, String nombre2, String descripcionEntrenamiento,
				DayOfWeek día, ArrayList<Ejercicio> ejercicios) {
			super(reps, peso, esfuerzo, nombre, id, musc_principal, musc_secundario, series);
			nombre = nombre2;
			this.descripcionEntrenamiento = descripcionEntrenamiento;
			this.día = día;
			this.ejercicios = ejercicios;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getDescripcionEntrenamiento() {
			return descripcionEntrenamiento;
		}
		public void setDescripcionEntrenamiento(String descripcionEntrenamiento) {
			this.descripcionEntrenamiento = descripcionEntrenamiento;
		}
		public DayOfWeek getDía() {
			return día;
		}
		public void setDía(DayOfWeek día) {
			this.día = día;
		}
		public ArrayList<Ejercicio> getEjercicios() {
			return ejercicios;
		}
		public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
			this.ejercicios = ejercicios;
		}
		@Override
		public String toString() {
			return "Entrenamiento [nombre=" + nombre + ", descripcionEntrenamiento=" + descripcionEntrenamiento
					+ ", día=" + día + ", ejercicios=" + ejercicios + ", getId()=" + getId() + ", getMusc_principal()="
					+ getMusc_principal() + ", getMusc_secundario()=" + getMusc_secundario() + ", getSeries()="
					+ getSeries() + ", toString()=" + super.toString() + ", getReps()=" + getReps() + ", getPeso()="
					+ getPeso() + ", getEsfuerzo()=" + getEsfuerzo() + ", getClass()=" + getClass() + ", hashCode()="
					+ hashCode() + "]";
		}
		
}
	
	


