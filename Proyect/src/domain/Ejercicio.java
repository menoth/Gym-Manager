package domain;


import java.util.List;
import java.util.Objects;

public class Ejercicio{
	
	public enum Musculo{
		PECTORAL, ANTEBRAZO, ABDOMINAL, CUADRICEPS, CUELLO, TRAPECIO, 
		HOMBRO, BICEPS, DORSAL, ESPALDA_ALTA, FEMORAL, GEMELO, TRICEPS, 
		GLUTEO, CARDIO
	}
	
	private String nombre;
	private int id;
	private Musculo musculoPrincipal;
	private Musculo musculoSecundario;
	private Musculo musculoSecundario2;
	private List<Serie> series;
	
	
	
	//CONSTRUCTOR
	public Ejercicio(String nombre, int id, Musculo musculoPrincipal, Musculo musculoSecundario,
			Musculo musculoSecundario2, List<Serie> series) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.musculoPrincipal = musculoPrincipal;
		this.musculoSecundario = musculoSecundario;
		this.musculoSecundario2 = musculoSecundario2;
		this.series = series;
	}
	
	
	
	//GETTERS Y SETTERS
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Musculo getMusculoSecundario2() {
		return musculoSecundario2;
	}
	public void setMusculoSecundario2(Musculo musculoSecundario2) {
		this.musculoSecundario2 = musculoSecundario2;
	}
	public List<Serie> getSeries() {
		return series;
	}
	public void setSeries(List<Serie> series) {
		this.series = series;
	}

		
	
	//TOSTRING
	@Override
	public String toString() {
		return "Ejercicio [nombre=" + nombre + ", id=" + id + ", musculoPrincipal=" + musculoPrincipal
				+ ", musculoSecundario=" + musculoSecundario + ", musculoSecundario2=" + musculoSecundario2
				+ ", series=" + series + "]";
	}

	
	
	//HASHCODE Y EQUALS
	@Override
	public int hashCode() {
		return Objects.hash(id, musculoPrincipal, musculoSecundario, musculoSecundario2, nombre, series);
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
		return id == other.id && musculoPrincipal == other.musculoPrincipal
				&& musculoSecundario == other.musculoSecundario && musculoSecundario2 == other.musculoSecundario2
				&& Objects.equals(nombre, other.nombre) && Objects.equals(series, other.series);
	}
	
	
	
	


	
	
	
	
}