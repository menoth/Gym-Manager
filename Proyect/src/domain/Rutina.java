package domain;

import java.util.ArrayList;

public class Rutina {
	
	private String nombre;
	private ArrayList<Entrenamiento> entrenamientos;
	private String objetivo;
	private int duracionSemanas;
	private int diasPorSemana;
	
	public Rutina(String nombre, ArrayList<Entrenamiento> entrenamientos, String objetivo, int duracionSemanas,
			int diasPorSemana) {
		this.nombre = nombre;
		this.entrenamientos = entrenamientos;
		this.objetivo = objetivo;
		this.duracionSemanas = duracionSemanas;
		this.diasPorSemana = diasPorSemana;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Entrenamiento> getEntrenamientos() {
		return entrenamientos;
	}

	public void setEntrenamientos(ArrayList<Entrenamiento> entrenamientos) {
		this.entrenamientos = entrenamientos;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public int getDuracionSemanas() {
		return duracionSemanas;
	}

	public void setDuracionSemanas(int duracionSemanas) {
		this.duracionSemanas = duracionSemanas;
	}

	public int getDiasPorSemana() {
		return diasPorSemana;
	}

	public void setDiasPorSemana(int diasPorSemana) {
		this.diasPorSemana = diasPorSemana;
	}

	
	
	
	
	
}
	