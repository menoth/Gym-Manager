package domain;

public class Serie {
	
	private int reps;
	private float peso;
	private Esfuerzo esfuerzo;
	
	public Serie(int reps, float peso, Esfuerzo esfuerzo) {
		super();
		this.reps = reps;
		this.peso = peso;
		this.esfuerzo = esfuerzo;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
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

	@Override
	public String toString() {
		return "Serie [reps=" + reps + ", peso=" + peso + ", esfuerzo=" + esfuerzo + "]";
	}
	
}
