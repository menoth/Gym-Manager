package domain;

	import java.util.List;
	import java.util.Objects;
	public class EjercicioEnEntrenamiento {
		private int id;
		private int ID_Entrenamiento;
		private int ID_Ejercicio;
		private int OrdeEnEntrenamiento;
		private List<Serie> series;
		
		public EjercicioEnEntrenamiento(int id, int iD_Entrenamiento, int iD_Ejercicio, int ordeEnEntrenamiento,
				List<Serie> series) {
			super();
			this.id = id;
			ID_Entrenamiento = iD_Entrenamiento;
			ID_Ejercicio = iD_Ejercicio;
			OrdeEnEntrenamiento = ordeEnEntrenamiento;
			this.series = series;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getID_Entrenamiento() {
			return ID_Entrenamiento;
		}
		public void setID_Entrenamiento(int iD_Entrenamiento) {
			ID_Entrenamiento = iD_Entrenamiento;
		}
		public int getID_Ejercicio() {
			return ID_Ejercicio;
		}
		public void setID_Ejercicio(int iD_Ejercicio) {
			ID_Ejercicio = iD_Ejercicio;
		}
		public int getOrdeEnEntrenamiento() {
			return OrdeEnEntrenamiento;
		}
		public void setOrdeEnEntrenamiento(int ordeEnEntrenamiento) {
			OrdeEnEntrenamiento = ordeEnEntrenamiento;
		}
		public List<Serie> getSeries() {
			return series;
		}
		public void setSeries(List<Serie> series) {
			this.series = series;
		}
		@Override
		public String toString() {
			return "EjercicioEnEntrenamiento [id=" + id + ", ID_Entrenamiento=" + ID_Entrenamiento + ", ID_Ejercicio="
					+ ID_Ejercicio + ", OrdeEnEntrenamiento=" + OrdeEnEntrenamiento + ", series=" + series + "]";
		}
		@Override
		public int hashCode() {
			return Objects.hash(ID_Ejercicio, ID_Entrenamiento, OrdeEnEntrenamiento, id, series);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EjercicioEnEntrenamiento other = (EjercicioEnEntrenamiento) obj;
			return ID_Ejercicio == other.ID_Ejercicio && ID_Entrenamiento == other.ID_Entrenamiento
					&& OrdeEnEntrenamiento == other.OrdeEnEntrenamiento && id == other.id
					&& Objects.equals(series, other.series);
		}
		
		
	}


