package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Entrenamiento{
		
		private String nombre;
		private String descripcionEntrenamiento;
		private DayOfWeek día;
		private List<Ejercicio> ejercicios;
		
		
		
		//CONSTRUCTOR
		public Entrenamiento(String nombre, String descripcionEntrenamiento, DayOfWeek día,
				ArrayList<Ejercicio> ejercicios) {
			super();
			this.nombre = nombre;
			this.descripcionEntrenamiento = descripcionEntrenamiento;
			this.día = día;
			this.ejercicios = ejercicios;
		}
		
		
		//GETTERS AND SETTERS
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
		public List<Ejercicio> getEjercicios() {
			return ejercicios;
		}
		public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
			this.ejercicios = ejercicios;
		}
		
		
		
		//HASHCODE Y EQUALS
		@Override
		public int hashCode() {
			return Objects.hash(descripcionEntrenamiento, día, ejercicios, nombre);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entrenamiento other = (Entrenamiento) obj;
			return Objects.equals(descripcionEntrenamiento, other.descripcionEntrenamiento) && día == other.día
					&& Objects.equals(ejercicios, other.ejercicios) && Objects.equals(nombre, other.nombre);
		}
		
		
		
		
}
	
	


