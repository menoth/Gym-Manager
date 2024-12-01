package domain;



import java.util.List;
import java.util.Objects;

public class Ejercicio{
	
	public enum Musculo{
		PECTORAL, ANTEBRAZO, ABDOMINAL, CUADRICEPS, CUELLO, TRAPECIO, 
		HOMBRO, BICEPS, DORSAL, ESPALDA_ALTA, FEMORAL, GEMELO, TRICEPS, 
		GLUTEO, CARDIO
	}
	
	private int id;
	private String nombre;
	private int ID_Entrenamiento;
	private Musculo musculoPrincipal;
	private Musculo musculoSecundario;
	private List<Serie> series;
	private int OrdenEnEntrenamiento;
	

	public Ejercicio(int id, String nombre, int iD_Entrenamiento, Musculo musculoPrincipal, Musculo musculoSecundario,
			List<Serie> series, int ordenEnEntrenamiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		ID_Entrenamiento = iD_Entrenamiento;
		this.musculoPrincipal = musculoPrincipal;
		this.musculoSecundario = musculoSecundario;
		this.series = series;
		OrdenEnEntrenamiento = ordenEnEntrenamiento;
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

	public int getID_Entrenamiento() {
		return ID_Entrenamiento;
	}

	public void setID_Entrenamiento(int iD_Entrenamiento) {
		ID_Entrenamiento = iD_Entrenamiento;
	}

	public Musculo getMusculoPrincipal() {
		return musculoPrincipal;
	}

	public void setMusculoPrincipal(Musculo musculoPrincipal) {
		this.musculoPrincipal = musculoPrincipal;
	}

	public Musculo getMusculoSecundario() {
		return musculoSecundario;
	}

	public void setMusculoSecundario(Musculo musculoSecundario) {
		this.musculoSecundario = musculoSecundario;
	}

	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}

	public int getOrdenEnEntrenamiento() {
		return OrdenEnEntrenamiento;
	}

	public void setOrdenEnEntrenamiento(int ordenEnEntrenamiento) {
		OrdenEnEntrenamiento = ordenEnEntrenamiento;
	}

	@Override
	public String toString() {
		return "Ejercicio [id=" + id + ", nombre=" + nombre + ", ID_Entrenamiento=" + ID_Entrenamiento
				+ ", musculoPrincipal=" + musculoPrincipal + ", musculoSecundario=" + musculoSecundario + ", series="
				+ series + ", OrdenEnEntrenamiento=" + OrdenEnEntrenamiento + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID_Entrenamiento, OrdenEnEntrenamiento, id, musculoPrincipal, musculoSecundario, nombre,
				series);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio other = (Ejercicio) obj;

		return ID_Entrenamiento == other.ID_Entrenamiento && OrdenEnEntrenamiento == other.OrdenEnEntrenamiento
				&& id == other.id && musculoPrincipal == other.musculoPrincipal
				&& musculoSecundario == other.musculoSecundario && Objects.equals(nombre, other.nombre)
				&& Objects.equals(series, other.series);
	}
	
	
	
	
}