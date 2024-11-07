package domain;

import java.util.ArrayList;

public class Ejercicio extends Entrenamiento {
	
	private String nombre;	// Nombre del ejercicio
	private String desc;
    private int repeticiones; // Número de repeticiones
    private int series; // Número de series
    private double peso; // En kg
    private int rir; // Repeticiones en reserva
    
	
	public Ejercicio(String nombre, ArrayList<Entrenamiento> entrenamientos, String objetivo, int duracionSemanas,
			int diasPorSemana, String nombre2, int duracionMinutos, String diaSemana, int numeroEjercicios,
			String tipoEntrenamiento, int duracionDescanso, String instrucciones, String nombre3, String desc,
			int repeticiones, int series, double peso, int rir) {
		super(nombre, entrenamientos, objetivo, duracionSemanas, diasPorSemana, nombre2, duracionMinutos, diaSemana,
				numeroEjercicios, tipoEntrenamiento, duracionDescanso, instrucciones);
		nombre = nombre3;
		this.desc = desc;
		this.repeticiones = repeticiones;
		this.series = series;
		this.peso = peso;
		this.rir = rir;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int getRir() {
		return rir;
	}

	public void setRir(int rir) {
		this.rir = rir;
	}

	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Ejercicio [nombre=" + nombre + ", desc=" + desc + ", repeticiones=" + repeticiones + ", series="
				+ series + ", peso=" + peso + ", rir=" + rir + ", getDuracionMinutos()=" + getDuracionMinutos()
				+ ", getDiaSemana()=" + getDiaSemana() + ", getNumeroEjercicios()=" + getNumeroEjercicios()
				+ ", getTipoEntrenamiento()=" + getTipoEntrenamiento() + ", getDuracionDescanso()="
				+ getDuracionDescanso() + ", getInstrucciones()=" + getInstrucciones() + ", getEntrenamientos()="
				+ getEntrenamientos() + ", getObjetivo()=" + getObjetivo() + ", getDuracionSemanas()="
				+ getDuracionSemanas() + ", getDiasPorSemana()=" + getDiasPorSemana() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
    
    
}
