package domain;

public class Logro {
    private String nombre;
    private String imagen;

    public Logro(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

	@Override
	public String toString() {
		return "Logro [nombre=" + nombre + ", imagen=" + imagen + "]";
	}
    
}
