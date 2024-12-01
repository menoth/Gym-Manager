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
	private Musculo musculoPrincipal;
	private Musculo musculoSecundario;
	
	public Ejercicio(int id, String nombre, Musculo musculoPrincipal, Musculo musculoSecundario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.musculoPrincipal = musculoPrincipal;
		this.musculoSecundario = musculoSecundario;
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

	@Override
	public String toString() {
		return "Ejercicio [id=" + id + ", nombre=" + nombre + ", musculoPrincipal=" + musculoPrincipal
				+ ", musculoSecundario=" + musculoSecundario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, musculoPrincipal, musculoSecundario, nombre);
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
<<<<<<< HEAD

		return ID_Entrenamiento == other.ID_Entrenamiento && OrdenEnEntrenamiento == other.OrdenEnEntrenamiento
				&& id == other.id && musculoPrincipal == other.musculoPrincipal
				&& musculoSecundario == other.musculoSecundario && Objects.equals(nombre, other.nombre)
				&& Objects.equals(series, other.series);
=======
		return id == other.id && musculoPrincipal == other.musculoPrincipal
				&& musculoSecundario == other.musculoSecundario && Objects.equals(nombre, other.nombre);
>>>>>>> branch 'master' of https://github.com/menoth/proyecto-PROG3
	}
	
<<<<<<< HEAD
	
	
	
=======
		
>>>>>>> branch 'master' of https://github.com/menoth/proyecto-PROG3
}