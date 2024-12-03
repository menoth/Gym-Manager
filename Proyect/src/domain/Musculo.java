package domain;

import java.util.Objects;

public class Musculo {
	public enum TamanoMusculo{
		GRANDE, MEDIANO, PEQUEÑO
	}
	private String nombre;
	private TamanoMusculo TamanoMusculo;
	
	public Musculo(String nombre, TamanoMusculo tamanoMusculo) {
		super();
		this.nombre = nombre;
		TamanoMusculo = tamanoMusculo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TamanoMusculo getTamanoMusculo() {
		return TamanoMusculo;
	}

	public void setTamanoMusculo(TamanoMusculo tamanoMusculo) {
		TamanoMusculo = tamanoMusculo;
	}

	@Override
	public String toString() {
		return "Musculo [nombre=" + nombre + ", TamanoMusculo=" + TamanoMusculo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(TamanoMusculo, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Musculo other = (Musculo) obj;
		return TamanoMusculo == other.TamanoMusculo && Objects.equals(nombre, other.nombre);
	}
	
	
	
	
}
