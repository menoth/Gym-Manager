package domain;

import java.util.Objects;

public class Serie {
	
	public enum Esfuerzo {
		APROXIMACION, ESTANDAR, TOPSET
	}
	
	private int id;
	private float peso;
	private int repeticiones;
	private int ID_Ejercicio;
	private int ID_rpe;
	private int OrdenEnEjercicio;
	
	public Serie(int id, float peso, int repeticiones, int iD_Ejercicio, int iD_rpe, int ordenEnEjercicio) {
		super();
		this.id = id;
		this.peso = peso;
		this.repeticiones = repeticiones;
		ID_Ejercicio = iD_Ejercicio;
		ID_rpe = iD_rpe;
		OrdenEnEjercicio = ordenEnEjercicio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	public int getID_Ejercicio() {
		return ID_Ejercicio;
	}

	public void setID_Ejercicio(int iD_Ejercicio) {
		ID_Ejercicio = iD_Ejercicio;
	}

	public int getID_rpe() {
		return ID_rpe;
	}

	public void setID_rpe(int iD_rpe) {
		ID_rpe = iD_rpe;
	}

	public int getOrdenEnEjercicio() {
		return OrdenEnEjercicio;
	}

	public void setOrdenEnEjercicio(int ordenEnEjercicio) {
		OrdenEnEjercicio = ordenEnEjercicio;
	}

	@Override
	public String toString() {
		return "Serie [id=" + id + ", peso=" + peso + ", repeticiones=" + repeticiones + ", ID_Ejercicio="
				+ ID_Ejercicio + ", ID_rpe=" + ID_rpe + ", OrdenEnEjercicio=" + OrdenEnEjercicio + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID_Ejercicio, ID_rpe, OrdenEnEjercicio, id, peso, repeticiones);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Serie other = (Serie) obj;
		return ID_Ejercicio == other.ID_Ejercicio && ID_rpe == other.ID_rpe
				&& OrdenEnEjercicio == other.OrdenEnEjercicio && id == other.id
				&& Float.floatToIntBits(peso) == Float.floatToIntBits(other.peso) && repeticiones == other.repeticiones;
	}
	
	
	
	
		
}
