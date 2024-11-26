package domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Entrenamiento{
		
		private int id;
		private String nombre;
		private String descripcionEntrenamiento;
		private int ID_rutina;
		private DayOfWeek día;
		private List<Ejercicio> ejercicios;
		
		public Entrenamiento(int id, String nombre, String descripcionEntrenamiento, int iD_rutina, DayOfWeek día,
				List<Ejercicio> ejercicios) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.descripcionEntrenamiento = descripcionEntrenamiento;
			ID_rutina = iD_rutina;
			this.día = día;
			this.ejercicios = ejercicios;
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

		public String getDescripcionEntrenamiento() {
			return descripcionEntrenamiento;
		}

		public void setDescripcionEntrenamiento(String descripcionEntrenamiento) {
			this.descripcionEntrenamiento = descripcionEntrenamiento;
		}

		public int getID_rutina() {
			return ID_rutina;
		}

		public void setID_rutina(int iD_rutina) {
			ID_rutina = iD_rutina;
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

		public void setEjercicios(List<Ejercicio> ejercicios) {
			this.ejercicios = ejercicios;
		}

		@Override
		public String toString() {
			return "Entrenamiento [id=" + id + ", nombre=" + nombre + ", descripcionEntrenamiento="
					+ descripcionEntrenamiento + ", ID_rutina=" + ID_rutina + ", día=" + día + ", ejercicios="
					+ ejercicios + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(ID_rutina, descripcionEntrenamiento, día, ejercicios, id, nombre);
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
			return ID_rutina == other.ID_rutina
					&& Objects.equals(descripcionEntrenamiento, other.descripcionEntrenamiento) && día == other.día
					&& Objects.equals(ejercicios, other.ejercicios) && id == other.id
					&& Objects.equals(nombre, other.nombre);
		}
		
		
		
		
				
}
	
	


	
	


