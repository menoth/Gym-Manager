package domain;

import java.util.Objects;

public class RPE {
	public enum Esfuerzo {
		APROXIMACION, ESTANDAR, TOPSET
	}
	
	private int ID_RPE;
	private String descripcion;
	private Esfuerzo esfuerzo;
	
	public RPE(int iD_RPE, String descripcion, Esfuerzo esfuerzo) {
		super();
		ID_RPE = iD_RPE;
		this.descripcion = descripcion;
		this.esfuerzo = esfuerzo;
	}

	public int getID_RPE() {
		return ID_RPE;
	}

	public void setID_RPE(int iD_RPE) {
		ID_RPE = iD_RPE;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Esfuerzo getEsfuerzo() {
		return esfuerzo;
	}

	public void setEsfuerzo(Esfuerzo esfuerzo) {
		this.esfuerzo = esfuerzo;
	}

	@Override
	public String toString() {
		return "RPE [ID_RPE=" + ID_RPE + ", descripcion=" + descripcion + ", esfuerzo=" + esfuerzo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID_RPE, descripcion, esfuerzo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RPE other = (RPE) obj;
		return ID_RPE == other.ID_RPE && Objects.equals(descripcion, other.descripcion) && esfuerzo == other.esfuerzo;
	}
	
	
}
