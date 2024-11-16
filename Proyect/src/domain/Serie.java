package domain;

import java.util.Objects;

public class Serie {
	
	public enum Esfuerzo {
		APROXIMACION, ESTANDAR, TOPSET
	}
	
	
	private int repeticiones;
	private float peso;
	Esfuerzo esfuerzo;
	
	
	//CONSTRUCTOR
	public Serie(int repeticiones, float peso, Esfuerzo esfuerzo) {
		super();
		this.repeticiones = repeticiones;
		this.peso = peso;
		this.esfuerzo = esfuerzo;
	}
	
	
	
	//GETTERS Y SETTERS
	public int getRepeticiones() {
		return repeticiones;
	}
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public Esfuerzo getEsfuerzo() {
		return esfuerzo;
	}
	public void setEsfuerzo(Esfuerzo esfuerzo) {
		this.esfuerzo = esfuerzo;
	}
	
	
	
	//TOSTRING
	@Override
	public String toString() {
		return "Serie [repeticiones=" + repeticiones + ", peso=" + peso + ", esfuerzo=" + esfuerzo + "]";
	}
	
	
	
	//HASHCODE Y EQUALS
	@Override
	public int hashCode() {
		return Objects.hash(esfuerzo, peso, repeticiones);
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
		return esfuerzo == other.esfuerzo && Float.floatToIntBits(peso) == Float.floatToIntBits(other.peso)
				&& repeticiones == other.repeticiones;
	}
	
	
	
	
	
	
	
	
	
}
