package domain;

import java.util.ArrayList;

public class Entrenamiento extends Rutina {
		
	
	private String nombre;
	private int duracionMinutos;
    private String diaSemana;
    private int numeroEjercicios;
    private String tipoEntrenamiento;
    private int duracionDescanso;
    private String instrucciones;
    
	public Entrenamiento(String nombre, ArrayList<Entrenamiento> entrenamientos, String objetivo, int duracionSemanas,
			int diasPorSemana, String nombre2, int duracionMinutos, String diaSemana, int numeroEjercicios,
			String tipoEntrenamiento, int duracionDescanso, String instrucciones) {
		super(nombre, entrenamientos, objetivo, duracionSemanas, diasPorSemana);
		nombre = nombre2;
		this.duracionMinutos = duracionMinutos;
		this.diaSemana = diaSemana;
		this.numeroEjercicios = numeroEjercicios;
		this.tipoEntrenamiento = tipoEntrenamiento;
		this.duracionDescanso = duracionDescanso;
		this.instrucciones = instrucciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public int getNumeroEjercicios() {
		return numeroEjercicios;
	}

	public void setNumeroEjercicios(int numeroEjercicios) {
		this.numeroEjercicios = numeroEjercicios;
	}

	public String getTipoEntrenamiento() {
		return tipoEntrenamiento;
	}

	public void setTipoEntrenamiento(String tipoEntrenamiento) {
		this.tipoEntrenamiento = tipoEntrenamiento;
	}

	public int getDuracionDescanso() {
		return duracionDescanso;
	}

	public void setDuracionDescanso(int duracionDescanso) {
		this.duracionDescanso = duracionDescanso;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	
}
	
	


