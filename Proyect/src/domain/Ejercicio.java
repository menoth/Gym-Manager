package domain;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio extends Serie {
	private String nombre;
	private int id;
	private String musc_principal;
	private String musc_secundario;
	private ArrayList<Serie> series;
	
	
	public Ejercicio(int reps, float peso, Esfuerzo esfuerzo, String nombre, int id, String musc_principal,
			String musc_secundario, ArrayList<Serie> series) {
		super(reps, peso, esfuerzo);
		this.nombre = nombre;
		this.id = id;
		this.musc_principal = musc_principal;
		this.musc_secundario = musc_secundario;
		this.series = series;
	}


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


	public String getMusc_principal() {
		return musc_principal;
	}


	public void setMusc_principal(String musc_principal) {
		this.musc_principal = musc_principal;
	}


	public String getMusc_secundario() {
		return musc_secundario;
	}


	public void setMusc_secundario(String musc_secundario) {
		this.musc_secundario = musc_secundario;
	}


	public ArrayList<Serie> getSeries() {
		return series;
	}


	public void setSeries(ArrayList<Serie> series) {
		this.series = series;
	}


	@Override
	public String toString() {
		return "Ejercicio [nombre=" + nombre + ", id=" + id + ", musc_principal=" + musc_principal
				+ ", musc_secundario=" + musc_secundario + ", series=" + series + ", getReps()=" + getReps()
				+ ", getPeso()=" + getPeso() + ", getEsfuerzo()=" + getEsfuerzo() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
	
}